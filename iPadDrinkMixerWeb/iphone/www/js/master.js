var db;
var favoritesArray = new Array();
var selectedDrinkDetails;
var ROOT_IP = "http://localhost:8080";
var ROOT_URL = ROOT_IP + "/iPad/rest/";
var css_orientation = "port";
var list_scroll = false;
var PAGING_COUNT = 0;
var PAGING_TYPE; //keep track of the current page count for load more.
var CAT_TYPE_ID=-1; //keep track of the current category selected for load more.
var ING_TYPE_ID=-1; //keep track of the current ingredient type for load more.
var LIMIT = 100;
var BUTTON_CLICKED = false; //set this so when a button is clicked no others can be
var clientIp = "0.0.0.0";
var deviceName = "";
var deviceUID = "";
var drinkUID = "";
var deviceVersion = "";

var subject = 'Check out this drink!';
var anything = 'Check out this drink!';
var toRecipients = '';
var ccRecipients = '';
var bccRecipients = '';
var bIsHTML = 'false';
const TYPE_LIQUOR = 1; //"Liquor";
const TYPE_MIXERS = 2; //"Mixers";
const TYPE_GARNISH = 3; //"Garnish";
const TYPE_LIQUOR_NAME = "Liquor";
const TYPE_MIXERS_NAME = "Mixers";
const TYPE_GARNISH_NAME = "Garnish";
var TYPE_NAME;

const CAT_COCKTAIL = 1;
const CAT_HOT_DRINK = 2;
const CAT_JELLO_SHOT = 3;
const CAT_MARTINIS = 4;
const CAT_NON_ALC = 5;
const CAT_PUNCH = 6;
const CAT_SHOOTER = 7;

const PAGING_TYPE_ALL = 1;
const PAGING_TYPE_CATEGORY = 2;
const PAGING_TYPE_ING = 3;
const PAGING_TYPE_SEARCH = 4;
//used for filting
const PAGING_TYPE_ING_LIQUOR = 5;
const PAGING_TYPE_ING_MIXER = 6;
const PAGING_TYPE_ING_GARNISH = 7;

var isTouch = (/ipad/gi).test(navigator.appVersion),

// Event sniffing
var START_EVENT = isTouch ? 'touchstart' : 'mousedown';
var MOVE_EVENT = isTouch ? 'touchmove' : 'mousemove';
var END_EVENT = isTouch ? 'touchend' : 'mouseup';
var screenTimeout;


function changeOrientation(orientation) {
	
    if (orientation == 0) //portrait
    {
        css_orientation = "port";
        $("body").addClass("port").removeClass("land");
        //hide any popouts
        $(".popout").hide();
    }
    else //landscape
    {
        css_orientation = "land"
        $("body").addClass("land").removeClass("port");
        $("#wrapper").show();
		
    }
	
}

function refreshMainScroller() {
    //resize the scroll area
    mainscroller.refresh();
}


