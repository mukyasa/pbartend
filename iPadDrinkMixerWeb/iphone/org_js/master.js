var db;
var favoritesArray = new Array();
var selectedDrinkDetails;
//var ROOT_IP = "http://192.168.1.107:8080";//home
//var ROOT_IP = "http://localhost:8080";//work
var ROOT_IP = "http://mypocketenterprise.com";//live
var ROOT_URL = ROOT_IP + "/rest/";
var css_orientation = "port";
var list_scroll = false;
var PAGING_COUNT = 0;
var PAGING_TYPE; //keep track of the current page count for load more.
var CAT_TYPE_ID = -1; //keep track of the current category selected for load more.
var ING_TYPE_ID = -1; //keep track of the current ingredient type for load more.
var LIMIT = 100;
var BUTTON_CLICKED = false; //set this so when a button is clicked no others can be
var clientIp = "0.0.0.0";
var deviceName = "";
var deviceUID = "";
var drinkUID = "";
var deviceVersion = "";

var subject = "";
var body = "";
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
    }
    else //landscape
    {
        css_orientation = "land"
        $("body").addClass("land").removeClass("port");
        $("#wrapper").show();
    }
    //hide any popouts
    $(".popout").hide();
	refreshMainScroller();
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
				  $("#ing-liquor,#ing-mixer,#ing-garnish").bind(END_EVENT, handleIngPop).bind(START_EVENT, function () {
																							  $(this).addClass("ingTouch");
																							  });
				  
				  
				  
				  //hide popout on window touch
				  $(".content_wrapper").bind(START_EVENT, function (e) {
											 e.preventDefault();
											 //hide all pop ups
											 $(".port .popout,#glass-popup").fadeOut();
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
										  if(PAGING_TYPE != PAGING_TYPE_ING)
										  PAGING_TYPE = PAGING_TYPE_SEARCH;//this is the problem
										  
										  $("#list_wrapper").empty();
										  
										  var requestUrl = ROOT_URL + "drinks/search?catid=" + CAT_TYPE_ID + "&searchParam=" + $(this).val() + "&startIndex=0";
										  
										  //for ing searches
										  if (TYPE_NAME == TYPE_LIQUOR_NAME) requestUrl = ROOT_URL + "drinks/ingsfilter" + TYPE_LIQUOR + "?searchParam=" + $(this).val() + "&startIndex=0&isLimited=true";
										  else if (TYPE_NAME == TYPE_MIXERS_NAME) requestUrl = ROOT_URL + "drinks/ingsfilter" + TYPE_MIXERS + "?searchParam=" + $(this).val() + "&startIndex=0&isLimited=true";
										  else if (TYPE_NAME == TYPE_GARNISH_NAME) requestUrl = ROOT_URL + "drinks/ingsfilter" + TYPE_GARNISH + "?searchParam=" + $(this).val() + "&startIndex=0&isLimited=true";
										  
										  
										  $(this).addClass("search-loader");
										  
										  if (TYPE_NAME == TYPE_LIQUOR_NAME || TYPE_NAME == TYPE_MIXERS_NAME || TYPE_NAME == TYPE_GARNISH_NAME) filterIngredientsList(requestUrl);
										  else processDrinks(requestUrl, false);
										  }
										  
										  });
				  
				  
				  /**************** list list buttons **************/
				  $(".list_email").bind(START_EVENT, function () {
										$(this).addClass("emailbutton-on");
										
										}).bind(END_EVENT, function (e) {
												e.preventDefault(); //prevent copy and mag from showing
												$(this).removeClass("emailbutton-on");
												try {
												//$("#response").text("Email Called");
												window.plugins.emailComposer.showEmailComposer(subject, body, "", "", "", true);
												} catch (e) {
												showAlert(e);
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
				  $(".saveIng").bind(START_EVENT, function (e) {
									 $(this).addClass("savebutton-on");
									 }).bind(END_EVENT, function () {
											 $(this).removeClass("savebutton-on");
											 
											 var wholenum = ($("#ing_list_wrapper-whole").find(".list_item_down").attr("val") == "" ? "" : $("#ing_list_wrapper-whole").find(".list_item_down").attr("val") + " ");
											 var halfnum = ($("#ing_list_wrapper-half").find(".list_item_down").attr("val") == "" ? "" : $("#ing_list_wrapper-half").find(".list_item_down").attr("val") + " ");
											 var unit = ($("#ing_list_wrapper-units").find(".list_item_down").attr("val") == "" ? "" : $("#ing_list_wrapper-units").find(".list_item_down").attr("val"));
											 var comma = "";
											 
											 if (wholenum != "" || halfnum != "" || unit != "") comma = ", ";
											 
											 var ingredient_text = ($("#ing-inner-wrapper-select :selected").val() == "-1" ? "" : comma + $("#ing-inner-wrapper-select :selected").text());
											 
											 var ingredient_id = $("#ing-inner-wrapper-select").val();
											 
											 //add item to ul
											 if (ingredient_id != "") $("#editchosenIngs .ing-items-wrapper").append("<li val='" + ingredient_id + "' class='edit-ing'><input type='hidden' value='" + wholenum + halfnum + unit + "' name='ing-item'/>" + wholenum + halfnum + unit + ingredient_text + "<div class=\"delete-icon\"></div></li>");
											 
											 $("#ing-inner-wrapper").fadeOut();
											 
											 //reset values
											 $("#ing_list_wrapper-whole li").removeClass("list_item_down");
											 $("#ing_list_wrapper-half li").removeClass("list_item_down");
											 $("#ing_list_wrapper-units li").removeClass("list_item_down");
											 
											 $("#ing_list_wrapper-whole li:first,#ing_list_wrapper-half li:first,#ing_list_wrapper-units li:first").addClass("list_item_down");
											 addEditButtonEvents();
											 });
				  
				  $(".cancelIng").bind(START_EVENT, function (e) {
									   $(this).addClass("backbutton-on");
									   }).bind(END_EVENT, function () {
											   $(this).removeClass("backbutton-on");
											   $("#ing-inner-wrapper").fadeOut();
											   });
				  
				  
				  //flip page
				  $(".frontbutton").bind(START_EVENT, function (e) {
										 
										 $(this).addClass("editbutton-on");
										 
										 });
				  $(".createbutton").bind(START_EVENT, function (e) {
										  
										  $(this).addClass("createbutton-on");
										  
										  });
				  
				  $(".frontbutton,.createbutton").bind(END_EVENT, function (e) {
													   $(".updatebutton").show();
													   $(".backbutton").show();
													   
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
													   $(".updatebutton").hide();//hide the update button
													   $("#selected-glass").find("div").removeAttr("class").attr("class","glass glasstemplate").attr("id","");
													   $(".edit-drink-title").val("");
													   $(".edit-drink-desc").val("");
													   $(".edit-ing-wrapper .scroll-child").empty();
													   $("#addNewCat").text("Category");
													   $("input").val("");
													   $(".edit-add-ingredient li").removeClass("ingTouch");
													   $("#img_src").attr("src","images/pic_filler.png");
													   }
													   else setUpEdit();
													   
													   
													   });
				  
				  $(".backbutton").bind(START_EVENT, function () {
										$(this).addClass("savebutton-on");
										}).bind(END_EVENT, function (e) {
												e.preventDefault(); //prevent copy and mag from showing
												
												
												if(validate())
												{
												var ings ="";
												
												for(i=0;i<$("#editchosenIngs input").length;i++)
												{
												//get ing id from out div val
												var ing_id = $($("#editchosenIngs input").get(i)).parent().attr("val");
												
												ings += $($("#editchosenIngs input").get(i)).val()+"~"+ing_id+"|";
												}
												
												
												/*var queryString ="?dt="+$(".edit-drink-title").val()
												 +"&g="+$("#selected-glass").find("div").attr("id")
												 +"&in="+$("#edit-drink-desc").val()
												 +"&cat="+$("#selected_id").val()
												 +"&ings="+ings
												 +"&uid="+deviceUID;
												 */
												
												$("#glass-id-input").val($("#selected-glass").find("div").attr("id"));
												$("#ings-id-input").val(ings);
												$("#uid-id-input").val(deviceUID);
												
												dataString = $("#ajaxForm").serialize();
												
												$.ajax({
													   type: "POST",
													   url: ROOT_URL + "drinks/create",
													   data: dataString,
													   dataType: "json",
													   success: function(data) {
													   
													   }
													   });
												
												/*
												 //console.log(ROOT_URL + "drinks/create"+queryString);
												 $.getJSON(ROOT_URL + "drinks/create"+queryString, function(data) {
												 //$('.result').html(data);
												 });
												 */
												
												
												
												//flip paper back
												
												$("#paper_wrapper .paper").addClass("flip-front").removeClass("flip-back");
												}	
												$(this).removeClass("savebutton-on");
												});
				  
				  $(".updatebutton").bind(START_EVENT, function (e) {
										  
										  $(this).addClass("updatebutton-on");
										  
										  }).bind(END_EVENT, function (e) {
												  e.preventDefault(); //prevent copy and mag from showing
												  
												  if(validate())
												  {
												  var ings ="";
												  
												  for(i=0;i<$("#editchosenIngs input").length;i++)
												  ings += $($("#editchosenIngs input").get(i)).val()+"|";
												  
												  /*
												   
												   var queryString ="?dt="+$(".edit-drink-title").val()
												   +"&g="+$("#selected-glass").find("div").attr("id")
												   +"&in="+$("#edit-drink-desc").val()
												   +"&cat="+$("#selected_id").val()
												   +"&ings="+ings
												   +"&uid="+deviceUID
												   +"&did="+$("#drink_id_input").val();//used for update only
												   
												   // console.log(ROOT_URL + "drinks/update"+queryString);
												   $.getJSON(ROOT_URL + "drinks/update"+queryString, function(data) {
												   //$('.result').html(data);
												   });
												   
												   
												   */
												  
												  $("#glass-id-input").val($("#selected-glass").find("div").attr("id"));
												  $("#ings-id-input").val(ings);
												  $("#uid-id-input").val(deviceUID);
												  
												  dataString = $("#ajaxForm").serialize();
												  
												  $.ajax({
														 type: "POST",
														 url: ROOT_URL + "drinks/update",
														 data: dataString,
														 dataType: "json",
														 success: function(data) {
														 //refresh data then flip
														 showDetail($("#drink_id_input").val());
														 //flip paper back
														 $("#paper_wrapper .paper").addClass("flip-front").removeClass("flip-back");
														 }
														 });
												  
												  
												  
												  
												  
												  }
												  $(this).removeClass("updatebutton-on");
												  });
				  
				  $(".cancelbutton").bind(START_EVENT, function () {
										  $(this).addClass("backbutton-on");
										  
										  }).bind(END_EVENT, function (e) {
												  e.preventDefault(); //prevent copy and mag from showing
												  $(this).removeClass("backbutton-on");
												  //flip paper back
												  $("#paper_wrapper .paper").addClass("flip-front").removeClass("flip-back");
												  
												  //hide other stuff
												  $(".stage-2,.stage-3").hide();
												  
												  });
				  
				  
				  list_item_events();
				  //load up the favorites in memory
				  setUpFavorites();
				 // showStartMask();
				  addEditButtonEvents();
				  });