$(document).ready(function () {
				  
				  //prevent screen from scrolling
				  document.addEventListener(MOVE_EVENT, function (e) {
											e.preventDefault();
											});
				  
				  /*********** CHECK AND CREATE LOCAL DB ***************/
				  var dbName = "favoritesDB";
				  var version = "1.0";
				  var displayName = "favoritesDB";
				  var maxSize = 65536;
				  db = openDatabase(dbName, version, displayName, maxSize);
				  db.transaction(
								 
								 function (transaction) {
								 transaction.executeSql("CREATE TABLE IF NOT EXISTS tblFavorites (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,drink_id INTEGER NOT NULL);");
								 });
				  
				  //create drink 
				  $("#ing-liquor,#ing-mixer,#ing-garnish").bind(END_EVENT,handleIngPop).bind(START_EVENT,function(){$(this).addClass("ingTouch");});
				  
				  
				  
				  //hide popout on window touch
				  $(".content_wrapper").bind(START_EVENT, function (e) {
											 e.preventDefault();
											 //hide all pop ups
											 $(".port .popout").fadeOut();
											 $("#ing-inner-wrapper").fadeOut();
											 
											 });
				  
				  
				  /********* SET ORIENTATION ******************/
				  var orientation; //leave after scroll
				  if (window.innerWidth > window.innerHeight) {
				  css_orientation = "land";
				  $("body").addClass("land").removeClass("port");
				  }
				  else {
				  css_orientation = "port";
				  $("body").addClass("port").removeClass("land");
				  }
				  
				  mainscroller = new iScroll('scroller');
				  editchosenIngs = new iScroll('editchosenIngs');
				  chosenIngs = new iScroll('chosenIngs');
				  scroller4 = new iScroll('scroll-desc');
				  
				  $(".button,.sm_button,.fav_button").bind(START_EVENT, handleTouchStart).bind(END_EVENT, handleTouchEnd);
				  $(".postbutton").bind(START_EVENT, handlePostButtonTouchStart).bind(END_EVENT, handlePostButtonTouchEnd);
				  
				  
				  $(".star_delete").bind(END_EVENT, function () {
										 $(".star").removeClass("star_on");
										 $(".star").removeClass("star_half_on");
										 $("#rating").val("");
										 });
				  
				  $(".submit_rate").bind(START_EVENT, function () {
										 
										 $(this).addClass("submit_rate_on");
										 
										 }).bind(END_EVENT, function () {
												 
												 $(this).removeClass("submit_rate_on");
												 
												 var requestUrl = ROOT_URL + "drinks/rate?id=" + $("#drink_id_input").val() + "&rating=" + $("#rating").val() + "&ip=" + clientIp + "&version=" + deviceVersion + "&uid=" + deviceUID + "&name=" + deviceName;
												 
												 
												 $.getJSON(requestUrl, function (data) {
														   setRating(data);
														   $(".submit_rate").text("RATED!");
														   window.setTimeout(function () {
																			 $(".submit_rate").hide();
																			 }, 1500);
														   
														   });
												 
												 
												 
												 });
				  
				  $(".star").bind(END_EVENT, function () {
								  
								  if ($(".submit_rate").text() != "RATED!") $(".submit_rate").show();
								  
								  $(".star").removeClass("star_on");
								  $(".star").removeClass("star_half_on");
								  var id = $(this).attr("id");
								  var s = id.split("_");
								  
								  var index = s[1];
								  $("#rating").val(index);
								  
								  for (i = 0; i < $(".star").length; i++) {
								  
								  if (i < index) $($(".star").get(i)).addClass("star_on");
								  }
								  
								  
								  
								  
								  });
				  
				  
				  
				  /****************  SEARCH FILTER ********************/
				  $(".search-input").bind("keyup", function (e) {
										  
										  
										  //console.log(e.keyCode);
										  if ((e.keyCode >= 48 && e.keyCode <= 57) || (e.keyCode >= 65 && e.keyCode <= 90) || e.keyCode == 0) {
										  
											PAGING_COUNT = 0; //first time called
											PAGING_TYPE = PAGING_TYPE_SEARCH;
										  
											$("#list_wrapper").empty();
										  
											var requestUrl = ROOT_URL + "drinks/search?catid="+CAT_TYPE_ID+"&searchParam=" + $(this).val() + "&startIndex=0";
										  
											//for ing searches
											if (TYPE_NAME == TYPE_LIQUOR_NAME)
												requestUrl = ROOT_URL + "drinks/ingsfilter" + TYPE_LIQUOR + "?searchParam=" + $(this).val() + "&startIndex=0";
											else if(TYPE_NAME == TYPE_MIXERS_NAME)
												requestUrl = ROOT_URL + "drinks/ingsfilter" + TYPE_MIXERS + "?searchParam=" + $(this).val() + "&startIndex=0";
											else if(TYPE_NAME == TYPE_GARNISH_NAME) 
												requestUrl = ROOT_URL + "drinks/ingsfilter" + TYPE_GARNISH + "?searchParam=" + $(this).val() + "&startIndex=0";
				
			
											$(this).addClass("search-loader");
											
											if(TYPE_NAME == TYPE_LIQUOR_NAME || TYPE_NAME == TYPE_MIXERS_NAME || TYPE_NAME == TYPE_GARNISH_NAME)	
												filterIngredientsList(requestUrl);
											else
												processDrinks(requestUrl, false);
										  }
										  
										  });
				  
				  
				  /**************** list list buttons **************/
				  $(".list_email").bind(START_EVENT,function(){
										$(this).addClass("emailbutton-on");
										
										}).bind(END_EVENT, function (e) {
										e.preventDefault(); //prevent copy and mag from showing
												$(this).removeClass("emailbutton-on");
										try {
										//$("#response").text("Email Called");
										window.plugins.emailComposer.showEmailComposer("subject", "body", "", "", "", true);
										} catch (e) {
										alert(e);
										}
										
										});
				  
				  $(".list_glass").bind(START_EVENT, function (e) {
										e.preventDefault(); //prevent copy and mag from showing
										if (!list_scroll) //if not scrolling 
										{
										//$("#response").text("Drink Called");
										}
										else list_scroll = false;
										});
				  
				  /***********************************************/
				  $(".saveIng").bind(START_EVENT,function(e){
									 $(this).addClass("savebutton-on");
									 }).bind(END_EVENT,function(){
													$(this).removeClass("savebutton-on");	
											 
											 var wholenum = $("#ing_list_wrapper-whole").find(".list_item_down").attr("val");
											 var halfnum = $("#ing_list_wrapper-half").find(".list_item_down").attr("val");
											 var unit = $("#ing_list_wrapper-units").find(".list_item_down").attr("val");
											 var ingredient_text = $("#ing-inner-wrapper-select :selected").text();
											 var ingredient_id = $("#ing-inner-wrapper-select").val();
											// console.log(wholenum +" "+ halfnum +" "+ unit +" "+ingredient_text);
											 //add item to ul
											 $("#editchosenIngs .ing-items-wrapper").append("<li val='"+ingredient_id+"' class='edit-ing'>"+wholenum +" "+ halfnum+ " " + unit+", "+ingredient_text+"</li>");
									
											 $("#ing-inner-wrapper").fadeOut();
											 
											 //reset values
											 $("#ing_list_wrapper-whole li").removeClass("list_item_down");
											 $("#ing_list_wrapper-half li").removeClass("list_item_down");
											 $("#ing_list_wrapper-units li").removeClass("list_item_down");
																	
									});
				  
				  $(".cancelIng").bind(START_EVENT,function(e){
									 $(this).addClass("backbutton-on");
									 }).bind(END_EVENT,function(){
											 $(this).removeClass("backbutton-on");				
											 $("#ing-inner-wrapper").fadeOut();
											 });
				  
				  
				  //flip page
				  $(".frontbutton").bind(START_EVENT,function(e){
													   
													   $(this).addClass("editbutton-on");
													   
													   });
				  $(".createbutton").bind(START_EVENT,function(e){
													   
													   $(this).addClass("createbutton-on");
													   
													   });
				  
				  $(".frontbutton,.createbutton").bind(END_EVENT, function (e) {
													   e.preventDefault(); //prevent copy and mag from showing
															   
															   $(this).removeClass("createbutton-on");
															   $(this).removeClass("editbutton-on");
													   
													   //reset ing
													   $(".stage-2").hide();
													   $(".stage-1").show();
													   
													   //flip paper
													   $("#paper_wrapper .paper").addClass("flip-back").removeClass("flip-front");
													   
													   if ($(this).hasClass("createbutton")) //we are creating a new drink so lets clear out the fields
													   {
													   
													   $(".edit-drink-title").val("");
													   $(".edit-drink-desc").val("");
													   $(".edit-ing-wrapper .scroll-child").empty();
													   
													   
													   }
													   else setUpEdit();
													   
													   
													   });
				  
				  $(".backbutton").bind(START_EVENT,function(){
											$(this).addClass("savebutton-on");
										}).bind(END_EVENT, function (e) {
												e.preventDefault(); //prevent copy and mag from showing
												$(this).removeClass("savebutton-on");
										//flip paper back
										$("#paper_wrapper .paper").addClass("flip-front").removeClass("flip-back");
										});
				  
				  $(".cancelbutton").bind(START_EVENT,function(){
										  $(this).addClass("backbutton-on");
										  
										  }).bind(END_EVENT, function (e) {
												  e.preventDefault(); //prevent copy and mag from showing
												  $(this).removeClass("backbutton-on");
										  //flip paper back
										  $("#paper_wrapper .paper").addClass("flip-front").removeClass("flip-back");
										  });
				  
				  
				  list_item_events();
				  //load up the favorites in memory
				  setUpFavorites();
				  showStartMask();
				  addEditButtonEvents();
				  });

function list_item_events() {
	
	$(".ing-item").bind(START_EVENT,function(){
						list_scroll = false;
						
						}).bind(END_EVENT,function(){
								
						$(this).parent().find(".ing-item").removeClass("list_item_down");
						
						if (!list_scroll)
							$(this).addClass("list_item_down");
						
						}).bind(MOVE_EVENT,function(){
								list_scroll = true;
								});
	
    $(".list_item").bind(START_EVENT, function (e) {
						 e.preventDefault();
						 
						 list_scroll = false;
						 }).bind(END_EVENT, function (e) {
								 e.preventDefault();
								  $(this).parent().find(".list_item").removeClass("list_item_down");
								 		
								 if (!list_scroll) //if not scrolling 
								 {
									$(this).addClass("list_item_down");
									$(this).find(".list_fav_selected").addClass("list-item-loading");
									$(this).find(".list_fav").addClass("list-item-loading");
									showDetail(this);
								 }
								 
								 
								 
								 }).bind(MOVE_EVENT, function (e) {
										 list_scroll = true; //used if your scrolling
										 });;
	
    $(".list_fav,.list_fav_selected").bind(START_EVENT, function (e) {
										   e.preventDefault(); //prevent copy and mag from showing
										   if (!list_scroll) //if not scrolling 
										   {
										   if (confirm("Are you sure you want change your favorite status?")) {
										   
										   var drinkId = $(this).parent().attr("id");
										   var isOn = $(this).hasClass("list_fav_selected");
										   var that = this;
										   var tmpfav = new Array();
										   
										   if (!isOn) //add favorite
										   {
										   db.transaction(
														  
														  function (transaction) {
														  transaction.executeSql("INSERT INTO tblFavorites (drink_id) VALUES (?);", [drinkId], function () {
																				 
																				 $(that).addClass("list_fav_selected").removeClass("list_fav");
																				 //add id
																				 favoritesArray.push(drinkId);
																				 $(".list_item").removeClass("list_item_down");
																				 })
														  });
										   }
										   else //remove favorite
										   {
										   db.transaction(
														  
														  function (transaction) {
														  transaction.executeSql("DELETE FROM tblFavorites WHERE drink_id = (?);", [drinkId], function () {
																				 
																				 $(that).addClass("list_fav").removeClass("list_fav_selected");
																				 //remove id
																				 for (i = 0; i < favoritesArray.length; i++) {
																				 if (favoritesArray[i] != drinkId) tmpfav.push(favoritesArray[i]);
																				 }
																				 //reset favoritesArray
																				 favoritesArray = tmpfav;
																				 
																				 
																				 $(".list_item").removeClass("list_item_down");
																				 
																				 })
														  });
										   }
										   
										   }
										   }
										   else list_scroll = false;
										   
										   
										   });
	
    $("#loadMore").bind(END_EVENT, function () {
						PAGING_COUNT += 200;
						
						if (PAGING_TYPE == PAGING_TYPE_CATEGORY) var requestUrl = ROOT_URL + "drinks/cats" + CAT_TYPE_ID + "?startIndex=" + PAGING_COUNT;
						else if (PAGING_TYPE == PAGING_TYPE_ALL) var requestUrl = ROOT_URL + "drinks?startIndex=" + PAGING_COUNT;
						else if (PAGING_TYPE == PAGING_TYPE_ING) var requestUrl = ROOT_URL + "drinks/ings" + ING_TYPE_ID + "?startIndex=" + PAGING_COUNT;
						else if (PAGING_TYPE == PAGING_TYPE_SEARCH) var requestUrl = ROOT_URL + "drinks/search?searchParam=" + $(".search-input").val() + "&startIndex=" + PAGING_COUNT;
						
						
						$(this).remove();
						
						processDrinks(requestUrl, true);
						
						});
}