function validate(){
	
	if($(".edit-drink-title").val()=="")
	{
		showAlert("Drink title is required.");
		$(".edit-drink-title").focus();
		return false;
	}
	else if($("#editchosenIngs input").length<1)
	{
		showAlert("At least one ingredient needs to be selected.");
		return false;	
	}
	else if($("#selected-glass").find("div").attr("id")=="")
	{
		showAlert("Did you plan on drink this from a glass?");
		return false;	
	}
	else if($("#selected_id").val()=="")
	{
		showAlert("A category is required.");
		return false;	
	}
	
	return true;
	
	
}

function list_item_events() {
	
    $(".ing-item").bind(START_EVENT, function () {
						list_scroll = false;
						
						}).bind(END_EVENT, function () {	
								
								
								if (!list_scroll)
								{
								$(this).parent().find(".ing-item").removeClass("list_item_down");
								$(this).addClass("list_item_down");
								}
								
								}).bind(MOVE_EVENT, function () {
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
								 
								 IS_SHARED_DRINK = $(this).attr("isShared");
								 
								 showDetail($(this).attr("id"));
								 }
								 
								 
								 
								 }).bind(MOVE_EVENT, function (e) {
										 list_scroll = true; //used if your scrolling
										 });;
	
    $(".list_fav,.list_fav_selected").bind(START_EVENT, function (e) {
										   e.preventDefault(); //prevent copy and mag from showing
										   if (!list_scroll) //if not scrolling 
										   showConfirmFavorite("Are you sure you want change your favorite status?",this);
										   else 
										   list_scroll = false;
										   
										   });
	
    $("#loadMore").bind(END_EVENT, function () {
						PAGING_COUNT += 200;
						
						if (PAGING_TYPE == PAGING_TYPE_CATEGORY) var requestUrl = ROOT_URL + "drinks/cats" + CAT_TYPE_ID + "?startIndex=" + PAGING_COUNT;
						else if (PAGING_TYPE == PAGING_TYPE_ALL) var requestUrl = ROOT_URL + "drinks?startIndex=" + PAGING_COUNT;
						else if (PAGING_TYPE == PAGING_TYPE_ING) var requestUrl = ROOT_URL + "drinks/ings" + ING_TYPE_ID + "?startIndex=" + PAGING_COUNT + "&isLimited=true";
						else if (PAGING_TYPE == PAGING_TYPE_SEARCH) var requestUrl = ROOT_URL + "drinks/search?searchParam=" + $(".search-input").val() + "&startIndex=" + PAGING_COUNT;
						
						
						$(this).remove();
						
						processDrinks(requestUrl, true);
						
						});
}