function handleIngPop(){
	
	var id = $(this).attr("id");
	$(this).removeClass("ingTouch");
	$("#ing-inner-wrapper").fadeIn();
	
	ingscrollerwhole = new iScroll('ing-scroller-whole',{vScrollbar:false});
	ingscrollerhalf = new iScroll('ing-scroller-half',{vScrollbar:false});
	ingscrollerunit = new iScroll('ing-scroller-units',{vScrollbar:false});
	
	if(id=="ing-liquor"){
				
		//ajax
		var requestUrl = ROOT_URL + "drinks/ings" + TYPE_LIQUOR + "?startIndex=0";
		$("#ing-inner-wrapper-select").empty();
		
		processIngredients(requestUrl);
		
	}
	else if(id=="ing-mixer"){

		//ajax
		var requestUrl = ROOT_URL + "drinks/ings" + TYPE_MIXERS + "?startIndex=0";
		$("#ing-inner-wrapper-select").empty();
		
		processIngredients(requestUrl);
		
	}
	else if(id=="ing-garnish"){

		//ajax
		var requestUrl = ROOT_URL + "drinks/ings" + TYPE_GARNISH + "?startIndex=0";
		$("#ing-inner-wrapper-select").empty();
		
		processIngredients(requestUrl);
	}
	
	$("#ing-inner-wrapper").addClass(id);
	$("#ing-inner-wrapper").fadeIn();			
}

function handlePostButtonTouchStart(e) {
    e.preventDefault(); //prevent copy and mag from showing
    var obj = e.currentTarget;
	
    if ($(obj).attr("id") == "search") {
        $(obj).addClass("search_touch");
		
    }
    else if ($(obj).attr("id") == "list") {
        $(obj).addClass("list_touch");
    }
}

//post button popouts

function handlePostButtonTouchEnd(e) {
    var obj = e.currentTarget;
    $(obj).removeClass("search_touch");
    $(obj).removeClass("list_touch");
	
    //show popoaver here
    //hid any open popouts
    $(".popout").hide();
    if ($(obj).attr("id") == "search") $(".port #button_block").fadeIn();
    else if ($(obj).attr("id") == "list") {
        $(".port #wrapper").fadeIn();
        //resize the scroll area
        mainscroller.refresh();
    }
	
	
}

//normal buttons

function handleTouchStart(e) {
    e.preventDefault(); //prevent copy and mag from showing
    var obj = e.currentTarget;
	
    if ($(obj).hasClass("button")) $(obj).addClass("touched");
    else if ($(obj).hasClass("fav_button")) $(obj).addClass("fav_touched");
    else $(obj).addClass("sm_touched");
	
}
//standard button events

function handleTouchEnd(e) {
    e.preventDefault(); //prevent copy and mag from showing
	//clear text field
	$(".search-input").val("");
	
    var obj = e.currentTarget;
    //reset back to default
    IS_SHARED_DRINK = false;
    $(obj).removeClass("touched").removeClass("sm_touched").removeClass("fav_touched");
	
    if ($(obj).attr("id") == "filter_ing") {
        if (!BUTTON_CLICKED) {
            BUTTON_CLICKED = true;
            $("#main_buttons").slideUp(function () {
									   $("#ingredients").slideDown(function () {
																   BUTTON_CLICKED = false;
																   });
									   });
        }
		
    }
    else if ($(obj).attr("id") == "filter_cat") {
        if (!BUTTON_CLICKED) {
            BUTTON_CLICKED = true;
            $("#main_buttons").slideUp(function () {
									   $("#category").slideDown(function () {
																BUTTON_CLICKED = false;
																});
									   });
        }
    }
    else if ($(obj).attr("id") == "ing_back") //back from ing
    {
        $("#ingredients").slideUp(function () {
								  $("#main_buttons").slideDown();
								  });
		TYPE_NAME = "";
		CAT_TYPE_ID=-1
    } else if ($(obj).attr("id") == "cat_back") //back from cat
    {
        $("#category").slideUp(function () {
							   $("#main_buttons").slideDown();
							   });
		TYPE_NAME = "";
		CAT_TYPE_ID=-1
    } else if ($(obj).attr("id") == "shared") {
        //reset back to default
        IS_SHARED_DRINK = true;
        showDrinkList(ROOT_URL + "drinks/shared?startIndex=0");
		
    }
    else if ($(obj).attr("id") == "fav") {
        showDrinkList(ROOT_URL + "drinks/favs?ids=" + favoritesArray.toString() + "&startIndex=0");
    }
    else if ($(obj).attr("id") == "show_all") {
        showDrinkList(ROOT_URL + "drinks?startIndex=0");
    }
    else if ($(obj).attr("id") == "liquor") {
        TYPE_NAME = TYPE_LIQUOR_NAME;
        showIngList(TYPE_LIQUOR);
		
    }
    else if ($(obj).attr("id") == "mixer") {
        TYPE_NAME = TYPE_MIXERS_NAME;
        showIngList(TYPE_MIXERS);
    }
    else if ($(obj).attr("id") == "garnish") {
        TYPE_NAME = TYPE_GARNISH_NAME;
        showIngList(TYPE_GARNISH);
    }
    else if ($(obj).attr("id") == "cocktail") {
        showDrinksByCatList(CAT_COCKTAIL);
    }
    else if ($(obj).attr("id") == "hotDrinks") {
        showDrinksByCatList(CAT_HOT_DRINK);
    }
    else if ($(obj).attr("id") == "jelloShots") {
        showDrinksByCatList(CAT_JELLO_SHOT);
    }
    else if ($(obj).attr("id") == "martinis") {
        showDrinksByCatList(CAT_MARTINIS);
    }
    else if ($(obj).attr("id") == "nonAlcohlic") {
        showDrinksByCatList(CAT_NON_ALC);
    }
    else if ($(obj).attr("id") == "punch") {
        showDrinksByCatList(CAT_PUNCH);
    }
    else if ($(obj).attr("id") == "shooter") {
        showDrinksByCatList(CAT_SHOOTER);
    }
	
	
}


/********** FILTER DRINK INN LIST *************/