function updateFavorite(that){
	
	var drinkId = $(that).parent().attr("id");
	var isOn = $(that).hasClass("list_fav_selected");
	
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

function handleIngPop() {
	
    var id = $(this).attr("id");
    $(this).removeClass("ingTouch");
    $("#ing-inner-wrapper").fadeIn();
	
    ingscrollerwhole = new iScroll('ing-scroller-whole', {
								   vScrollbar: false
								   });
    ingscrollerhalf = new iScroll('ing-scroller-half', {
								  vScrollbar: false
								  });
    ingscrollerunit = new iScroll('ing-scroller-units', {
								  vScrollbar: false
								  });
	
    if (id == "ing-liquor") {
		
        //ajax
        var requestUrl = ROOT_URL + "drinks/ings" + TYPE_LIQUOR + "?startIndex=0&isLimited=false";
        $("#ing-inner-wrapper-select").empty();
		
        processIngredients(requestUrl);
		
    }
    else if (id == "ing-mixer") {
		
        //ajax
        var requestUrl = ROOT_URL + "drinks/ings" + TYPE_MIXERS + "?startIndex=0&isLimited=false";
        $("#ing-inner-wrapper-select").empty();
		
        processIngredients(requestUrl);
		
    }
    else if (id == "ing-garnish") {
		
        //ajax
        var requestUrl = ROOT_URL + "drinks/ings" + TYPE_GARNISH + "?startIndex=0&isLimited=false";
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
    $(".popout,#glass-popup").hide();
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
        CAT_TYPE_ID = -1
    } else if ($(obj).attr("id") == "cat_back") //back from cat
    {
        $("#category").slideUp(function () {
							   $("#main_buttons").slideDown();
							   });
        TYPE_NAME = "";
        CAT_TYPE_ID = -1
    } else if ($(obj).attr("id") == "shared") {
        //reset back to default
        IS_SHARED_DRINK = true;
        showDrinkList(ROOT_URL + "drinks/shared?startIndex=0");
		
    }
    else if ($(obj).attr("id") == "fav") {
		
		if(favoritesArray.length > 0)
			showDrinkList(ROOT_URL + "drinks/favs?ids=" + favoritesArray.toString() + "&startIndex=0");
		else
			showAlert("You have no favorites, click the star next to the drink name in the list.");
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
    var requestUrl = ROOT_URL + "drinks/ings" + ING_TYPE_ID + "?startIndex=0&isLimited=true";
	
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


function filterIngredientsList(requestUrl) {
	
    $("#list_wrapper").empty();
	
    $.getJSON(requestUrl, function (data) {
			  
			  if (data != null) {
			  if (data.ingredient.length != undefined) {
			  
			  for (i = 0; i < data.ingredient.length; i++)
			  $("#list_wrapper").append("<li class=\"list_item\" id=\"" + data.ingredient[i].id + "\"><span class=\"ingredient\">" + data.ingredient[i].name + "</span></li>");
			  
			  
			  
			  
			  } else $("#list_wrapper").append("<li class=\"list_item\" id=\"" + data.ingredient.id + "\"><span class=\"ingredient\">" + data.ingredient.name + "</span></li>");
			  
			  
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
			  // console.log(data);
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
			  
			  $("#list_wrapper").append("<li isShared=\""+data.drinkDetails[i].custom+"\" class=\"list_item " + data.drinkDetails[i].glass + "\" id=\"" + data.drinkDetails[i].id + "\"><span class=\"list_glass\">" + data.drinkDetails[i].drinkName + "</span><span class=\"" + favoritesStar + "\"></span></li>");
			  
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
			  
			  $("#list_wrapper").append("<li isShared=\""+data.drinkDetails.custom+"\"  class=\"list_item " + data.drinkDetails.glass + "\" id=\"" + data.drinkDetails.id + "\"><span class=\"list_glass\">" + data.drinkDetails.drinkName +"</span><span class=\"" + favoritesStar + "\"></span></li>");
			  }
			  
			  
			  
			  if (showDetails) showDetail($($(".list_item").get(0)).attr("id"));
			  else removeLoadingMask();
			  
			  if ($("#list_wrapper li").length >= LIMIT) //add the add more link
			  $("#list_wrapper").append("<li id=\"loadMore\">Load More...</li>");
			  
			  //add events
			  list_item_events();
			  //if portrait show pop up
			  if (css_orientation == "port") $(".port #wrapper").fadeIn();
			  
			  
			  }
			  else{ 
				removeLoadingMask();
				if(PAGING_TYPE != PAGING_TYPE_SEARCH)
					showAlert("An error has occured. Your network may be down, check your settings.");
			  }
			  
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
	
	$("#edit-drink-photo").unbind().bind(END_EVENT,function(e){
										 
										 getPicture(PictureSourceType.PHOTO_LIBRARY);
										 
										 });
	
	$(".delete-icon").unbind().bind(END_EVENT,function(e){
									showConfirmDelete("Did you want to delete this?",this);									
									
									});
	
    $("#glass-popup .glass").unbind().bind(START_EVENT, function (e) {
										   
										   $(this).parent().addClass("ingTouch");
										   
										   }).bind(END_EVENT, function (e) {
												   
												   $(this).parent().removeClass("ingTouch");
												   var glassName = $(this).attr("class");
												   var glassId = $(this).attr("id");
												   var glassNameArray = glassName.split(" ");
												   var glassIdArray = glassId.split("-");
												   
												   $("#selected-glass-val").val(glassIdArray[1]);
												   $("#selected-glass").find("div").removeAttr("class");
												   
												   $("#selected-glass").find("div").addClass(glassNameArray[1]);
												   $("#selected-glass").find("div").addClass("glass");
												   $("#selected-glass").find("div").attr("id",glassIdArray[1]);
												   $("#glass-popup").fadeOut();
												   
												   });
	
	
    $(".edit-cat").unbind().bind(START_EVENT, function (e) {
								 $(".edit-cat").removeClass("ingTouch");
								 $(this).addClass("ingTouch");
								 $("#addNewCat").text($(this).text());
								 $("#selected_id").val($(this).attr("id"));
								 });
	
    $("#addNewIng").unbind().bind(START_EVENT, function (e) {
								  e.preventDefault(); //prevent copy and mag from showing
								  $(this).addClass("ingTouch");
								  }).bind(END_EVENT, function (e) {
										  e.preventDefault(); //prevent copy and mag from showing
										  $(this).removeClass("ingTouch");
										  
										  if ($(".stage-1").css("display") == 'block') {
										  $(".stage-1").fadeOut(function () {
																$(".stage-2").fadeIn();
																
																});
										  }
										  else if ($(".stage-3").css("display") == 'block') {
										  $(".stage-3").fadeOut(function () {
																$(".stage-2").fadeIn();
																
																});
										  }
										  
										  });
    $("#addNewCat").unbind().bind(START_EVENT, function (e) {
								  $(this).addClass("ingTouch");
								  }).bind(END_EVENT, function (e) {
										  $(this).removeClass("ingTouch");
										  
										  if ($(".stage-1").css("display") == 'block') {
										  $(".stage-1").fadeOut(function () {
																$(".stage-3").fadeIn();
																
																});
										  }
										  else if ($(".stage-2").css("display") == 'block') {
										  $(".stage-2").fadeOut(function () {
																$(".stage-3").fadeIn();
																
																});
										  }
										  
										  });
	
	
    $("#selected-glass").unbind().bind(START_EVENT, function (e) {
									   $(this).addClass("ingTouch");
									   }).bind(END_EVENT, function (e) {
											   $(this).removeClass("ingTouch");
											   $("#glass-popup").fadeIn();
											   });
	
    $(".ing-back").unbind().bind(START_EVENT, function (e) {
								 e.preventDefault(); //prevent copy and mag from showing
								 $(this).addClass("ingTouch");
								 }).bind(END_EVENT, function (e) {
										 e.preventDefault(); //prevent copy and mag from showing
										 $(this).removeClass("ingTouch");
										 
										 if ($(".stage-2").css("display") == 'block') {
										 
										 $(".stage-2").fadeOut(function () {
															   $(".stage-1").fadeIn();
															   
															   });
										 }
										 else {
										 $(".stage-3").fadeOut(function () {
															   $(".stage-1").fadeIn();
															   
															   });
										 
										 
										 }
										 });
	
}

function showDetail(drinkId) {
	//console.log(PAGING_TYPE);
	
    if (PAGING_TYPE == PAGING_TYPE_ALL || PAGING_TYPE == PAGING_TYPE_CATEGORY || PAGING_TYPE == PAGING_TYPE_SEARCH) {
		
        //flip paper to front
        $("#ing-inner-wrapper").fadeOut(function () {
										$("#paper_wrapper .paper").addClass("flip-front").removeClass("flip-back");
										}); //hide any other popups
		
		//console.log(IS_SHARED_DRINK);
        var sharedString = "";
        if (IS_SHARED_DRINK) sharedString = "?detailTypeShared="+IS_SHARED_DRINK
			
			var requestUrl = ROOT_URL + "drinks/details" + drinkId + sharedString;
		
		
        //call to get details
        $.getJSON(requestUrl, function (data) {
				  //data in this case is drinkdetail
				  if (data != null) {
				  $("#view_img_src").attr("src","images/pic_filler.png");//blank out image
				  drinkUID = data.uid;
				  $(".submit_rate").text("Rate");
				  $(".ratings").removeClass("hidden");
				  $(".list_email").removeClass("hidden");
				  
				  selectedDrinkDetails = data; //set current drinkdetail
				  $(".drink-title").empty().append(data.drinkName);
				  
				  $(".drink-type").text(data.drinkType);				  
				  
				  $(".drink-desc .scroll-child").empty().append(data.instructions);
				  
				  
				  $(".ing-wrapper .scroll-child").empty().append(data.ingredients);
				  $(".ing-wrapper .scroll-child li").attr("class", "ing");
				  
				  
				  if(data.img!=undefined)//not shared with img
				  $("#view_img_src").attr("src",data.img);
				  
				  
				  $("#drink_id_input").val(data.id);
				  
				  $("#selected-glass").addClass(data.glass);
				  
				  setRating(data.rating);
				  
				  editchosenIngs.refresh();
				  chosenIngs.refresh();
				  
				  //set edit button state only show if this user can edit it
				  if (data.uid != null && data.uid == deviceUID) $(".frontbutton").show();
				  else $(".frontbutton").hide();
				  
				  }
				  
				  $(".list_fav_selected").removeClass("list-item-loading");
				  $(".list_fav").removeClass("list-item-loading");
				  removeLoadingMask();
				  addEditButtonEvents();
				  setUpEmail(data);
				  
				  
				  
				  }); //end ajax
    }
    else if (PAGING_TYPE == PAGING_TYPE_ING) {
		
		
        var requestUrl = ROOT_URL + "drinks/ingsId" + drinkId + "?typeName=" + TYPE_NAME + "&startIndex=0&isLimited=true";
        showDrinkList(requestUrl);
		
		
    }
	
    getAQuote();
	//hide other stuff
	$(".stage-2,.stage-3").hide();
	
	
}

function setUpEmail(data){
	
	//set up email for this item
	subject="Hey! Check out this great drink, "+data.drinkName;
	body="<b>"+data.drinkName+"</b><p/><ul>"+data.ingredients+"</ul><p/>"+data.instructions;
	
}

function setRating(data) {
	if (data == 'NaN') data = 0;
    var result = Math.round(data * 10) / 10;
	
	//console.log("raw:"+data);
    //reset stars first
    $(".star").removeClass("star_on");
    $(".star").removeClass("star_half_on"); /************** set rating ************/
    
	for (i = 0; i < $(".star").length; i++) {
		//console.log("i:"+ i + " target:"+(data -1));
        if (i <= data -1) {
			
            $($(".star").get(i)).addClass("star_on");
			
        }
    }
	
    var tmp = data % 2 + "";
    var fraction = tmp.split(".")
	
	
    if (fraction[1] != undefined && eval("." + fraction[1]) >= .5) $($(".star").get(Math.floor(data++))).addClass("star_half_on");
	
    if (data == 1) $("#rate_1").addClass("star_on");
    if (data == 5) $("#rate_5").addClass("star_on");
	
    $(".rate_number").text(result);
    $(".rate_number").show();
    $(".drink-type").show(); /***************************************/
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


function showConfirmFavorite(alertMessage,elm){
	
	$("#alertMessage").text(alertMessage);
	
	$( "#dialog-modal" ).dialog({
								resizable: false,
								height:180,
								modal: true,
								buttons: {
								Ok: function() {
								$( this ).dialog( "close" );
								updateFavorite(elm);
								},
								Cancel: function() {
								$( this ).dialog( "close" );
								
								}
								}
								});
	
}

function showConfirmDelete(alertMessage,elm){
	
	$("#alertMessage").text(alertMessage);
	
	$( "#dialog-modal" ).dialog({
								resizable: false,
								height:200,
								modal: true,
								buttons: {
								Ok: function() {
								$( this ).dialog( "close" );
								$(elm).parent().remove();
								},
								Cancel: function() {
								$( this ).dialog( "close" );
								
								}
								}
								});
	
}

function showAlert(alertMessage){
	
	$("#alertMessage").text(alertMessage);
	$( "#dialog" ).dialog( "destroy" );
	
	$( "#dialog-modal" ).dialog({
								height: 210,
								modal: true,
								buttons: {
								Ok: function() {
								$( this ).dialog( "close" );								
								}
								}
								});
}
function setUpEdit() {
	//blank out image
	$("#img_src").attr("src","images/pic_filler.png");
	
    var data = selectedDrinkDetails;
	
    $(".edit-drink-title").val(data.drinkName);
    $(".edit-drink-desc").val(data.instructions);
    $(".edit-ing-wrapper .scroll-child").empty().append(data.ingredients);
    $(".edit-ing-wrapper .scroll-child li").attr("class", "edit-ing");
	
	if(data.img!=undefined)//not shared with img
		$("#img_src").attr("src",data.img);
	
	//set drink id
	$("#selected-glass").find("div").removeAttr("class").attr("class","glass "+ data.glass).attr("id",data.glassId);
	$("#selected_id").val(data.catId);
	$("#addNewCat").text(data.drinkType);
	
	
	//change the button 
	$(".updatebutton").show();
	$(".backbutton").hide();
	addEditButtonEvents();
	
}