function filterDrinkList() {
	
    if (PAGING_TYPE == PAGING_TYPE_CATEGORY) var requestUrl = ROOT_URL + "drinks/cats" + CAT_TYPE_ID + "?startIndex=0";
    else if (PAGING_TYPE == PAGING_TYPE_ALL) var requestUrl = ROOT_URL + "drinks?startIndex=0";
    else if (PAGING_TYPE == PAGING_TYPE_ING) var requestUrl = ROOT_URL + "drinks/ings" + ING_TYPE_ID + "?startIndex=0";
	
    $("#list_wrapper").empty();
	
    processDrinks(requestUrl, true);
	
}

/********** FILTER DRINK INN LIST *************/


/*********** DRINK LISTS *************/
//gets all drinks by category

function showDrinksByCatList(catId) {
    PAGING_COUNT = 0; //first time called
    PAGING_TYPE = PAGING_TYPE_CATEGORY;
    CAT_TYPE_ID = catId;
	
    $("#list_wrapper").empty();
    var requestUrl = ROOT_URL + "drinks/cats" + CAT_TYPE_ID + "?startIndex=0";
	
    processDrinks(requestUrl, true);
}

//shows all the drinks limit 200

function showDrinkList(requestUrl) {
    PAGING_COUNT = 0; //first time called
    PAGING_TYPE = PAGING_TYPE_ALL;
	
    $("#list_wrapper").empty();
	
    processDrinks(requestUrl, true);
} /*********** DRINK LISTS *************/


//gets all the liquors

function showIngList(ingType) {
	
    PAGING_COUNT = 0; //first time called
    PAGING_TYPE = PAGING_TYPE_ING;
    ING_TYPE_ID = ingType;
	
    showLoadingMask(); //pop modal
    $("#list_wrapper").empty();
    var requestUrl = ROOT_URL + "drinks/ings" + ING_TYPE_ID + "?startIndex=0";
	
    $.getJSON(requestUrl, function (data) {
			  
			  if (data != null) {
			  for (i = 0; i < data.ingredient.length; i++)
			  $("#list_wrapper").append("<li class=\"list_item\" id=\"" + data.ingredient[i].id + "\"><span class=\"ingredient\">" + data.ingredient[i].name + "</span></li>");
			  
			  if ($("#list_wrapper li").length >= 200) //add the add more link
			  $("#list_wrapper").append("<li id=\"loadMore\">Load More...</li>");
			  
			  
			  //add events
			  list_item_events();
			  //if portrait show pop up
			  if (css_orientation == "port") $(".port #wrapper").fadeIn();
			  }
			  
			  removeLoadingMask();
			  
			  });
	
}

//provides a filter search based on ingredient type
function filterIngredientsList(requestUrl){
	
    showLoadingMask(); //pop modal
    $("#list_wrapper").empty();
	
    $.getJSON(requestUrl, function (data) {
			  
			  if (data != null) {
				if (data.ingredient.length != undefined) {
			  
				  for (i = 0; i < data.ingredient.length; i++)
					$("#list_wrapper").append("<li class=\"list_item\" id=\"" + data.ingredient[i].id + "\"><span class=\"ingredient\">" + data.ingredient[i].name + "</span></li>");
				  
				  
				  
					
				  }else
				      $("#list_wrapper").append("<li class=\"list_item\" id=\"" + data.ingredient.id + "\"><span class=\"ingredient\">" + data.ingredient.name + "</span></li>");
				  
			  
			  //add events
			  list_item_events();
			  //if portrait show pop up
			  if (css_orientation == "port") $(".port #wrapper").fadeIn();
			  
			  }
			  
			  removeLoadingMask();
			  
			  });
}

//process the ajax and returns the results

function processDrinks(requestUrl, showDetails) {

    if (showDetails) showLoadingMask();
	
    var favoritesStar;
    $.getJSON(requestUrl, function (data) {
			  
			  if (data != null) {
			  
			  if (data.drinkDetails.length != undefined) {
				  //if type array meaning returns more than one
				  for (i = 0; i < data.drinkDetails.length; i++) {
					favoritesStar = "list_fav";
				  
					for (x = 0; x < favoritesArray.length; x++) {
				  
						if (data.drinkDetails[i].id == favoritesArray[x]) {
						favoritesStar = "list_fav_selected";
						break;
						}
				  
					}
				  
					$("#list_wrapper").append("<li class=\"list_item " + data.drinkDetails[i].glass + "\" id=\"" + data.drinkDetails[i].id + "\"><span class=\"list_glass\">" + data.drinkDetails[i].drinkName + "</span><span class=\"" + favoritesStar + "\"></span></li>");
				  
				  }
			  }
			  else //only returned on object
			  {
			  
				favoritesStar = "list_fav";
			  
				  for (x = 0; x < favoritesArray.length; x++) {
				  
					if (data.drinkDetails.id == favoritesArray[x]) {
						favoritesStar = "list_fav_selected";
						break;
					  }
				  
				  }
			  
				$("#list_wrapper").append("<li class=\"list_item " + data.drinkDetails.glass + "\" id=\"" + data.drinkDetails.id + "\"><span class=\"list_glass\">" + data.drinkDetails.drinkName + "</span><span class=\"" + favoritesStar + "\"></span></li>");
			  }
			  
			  
			  
			  if (showDetails) showDetail($(".list_item").get(0));
			  else removeLoadingMask();
			  
			  if ($("#list_wrapper li").length >= LIMIT) //add the add more link
			  $("#list_wrapper").append("<li id=\"loadMore\">Load More...</li>");
			  
			  //add events
			  list_item_events();
			  //if portrait show pop up
			  if (css_orientation == "port") $(".port #wrapper").fadeIn();
			  
			  
			  }
			  else 
				removeLoadingMask();
			  
			  });
	
}

function processIngredients(requestUrl) {

    $.getJSON(requestUrl, function (data) {

			  if (data != null) {
			  	$("#ing-inner-wrapper-select").append("<option value=\"-1\" class=\"ing-item\">Choose One...</option>");
				for (i = 0; i < data.ingredient.length; i++)
					$("#ing-inner-wrapper-select").append("<option value=\"" + data.ingredient[i].id + "\" class=\"ing-item\">" + data.ingredient[i].name + "</option>");
 
					//add events
					list_item_events();
				}
			  
			  });
	
}

//adds favorites to the array on start up

function setUpFavorites() {
	
    db.transaction(
				   
				   function (transaction) {
				   transaction.executeSql("SELECT * FROM tblFavorites;", [], function (transaction, results) {
										  for (i = 0; i < results.rows.length; i++) {
										  var row = results.rows.item(i);
										  favoritesArray.push(row.drink_id);
										  
										  }
										  
										  })
				   });
	
}

function getAQuote() {
	
    var requestUrl = ROOT_URL + "quotes";
    //call to get details
    $.get(requestUrl, function (data) {
		  //data in this case is drinkdetail
		  if (data != null) $("#quote").empty().append(data);
		  
		  }); //end ajax
}

/************************************************/
/************  DRINK DETAIL BUILDING   **********/
/************************************************/

//get the drink details sets it to the screen

function addEditButtonEvents() {
	
    $("#addNewIng").unbind().bind(START_EVENT, function (e) {
								  e.preventDefault(); //prevent copy and mag from showing
								  $(this).addClass("ingTouch");
								  }).bind(END_EVENT, function (e) {
										  e.preventDefault(); //prevent copy and mag from showing
										  $(this).removeClass("ingTouch");
										  $(".stage-1").fadeOut(function () {
																$(".stage-2").fadeIn();
																
																});
										  
										  });
	
    $(".ing-back").unbind().bind(START_EVENT, function (e) {
								 e.preventDefault(); //prevent copy and mag from showing
								 $(this).addClass("ingTouch");
								 }).bind(END_EVENT, function (e) {
										 e.preventDefault(); //prevent copy and mag from showing
										 $(this).removeClass("ingTouch");
										 $(".stage-2").fadeOut(function () {
															   $(".stage-1").fadeIn();
															   
															   });
										 
										 });
	
}

function showDetail(that) {

    if (PAGING_TYPE == PAGING_TYPE_ALL || PAGING_TYPE == PAGING_TYPE_CATEGORY || PAGING_TYPE == PAGING_TYPE_SEARCH) {
        //if this is a drink not an ing or cat
        var drinkId = $(that).attr("id");
		//flip paper to front
		$("#ing-inner-wrapper").fadeOut(function(){
										$("#paper_wrapper .paper").addClass("flip-front").removeClass("flip-back");
									});//hide any other popups
		
		
		
        var sharedString = "";
        if (IS_SHARED_DRINK) sharedString = "?detailTypeShared=true"
			
			var requestUrl = ROOT_URL + "drinks/details" + drinkId + sharedString;
		
		
        //call to get details
        $.getJSON(requestUrl, function (data) {
				  //data in this case is drinkdetail
				   
				  if (data != null) {
				 
					  drinkUID = data.uid;
					  $(".submit_rate").text("Rate");
					  $(".ratings").removeClass("hidden");
					  $(".list_email").removeClass("hidden");
				  
					  selectedDrinkDetails = data; //set current drinkdetail
					  $(".drink-title").empty().append(data.drinkName);
					  $(".edit-drink-title").val(data.drinkName);
						
						$(".drink-type").text(data.drinkType);
				  $(".edit-drink-type").text(data.drinkType);
						
					  
					  $(".drink-desc .scroll-child").empty().append(data.instructions);
					  $(".edit-drink-desc").val(data.instructions);
					  
					  $(".ing-wrapper .scroll-child").empty().append(data.ingredients);
					  $(".ing-wrapper .scroll-child li").attr("class", "ing");
					  $(".edit-ing-wrapper .scroll-child").empty().append(data.ingredients);
					  $(".edit-ing-wrapper .scroll-child li").attr("class", "edit-ing");
					  $(".edit-ing-wrapper .scroll-child li").addClass("ing-choices");				  
					  
					  $("#drink_id_input").val(data.id);
					  
					  setRating(data.rating);
					  
					  editchosenIngs.refresh();
					  chosenIngs.refresh();				  
				  
					  //set edit button state only show if this user can edit it
					  if (data.uid != null && data.uid == deviceUID) 
							$(".frontbutton").show();
					  else 
							$(".frontbutton").hide();
				  
				  }
				  
				  $(".list_fav_selected").removeClass("list-item-loading");
				  $(".list_fav").removeClass("list-item-loading");
				  removeLoadingMask();
				  addEditButtonEvents();
				  
				  }); //end ajax
    }
    else if (PAGING_TYPE == PAGING_TYPE_ING) {
		
        //if this is a drink not an ing or cat
        var drinkId = $(that).attr("id");
		
        var requestUrl = ROOT_URL + "drinks/ingsId" + drinkId + "?typeName=" + TYPE_NAME + "&startIndex=0";
        showDrinkList(requestUrl);
		
		
    }
	
    getAQuote();
	
}

function setRating(data) {
	
    //reset stars first
    $(".star").removeClass("star_on");
    $(".star").removeClass("star_half_on"); /************** set rating ************/
	
    for (i = 0; i < $(".star").length; i++) {
		
        if (i < data - 1) {
			
            $($(".star").get(i)).addClass("star_on");
			
        }
    }
	
    var tmp = data % 2 + "";
    var fraction = tmp.split(".")
	
	
    if (fraction[1] != undefined && eval("." + fraction[1]) >= .5) $($(".star").get(Math.floor(data++))).addClass("star_half_on");
	
    if (data == 1) $("#rate_1").addClass("star_on");
    if (data == 5) $("#rate_5").addClass("star_on");
	
	if(data>1)
		data=data-1;
	if(data=='NaN')
		data=0;
	var result=Math.round(data*10)/10;
	
	$(".rate_number").text(result);
	$(".rate_number").show();
	$(".drink-type").show();
    /***************************************/
}

//adds loading spinner

function showLoadingMask() {
    $(".x-mask").empty();
    $("body").append("<div class='x-mask modal_pos'><div class='loading'>Loading...</div></div>");
    //if the ajax never returns close window
    window.clearTimeout(screenTimeout);
    screenTimeout = window.setTimeout("addCloseButton()", 5000);
}

function showStartMask() {
    $(".x-mask").empty();
    $("body").append("<div class='x-mask modal_pos'><div class='force-close'>Rememeber, please drink responsibly.<br/><br/>Click to start.</div></div>");
    $(".force-close").bind(END_EVENT, function () {
						   removeLoadingMask();
						   });
}

//removes loading mask

function removeLoadingMask() {
    $(".search-input").removeClass("search-loader");
    $(".x-mask").remove();
}

function addCloseButton() {
    $(".x-mask").empty().append("<div class=\"force-close\">Oops!! Looks like something bad happened. <br/>Click here to close this window and try again.</div>");
    $(".force-close").bind(END_EVENT, function () {
						   removeLoadingMask();
						   });
}

PhoneGap.addConstructor(function () {
						deviceInfo();
						});

function deviceInfo() {
    debug.log("deviceInfo");
	
    deviceName = device.name;
    deviceUID = device.uuid;
    deviceVersion = device.version;
}

function setClientIp(ip) {
    clientIp = ip;
}

function setUpEdit() {
	
    var data = selectedDrinkDetails;
	
    $(".edit-drink-title").val(data.drinkName);
    $(".edit-drink-desc").val(data.instructions);
    $(".edit-ing-wrapper .scroll-child").empty().append(data.ingredients);
    $(".edit-ing-wrapper .scroll-child li").attr("class", "edit-ing");
	
}