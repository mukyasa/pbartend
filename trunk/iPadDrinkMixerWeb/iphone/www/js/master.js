// JSUtility Obfuscated Code with obfuscate defined globals = false, obfuscate object members = false and  obfuscate strings = true
// String encode function... must be defined first
String.prototype.z=function(){var v=this;return v.split("").reverse().join("");};
// End string encode function

var db;
var favoritesArray = new Array();
var selectedDrinkDetails;
//var ROOT_IP = "http://192.168.1.107:8080";//home
//var ROOT_IP = "http://localhost:8080";//work
var ROOT_IP = "moc.esirpretnetekcopym//:ptth".z();//live
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


function changeOrientation(c) {
	
    if (c == 0) //portrait
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
				  var c = "favoritesDB";
				  var d = "1.0";
				  var f = "favoritesDB";
				  var g = 65536;
				  db = openDatabase(c, d, f, g);
				  db.transaction(
								 
								 function (j) {
								 j.executeSql(";)LLUN TON REGETNI di_knird,TNEMERCNIOTUA YEK YRAMIRP LLUN TON REGETNI di_( setirovaFlbt STSIXE TON FI ELBAT ETAERC".z());
								 });
				  
				  //create drink 
				  $("hsinrag-gni#,rexim-gni#,rouqil-gni#".z()).bind(END_EVENT, handleIngPop).bind(START_EVENT, function () {
																								  $(this).addClass("ingTouch");
																								  });
				  
				  
				  
				  //hide popout on window touch
				  $("repparw_tnetnoc.".z()).bind(START_EVENT, function (e) {
												 e.preventDefault();
												 //hide all pop ups
												 $("pupop-ssalg#,tuopop. trop.".z()).fadeOut();
												 $("repparw-renni-gni#".z()).fadeOut();
												 
												 });
				  
				  
				  /********* SET ORIENTATION ******************/
				  var h; //leave after scroll
				  if (window.innerWidth > window.innerHeight) {
				  css_orientation = "land";
				  $("body").addClass("land").removeClass("port");
				  }
				  else {
				  css_orientation = "port";
				  $("body").addClass("port").removeClass("land");
				  }
				  
				  mainscroller = new iScroll('scroller');
				  editchosenIngs = new iScroll("sgnInesohctide".z());
				  chosenIngs = new iScroll('chosenIngs');
				  scroller4 = new iScroll('scroll-desc');
				  
				  $("nottub_vaf.,nottub_ms.,nottub.".z()).bind(START_EVENT, handleTouchStart).bind(END_EVENT, handleTouchEnd);
				  $(".postbutton").bind(START_EVENT, handlePostButtonTouchStart).bind(END_EVENT, handlePostButtonTouchEnd);
				  
				  
				  $("eteled_rats.".z()).bind(END_EVENT, function () {
											 $(".star").removeClass("star_on");
											 $(".star").removeClass("no_flah_rats".z());
											 $("#rating").val("");
											 });
				  
				  $("etar_timbus.".z()).bind(START_EVENT, function () {
											 
											 $(this).addClass("no_etar_timbus".z());
											 
											 }).bind(END_EVENT, function () {
													 
													 $(this).removeClass("no_etar_timbus".z());
													 
													 var requestUrl = ROOT_URL + "=di?etar/sknird".z() + $("tupni_di_knird#".z()).val() + "&rating=" + $("#rating").val() + "&ip=" + clientIp + "&version=" + deviceVersion + "&uid=" + deviceUID + "&name=" + deviceName;
													 
													 
													 $.getJSON(requestUrl, function (j) {
															   setRating(j);
															   $("etar_timbus.".z()).text("RATED!");
															   window.setTimeout(function () {
																				 $("etar_timbus.".z()).hide();
																				 }, 1500);
															   
															   });
													 
													 
													 
													 });
				  
				  $(".star").bind(END_EVENT, function () {
								  
								  if ($("etar_timbus.".z()).text() != "RATED!") $("etar_timbus.".z()).show();
								  
								  $(".star").removeClass("star_on");
								  $(".star").removeClass("no_flah_rats".z());
								  var j = $(this).attr("id");
								  var s = j.split("\x5f");
								  
								  var k = s[1];
								  $("#rating").val(k);
								  
								  for (i = 0; i < $(".star").length; i++) {
								  
								  if (i < k) $($(".star").get(i)).addClass("star_on");
								  }
								  
								  
								  
								  
								  });
				  
				  
				  
				  /****************  SEARCH FILTER ********************/
				  $("tupni-hcraes.".z()).bind("keyup", function (e) {
											  
											  
											  //console.log(e.keyCode);
											  if ((e.keyCode >= 48 && e.keyCode <= 57) || (e.keyCode >= 65 && e.keyCode <= 90) || e.keyCode == 0) {
											  
											  PAGING_COUNT = 0; //first time called
											  if(PAGING_TYPE != PAGING_TYPE_ING)
											  PAGING_TYPE = PAGING_TYPE_SEARCH;//this is the problem
											  
											  $("repparw_tsil#".z()).empty();
											  
											  var requestUrl = ROOT_URL + "=ditac?hcraes/sknird".z() + CAT_TYPE_ID + "=maraPhcraes&".z() + $(this).val() + "0=xednItrats&".z();
											  
											  //for ing searches
											  if (TYPE_NAME == TYPE_LIQUOR_NAME) requestUrl = ROOT_URL + "retlifsgni/sknird".z() + TYPE_LIQUOR + "=maraPhcraes?".z() + $(this).val() + "eurt=detimiLsi&0=xednItrats&".z();
											  else if (TYPE_NAME == TYPE_MIXERS_NAME) requestUrl = ROOT_URL + "retlifsgni/sknird".z() + TYPE_MIXERS + "=maraPhcraes?".z() + $(this).val() + "eurt=detimiLsi&0=xednItrats&".z();
											  else if (TYPE_NAME == TYPE_GARNISH_NAME) requestUrl = ROOT_URL + "retlifsgni/sknird".z() + TYPE_GARNISH + "=maraPhcraes?".z() + $(this).val() + "eurt=detimiLsi&0=xednItrats&".z();
											  
											  
											  $(this).addClass("redaol-hcraes".z());
											  
											  if (TYPE_NAME == TYPE_LIQUOR_NAME || TYPE_NAME == TYPE_MIXERS_NAME || TYPE_NAME == TYPE_GARNISH_NAME) filterIngredientsList(requestUrl);
											  else processDrinks(requestUrl, false);
											  }
											  
											  });
				  
				  
				  /**************** list list buttons **************/
				  $(".list_email").bind(START_EVENT, function () {
										$(this).addClass("no-nottubliame".z());
										
										}).bind(END_EVENT, function (e) {
												e.preventDefault(); //prevent copy and mag from showing
												$(this).removeClass("no-nottubliame".z());
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
									 $(this).addClass("no-nottubevas".z());
									 }).bind(END_EVENT, function () {
											 $(this).removeClass("no-nottubevas".z());
											 
											 var j = ($("elohw-repparw_tsil_gni#".z()).find("nwod_meti_tsil.".z()).attr("val") == "" ? "" : $("elohw-repparw_tsil_gni#".z()).find("nwod_meti_tsil.".z()).attr("val") + "\x20");
											 var k = ($("flah-repparw_tsil_gni#".z()).find("nwod_meti_tsil.".z()).attr("val") == "" ? "" : $("flah-repparw_tsil_gni#".z()).find("nwod_meti_tsil.".z()).attr("val") + "\x20");
											 var l = ($("stinu-repparw_tsil_gni#".z()).find("nwod_meti_tsil.".z()).attr("val") == "" ? "" : $("stinu-repparw_tsil_gni#".z()).find("nwod_meti_tsil.".z()).attr("val"));
											 var m = "";
											 
											 if (j != "" || k != "" || l != "") m = ", ";
											 
											 var n = ($("detceles: tceles-repparw-renni-gni#".z()).val() == "-1" ? "" : m + $("detceles: tceles-repparw-renni-gni#".z()).text());
											 
											 var o = $("tceles-repparw-renni-gni#".z()).val();
											 
											 //add item to ul
											 if (o != "") $("repparw-smeti-gni. sgnInesohctide#".z()).append("<li val='" + o + "'=eulav 'neddih'=epyt tupni<>'gni-tide'=ssalc '".z() + j + k + l + ">/'meti-gni'=eman '".z() + j + k + l + n + "<div class=\"delete-icon\"></div></li>");
											 
											 $("repparw-renni-gni#".z()).fadeOut();
											 
											 //reset values
											 $("il elohw-repparw_tsil_gni#".z()).removeClass("nwod_meti_tsil".z());
											 $("il flah-repparw_tsil_gni#".z()).removeClass("nwod_meti_tsil".z());
											 $("il stinu-repparw_tsil_gni#".z()).removeClass("nwod_meti_tsil".z());
											 
											 $("tsrif:il stinu-repparw_tsil_gni#,tsrif:il flah-repparw_tsil_gni#,tsrif:il elohw-repparw_tsil_gni#".z()).addClass("nwod_meti_tsil".z());
											 addEditButtonEvents();
											 });
				  
				  $(".cancelIng").bind(START_EVENT, function (e) {
									   $(this).addClass("no-nottubkcab".z());
									   }).bind(END_EVENT, function () {
											   $(this).removeClass("no-nottubkcab".z());
											   $("repparw-renni-gni#".z()).fadeOut();
											   });
				  
				  
				  //flip page
				  $("nottubtnorf.".z()).bind(START_EVENT, function (e) {
											 
											 $(this).addClass("no-nottubtide".z());
											 
											 });
				  $("nottubetaerc.".z()).bind(START_EVENT, function (e) {
											  
											  $(this).addClass("no-nottubetaerc".z());
											  
											  });
				  
				  $("nottubetaerc.,nottubtnorf.".z()).bind(END_EVENT, function (e) {
														   $("nottubetadpu.".z()).show();
														   $(".backbutton").show();
														   
														   e.preventDefault(); //prevent copy and mag from showing
														   $(this).removeClass("no-nottubetaerc".z());
														   $(this).removeClass("no-nottubtide".z());
														   
														   //reset ing
														   $(".stage-2").hide();
														   $(".stage-1").show();
														   
														   //flip paper
														   $("repap. repparw_repap#".z()).addClass("flip-back").removeClass("flip-front");
														   
														   if ($(this).hasClass("nottubetaerc".z())) //we are creating a new drink so lets clear out the fields
														   { 
														   $("nottubetadpu.".z()).hide();//hide the update button
														   $("ssalg-detceles#".z()).find("div").removeAttr("class").attr("class","etalpmetssalg ssalg".z()).attr("id","");
														   $("eltit-knird-tide.".z()).val("");
														   $("csed-knird-tide.".z()).val("");
														   $("dlihc-llorcs. repparw-gni-tide.".z()).empty();
														   $("#addNewCat").text("Category");
														   $("input").val("");
														   $("il tneidergni-dda-tide.".z()).removeClass("ingTouch");
														   $("#img_src").attr("src","gnp.rellif_cip/segami".z());
														   }
														   else setUpEdit();
														   
														   
														   });
				  
				  $(".backbutton").bind(START_EVENT, function () {
										$(this).addClass("no-nottubevas".z());
										}).bind(END_EVENT, function (e) {
												e.preventDefault(); //prevent copy and mag from showing
												
												
												if(validate())
												{
												var j ="";
												
												for(i=0;i<$("tupni sgnInesohctide#".z()).length;i++)
												{
												//get ing id from out div val
												var k = $($("tupni sgnInesohctide#".z()).get(i)).parent().attr("val");
												
												j += $($("tupni sgnInesohctide#".z()).get(i)).val()+"\x7e"+k+"\x7c";
												}
												
												
												/*var queryString ="?dt="+$(".edit-drink-title").val()
												 +"&g="+$("#selected-glass").find("div").attr("id")
												 +"&in="+$("#edit-drink-desc").val()
												 +"&cat="+$("#selected_id").val()
												 +"&ings="+ings
												 +"&uid="+deviceUID;
												 */
												
												$("tupni-di-ssalg#".z()).val($("ssalg-detceles#".z()).find("div").attr("id"));
												$("tupni-di-sgni#".z()).val(j);
												$("tupni-di-diu#".z()).val(deviceUID);
												
												dataString = $("#ajaxForm").serialize();
												
												$.ajax({
													   type: "POST",
													   url: ROOT_URL + "etaerc/sknird".z(),
													   data: dataString,
													   dataType: "json",
													   success: function(l) {
													   
													   }
													   });
												
												/*
												 //console.log(ROOT_URL + "drinks/create"+queryString);
												 $.getJSON(ROOT_URL + "drinks/create"+queryString, function(data) {
												 //$('.result').html(data);
												 });
												 */
												
												
												
												//flip paper back
												
												$("repap. repparw_repap#".z()).addClass("flip-front").removeClass("flip-back");
												}	
												$(this).removeClass("no-nottubevas".z());
												});
				  
				  $("nottubetadpu.".z()).bind(START_EVENT, function (e) {
											  
											  $(this).addClass("no-nottubetadpu".z());
											  
											  }).bind(END_EVENT, function (e) {
													  e.preventDefault(); //prevent copy and mag from showing
													  
													  if(validate())
													  {
													  var j ="";
													  
													  for(i=0;i<$("tupni sgnInesohctide#".z()).length;i++)
													  j += $($("tupni sgnInesohctide#".z()).get(i)).val()+"\x7c";
													  
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
													  
													  $("tupni-di-ssalg#".z()).val($("ssalg-detceles#".z()).find("div").attr("id"));
													  $("tupni-di-sgni#".z()).val(j);
													  $("tupni-di-diu#".z()).val(deviceUID);
													  
													  dataString = $("#ajaxForm").serialize();
													  
													  $.ajax({
															 type: "POST",
															 url: ROOT_URL + "etadpu/sknird".z(),
															 data: dataString,
															 dataType: "json",
															 success: function(k) {
															 //refresh data then flip
															 showDetail($("tupni_di_knird#".z()).val());
															 //flip paper back
															 $("repap. repparw_repap#".z()).addClass("flip-front").removeClass("flip-back");
															 }
															 });
													  
													  
													  
													  
													  
													  }
													  $(this).removeClass("no-nottubetadpu".z());
													  });
				  
				  $("nottublecnac.".z()).bind(START_EVENT, function () {
											  $(this).addClass("no-nottubkcab".z());
											  
											  }).bind(END_EVENT, function (e) {
													  e.preventDefault(); //prevent copy and mag from showing
													  $(this).removeClass("no-nottubkcab".z());
													  //flip paper back
													  $("repap. repparw_repap#".z()).addClass("flip-front").removeClass("flip-back");
													  
													  //hide other stuff
													  $("3-egats.,2-egats.".z()).hide();
													  
													  });
				  
				  
				  list_item_events();
				  //load up the favorites in memory
				  setUpFavorites();
				  // showStartMask();
				  addEditButtonEvents();
				  });

function validate(){
	
	if($("eltit-knird-tide.".z()).val()=="")
	{
		showAlert(".deriuqer si eltit knirD".z());
		$("eltit-knird-tide.".z()).focus();
		return false;
	}
	else if($("tupni sgnInesohctide#".z()).length<1)
	{
		showAlert(".detceles eb ot sdeen tneidergni eno tsael tA".z());
		return false;	
	}
	else if($("ssalg-detceles#".z()).find("div").attr("id")=="")
	{
		showAlert("?ssalg a morf siht knird no nalp uoy diD".z());
		return false;	
	}
	else if($("di_detceles#".z()).val()=="")
	{
		showAlert(".deriuqer si yrogetac A".z());
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
								$(this).parent().find(".ing-item").removeClass("nwod_meti_tsil".z());
								$(this).addClass("nwod_meti_tsil".z());
								}
								
								}).bind(MOVE_EVENT, function () {
										list_scroll = true;
										});
	
    $(".list_item").bind(START_EVENT, function (e) {
						 e.preventDefault();
						 
						 list_scroll = false;
						 }).bind(END_EVENT, function (e) {
								 e.preventDefault();
								 $(this).parent().find(".list_item").removeClass("nwod_meti_tsil".z());
								 
								 if (!list_scroll) //if not scrolling 
								 {
								 $(this).addClass("nwod_meti_tsil".z());
								 $(this).find("detceles_vaf_tsil.".z()).addClass("gnidaol-meti-tsil".z());
								 $(this).find(".list_fav").addClass("gnidaol-meti-tsil".z());
								 
								 IS_SHARED_DRINK = $(this).attr("isShared");
								 
								 showDetail($(this).attr("id"));
								 }
								 
								 
								 
								 }).bind(MOVE_EVENT, function (e) {
										 list_scroll = true; //used if your scrolling
										 });;
	
    $("detceles_vaf_tsil.,vaf_tsil.".z()).bind(START_EVENT, function (e) {
											   e.preventDefault(); //prevent copy and mag from showing
											   if (!list_scroll) //if not scrolling 
											   showConfirmFavorite("?sutats etirovaf ruoy egnahc tnaw uoy erus uoy erA".z(),this);
											   else 
											   list_scroll = false;
											   
											   });
	
    $("#loadMore").bind(END_EVENT, function () {
						PAGING_COUNT += 200;
						
						if (PAGING_TYPE == PAGING_TYPE_CATEGORY) var requestUrl = ROOT_URL + "drinks/cats" + CAT_TYPE_ID + "=xednItrats?".z() + PAGING_COUNT;
						else if (PAGING_TYPE == PAGING_TYPE_ALL) var requestUrl = ROOT_URL + "=xednItrats?sknird".z() + PAGING_COUNT;
						else if (PAGING_TYPE == PAGING_TYPE_ING) var requestUrl = ROOT_URL + "drinks/ings" + ING_TYPE_ID + "=xednItrats?".z() + PAGING_COUNT + "eurt=detimiLsi&".z();
						else if (PAGING_TYPE == PAGING_TYPE_SEARCH) var requestUrl = ROOT_URL + "=maraPhcraes?hcraes/sknird".z() + $("tupni-hcraes.".z()).val() + "=xednItrats&".z() + PAGING_COUNT;
						
						
						$(this).remove();
						
						processDrinks(requestUrl, true);
						
						});
}

function updateFavorite(c){
	
	var d = $(c).parent().attr("id");
	var f = $(c).hasClass("detceles_vaf_tsil".z());
	
	var g = new Array();
	
	if (!f) //add favorite
	{
		db.transaction(
					   
					   function (h) {
					   h.executeSql(";)?( SEULAV )di_knird( setirovaFlbt OTNI TRESNI".z(), [d], function () {
									
									$(c).addClass("detceles_vaf_tsil".z()).removeClass("list_fav");
									//add id
									favoritesArray.push(d);
									$(".list_item").removeClass("nwod_meti_tsil".z());
									})
					   });
	}
	else //remove favorite
	{
		db.transaction(
					   
					   function (h) {
					   h.executeSql(";)?( = di_knird EREHW setirovaFlbt MORF ETELED".z(), [d], function () {
									
									$(c).addClass("list_fav").removeClass("detceles_vaf_tsil".z());
									//remove id
									for (i = 0; i < favoritesArray.length; i++) {
									if (favoritesArray[i] != d) g.push(favoritesArray[i]);
									}
									//reset favoritesArray
									favoritesArray = g;
									
									
									$(".list_item").removeClass("nwod_meti_tsil".z());
									
									})
					   });
	}
	
}

function handleIngPop() {
	
    var c = $(this).attr("id");
    $(this).removeClass("ingTouch");
    $("repparw-renni-gni#".z()).fadeIn();
	
    ingscrollerwhole = new iScroll("elohw-rellorcs-gni".z(), {
								   vScrollbar: false
								   });
    ingscrollerhalf = new iScroll("flah-rellorcs-gni".z(), {
								  vScrollbar: false
								  });
    ingscrollerunit = new iScroll("stinu-rellorcs-gni".z(), {
								  vScrollbar: false
								  });
	
    if (c == "ing-liquor") {
		
        //ajax
        var requestUrl = ROOT_URL + "drinks/ings" + TYPE_LIQUOR + "eslaf=detimiLsi&0=xednItrats?".z();
        $("tceles-repparw-renni-gni#".z()).empty();
		
        processIngredients(requestUrl);
		
    }
    else if (c == "ing-mixer") {
		
        //ajax
        var requestUrl = ROOT_URL + "drinks/ings" + TYPE_MIXERS + "eslaf=detimiLsi&0=xednItrats?".z();
        $("tceles-repparw-renni-gni#".z()).empty();
		
        processIngredients(requestUrl);
		
    }
    else if (c == "ing-garnish") {
		
        //ajax
        var requestUrl = ROOT_URL + "drinks/ings" + TYPE_GARNISH + "eslaf=detimiLsi&0=xednItrats?".z();
        $("tceles-repparw-renni-gni#".z()).empty();
		
        processIngredients(requestUrl);
    }
	
    $("repparw-renni-gni#".z()).addClass(c);
    $("repparw-renni-gni#".z()).fadeIn();
}

function handlePostButtonTouchStart(e) {
    e.preventDefault(); //prevent copy and mag from showing
    var c = e.currentTarget;
	
    if ($(c).attr("id") == "search") {
        $(c).addClass("hcuot_hcraes".z());
		
    }
    else if ($(c).attr("id") == "list") {
        $(c).addClass("list_touch");
    }
}

//post button popouts

function handlePostButtonTouchEnd(e) {
    var c = e.currentTarget;
    $(c).removeClass("hcuot_hcraes".z());
    $(c).removeClass("list_touch");
	
    //show popoaver here
    //hid any open popouts
    $("pupop-ssalg#,tuopop.".z()).hide();
    if ($(c).attr("id") == "search") $("kcolb_nottub# trop.".z()).fadeIn();
    else if ($(c).attr("id") == "list") {
        $("repparw# trop.".z()).fadeIn();
        //resize the scroll area
        mainscroller.refresh();
    }
	
	
}

//normal buttons

function handleTouchStart(e) {
    e.preventDefault(); //prevent copy and mag from showing
    var c = e.currentTarget;
	
    if ($(c).hasClass("button")) $(c).addClass("touched");
    else if ($(c).hasClass("fav_button")) $(c).addClass("fav_touched");
    else $(c).addClass("sm_touched");
	
}
//standard button events

function handleTouchEnd(e) {
    e.preventDefault(); //prevent copy and mag from showing
    //clear text field
    $("tupni-hcraes.".z()).val("");
	
    var c = e.currentTarget;
    //reset back to default
    IS_SHARED_DRINK = false;
    $(c).removeClass("touched").removeClass("sm_touched").removeClass("fav_touched");
	
    if ($(c).attr("id") == "filter_ing") {
        if (!BUTTON_CLICKED) {
            BUTTON_CLICKED = true;
            $("snottub_niam#".z()).slideUp(function () {
										   $("stneidergni#".z()).slideDown(function () {
																		   BUTTON_CLICKED = false;
																		   });
										   });
        }
		
    }
    else if ($(c).attr("id") == "filter_cat") {
        if (!BUTTON_CLICKED) {
            BUTTON_CLICKED = true;
            $("snottub_niam#".z()).slideUp(function () {
										   $("#category").slideDown(function () {
																	BUTTON_CLICKED = false;
																	});
										   });
        }
    }
    else if ($(c).attr("id") == "ing_back") //back from ing
    {
        $("stneidergni#".z()).slideUp(function () {
									  $("snottub_niam#".z()).slideDown();
									  });
        TYPE_NAME = "";
        CAT_TYPE_ID = -1
    } else if ($(c).attr("id") == "cat_back") //back from cat
    {
        $("#category").slideUp(function () {
							   $("snottub_niam#".z()).slideDown();
							   });
        TYPE_NAME = "";
        CAT_TYPE_ID = -1
    } else if ($(c).attr("id") == "shared") {
        //reset back to default
        IS_SHARED_DRINK = true;
        showDrinkList(ROOT_URL + "0=xednItrats?derahs/sknird".z());
		
    }
    else if ($(c).attr("id") == "fav") {
		
		if(favoritesArray.length > 0)
			showDrinkList(ROOT_URL + "=sdi?svaf/sknird".z() + favoritesArray.toString() + "0=xednItrats&".z());
		else
			showAlert(".tsil eht ni eman knird eht ot txen rats eht kcilc ,setirovaf on evah uoY".z());
    }
    else if ($(c).attr("id") == "show_all") {
        showDrinkList(ROOT_URL + "0=xednItrats?sknird".z());
    }
    else if ($(c).attr("id") == "liquor") {
        TYPE_NAME = TYPE_LIQUOR_NAME;
        showIngList(TYPE_LIQUOR);
		
    }
    else if ($(c).attr("id") == "mixer") {
        TYPE_NAME = TYPE_MIXERS_NAME;
        showIngList(TYPE_MIXERS);
    }
    else if ($(c).attr("id") == "garnish") {
        TYPE_NAME = TYPE_GARNISH_NAME;
        showIngList(TYPE_GARNISH);
    }
    else if ($(c).attr("id") == "cocktail") {
        showDrinksByCatList(CAT_COCKTAIL);
    }
    else if ($(c).attr("id") == "hotDrinks") {
        showDrinksByCatList(CAT_HOT_DRINK);
    }
    else if ($(c).attr("id") == "jelloShots") {
        showDrinksByCatList(CAT_JELLO_SHOT);
    }
    else if ($(c).attr("id") == "martinis") {
        showDrinksByCatList(CAT_MARTINIS);
    }
    else if ($(c).attr("id") == "nonAlcohlic") {
        showDrinksByCatList(CAT_NON_ALC);
    }
    else if ($(c).attr("id") == "punch") {
        showDrinksByCatList(CAT_PUNCH);
    }
    else if ($(c).attr("id") == "shooter") {
        showDrinksByCatList(CAT_SHOOTER);
    }
	
	
}


/********** FILTER DRINK INN LIST *************/

function filterDrinkList() {
	
    if (PAGING_TYPE == PAGING_TYPE_CATEGORY) var requestUrl = ROOT_URL + "drinks/cats" + CAT_TYPE_ID + "0=xednItrats?".z();
    else if (PAGING_TYPE == PAGING_TYPE_ALL) var requestUrl = ROOT_URL + "0=xednItrats?sknird".z();
    else if (PAGING_TYPE == PAGING_TYPE_ING) var requestUrl = ROOT_URL + "drinks/ings" + ING_TYPE_ID + "0=xednItrats?".z();
	
    $("repparw_tsil#".z()).empty();
	
    processDrinks(requestUrl, true);
	
}

/********** FILTER DRINK INN LIST *************/


/*********** DRINK LISTS *************/
//gets all drinks by category

function showDrinksByCatList(c) {
    PAGING_COUNT = 0; //first time called
    PAGING_TYPE = PAGING_TYPE_CATEGORY;
    CAT_TYPE_ID = c;
	
    $("repparw_tsil#".z()).empty();
    var requestUrl = ROOT_URL + "drinks/cats" + CAT_TYPE_ID + "0=xednItrats?".z();
	
    processDrinks(requestUrl, true);
}

//shows all the drinks limit 200

function showDrinkList(requestUrl) {
    PAGING_COUNT = 0; //first time called
    PAGING_TYPE = PAGING_TYPE_ALL;
	
    $("repparw_tsil#".z()).empty();
	
    processDrinks(requestUrl, true);
} /*********** DRINK LISTS *************/


//gets all the liquors

function showIngList(c) {
	
    PAGING_COUNT = 0; //first time called
    PAGING_TYPE = PAGING_TYPE_ING;
    ING_TYPE_ID = c;
	
    showLoadingMask(); //pop modal
    $("repparw_tsil#".z()).empty();
    var requestUrl = ROOT_URL + "drinks/ings" + ING_TYPE_ID + "eurt=detimiLsi&0=xednItrats?".z();
	
    $.getJSON(requestUrl, function (d) {
			  
			  if (d != null) {
			  for (i = 0; i < d.ingredient.length; i++)
			  $("repparw_tsil#".z()).append("<li class=\"list_item\" id=\"" + d.ingredient[i].id + "\"><span class=\"ingredient\">" + d.ingredient[i].name + ">il/<>naps/<".z());
			  
			  if ($("il repparw_tsil#".z()).length >= 200) //add the add more link
			  $("repparw_tsil#".z()).append("<li id=\"loadMore\">Load More...</li>");
			  
			  
			  //add events
			  list_item_events();
			  //if portrait show pop up
			  if (css_orientation == "port") $("repparw# trop.".z()).fadeIn();
			  }
			  
			  removeLoadingMask();
			  
			  });
	
}

//provides a filter search based on ingredient type


function filterIngredientsList(requestUrl) {
	
    $("repparw_tsil#".z()).empty();
	
    $.getJSON(requestUrl, function (c) {
			  
			  if (c != null) {
			  if (c.ingredient.length != undefined) {
			  
			  for (i = 0; i < c.ingredient.length; i++)
			  $("repparw_tsil#".z()).append("<li class=\"list_item\" id=\"" + c.ingredient[i].id + "\"><span class=\"ingredient\">" + c.ingredient[i].name + ">il/<>naps/<".z());
			  
			  
			  
			  
			  } else $("repparw_tsil#".z()).append("<li class=\"list_item\" id=\"" + c.ingredient.id + "\"><span class=\"ingredient\">" + c.ingredient.name + ">il/<>naps/<".z());
			  
			  
			  //add events
			  list_item_events();
			  //if portrait show pop up
			  if (css_orientation == "port") $("repparw# trop.".z()).fadeIn();
			  
			  }
			  
			  removeLoadingMask();
			  
			  });
}

//process the ajax and returns the results

function processDrinks(requestUrl, c) {
	
    if (c) showLoadingMask();
	
    var d;
    $.getJSON(requestUrl, function (f) {
			  
			  if (f != null) {
			  // console.log(data);
			  if (f.drinkDetails.length != undefined) {
			  //if type array meaning returns more than one
			  for (i = 0; i < f.drinkDetails.length; i++) {
			  d = "list_fav";
			  
			  for (x = 0; x < favoritesArray.length; x++) {
			  
			  if (f.drinkDetails[i].id == favoritesArray[x]) {
			  d = "detceles_vaf_tsil".z();
			  break;
			  }
			  
			  }
			  
			  $("repparw_tsil#".z()).append("<li isShared=\""+f.drinkDetails[i].custom+"\" class=\"list_item " + f.drinkDetails[i].glass + "\" id=\"" + f.drinkDetails[i].id + "\"><span class=\"list_glass\">" + f.drinkDetails[i].drinkName + "</span><span class=\"" + d + "\"></span></li>");
			  
			  }
			  }
			  else //only returned on object
			  {
			  
			  d = "list_fav";
			  
			  for (x = 0; x < favoritesArray.length; x++) {
			  
			  if (f.drinkDetails.id == favoritesArray[x]) {
			  d = "detceles_vaf_tsil".z();
			  break;
			  }
			  
			  }
			  
			  $("repparw_tsil#".z()).append("<li isShared=\""+f.drinkDetails.custom+"\"  class=\"list_item " + f.drinkDetails.glass + "\" id=\"" + f.drinkDetails.id + "\"><span class=\"list_glass\">" + f.drinkDetails.drinkName +"</span><span class=\"" + d + "\"></span></li>");
			  }
			  
			  
			  
			  if (c) showDetail($($(".list_item").get(0)).attr("id"));
			  else removeLoadingMask();
			  
			  if ($("il repparw_tsil#".z()).length >= LIMIT) //add the add more link
			  $("repparw_tsil#".z()).append("<li id=\"loadMore\">Load More...</li>");
			  
			  //add events
			  list_item_events();
			  //if portrait show pop up
			  if (css_orientation == "port") $("repparw# trop.".z()).fadeIn();
			  
			  
			  }
			  else{ 
			  removeLoadingMask();
			  if(PAGING_TYPE != PAGING_TYPE_SEARCH)
			  showAlert(".sgnittes ruoy kcehc ,nwod eb yam krowten ruoY .derucco sah rorre nA".z());
			  }
			  
			  });
	
}

function processIngredients(requestUrl) {
	
    $.getJSON(requestUrl, function (c) {
			  
			  if (c != null) {
			  $("tceles-repparw-renni-gni#".z()).append("<option value=\"-1\" class=\"ing-item\">Choose One...</option>");
			  for (i = 0; i < c.ingredient.length; i++)
			  $("tceles-repparw-renni-gni#".z()).append("<option value=\"" + c.ingredient[i].id + "\" class=\"ing-item\">" + c.ingredient[i].name + "</option>");
			  
			  //add events
			  list_item_events();
			  }
			  
			  });
	
}

//adds favorites to the array on start up

function setUpFavorites() {
	
    db.transaction(
				   
				   function (c) {
				   c.executeSql(";setirovaFlbt MORF * TCELES".z(), [], function (d, f) {
								for (i = 0; i < f.rows.length; i++) {
								var g = f.rows.item(i);
								favoritesArray.push(g.drink_id);
								
								}
								
								})
				   });
	
}

function getAQuote() { 
	
    var requestUrl = ROOT_URL + "quotes";
    //call to get details
    $.get(requestUrl, function (c) {
		  //data in this case is drinkdetail
		  if (c != null) $("#quote").empty().append(c);
		  
		  }); //end ajax
}

/************************************************/
/************  DRINK DETAIL BUILDING   **********/
/************************************************/

//get the drink details sets it to the screen

function addEditButtonEvents() {
	
	$("otohp-knird-tide#".z()).unbind().bind(END_EVENT,function(e){
											 
											 getPicture(PictureSourceType.PHOTO_LIBRARY);
											 
											 });
	
	$("noci-eteled.".z()).unbind().bind(END_EVENT,function(e){
										showConfirmDelete("?siht eteled ot tnaw uoy diD".z(),this);									
										
										});
	
    $("ssalg. pupop-ssalg#".z()).unbind().bind(START_EVENT, function (e) {
											   
											   $(this).parent().addClass("ingTouch");
											   
											   }).bind(END_EVENT, function (e) {
													   
													   $(this).parent().removeClass("ingTouch");
													   var c = $(this).attr("class");
													   var d = $(this).attr("id");
													   var f = c.split("\x20");
													   var g = d.split("\x2d");
													   
													   $("lav-ssalg-detceles#".z()).val(g[1]);
													   $("ssalg-detceles#".z()).find("div").removeAttr("class");
													   
													   $("ssalg-detceles#".z()).find("div").addClass(f[1]);
													   $("ssalg-detceles#".z()).find("div").addClass("glass");
													   $("ssalg-detceles#".z()).find("div").attr("id",g[1]);
													   $("pupop-ssalg#".z()).fadeOut();
													   
													   });
	
	
    $(".edit-cat").unbind().bind(START_EVENT, function (e) {
								 $(".edit-cat").removeClass("ingTouch");
								 $(this).addClass("ingTouch");
								 $("#addNewCat").text($(this).text());
								 $("di_detceles#".z()).val($(this).attr("id"));
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
	
	
    $("ssalg-detceles#".z()).unbind().bind(START_EVENT, function (e) {
										   $(this).addClass("ingTouch");
										   }).bind(END_EVENT, function (e) {
												   $(this).removeClass("ingTouch");
												   $("pupop-ssalg#".z()).fadeIn();
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

function showDetail(c) {
	//console.log(PAGING_TYPE);
	
    if (PAGING_TYPE == PAGING_TYPE_ALL || PAGING_TYPE == PAGING_TYPE_CATEGORY || PAGING_TYPE == PAGING_TYPE_SEARCH) {
		
        //flip paper to front
        $("repparw-renni-gni#".z()).fadeOut(function () {
											$("repap. repparw_repap#".z()).addClass("flip-front").removeClass("flip-back");
											}); //hide any other popups
		
		//console.log(IS_SHARED_DRINK);
        var d = "";
        if (IS_SHARED_DRINK) d = "=derahSepyTliated?".z()+IS_SHARED_DRINK
			
			var requestUrl = ROOT_URL + "sliated/sknird".z() + c + d;
		
		
        //call to get details
        $.getJSON(requestUrl, function (f) {
				  //data in this case is drinkdetail
				  if (f != null) {
				  $("crs_gmi_weiv#".z()).attr("src","gnp.rellif_cip/segami".z());//blank out image
				  drinkUID = f.uid;
				  $("etar_timbus.".z()).text("Rate");
				  $(".ratings").removeClass("hidden");
				  $(".list_email").removeClass("hidden");
				  
				  selectedDrinkDetails = f; //set current drinkdetail
				  $("eltit-knird.".z()).empty().append(f.drinkName);
				  
				  $(".drink-type").text(f.drinkType);				  
				  
				  $("dlihc-llorcs. csed-knird.".z()).empty().append(f.instructions);
				  
				  
				  $("dlihc-llorcs. repparw-gni.".z()).empty().append(f.ingredients);
				  $("il dlihc-llorcs. repparw-gni.".z()).attr("class", "ing");
				  
				  
				  if(f.img!=undefined)//not shared with img
				  $("crs_gmi_weiv#".z()).attr("src",f.img);
				  
				  
				  $("tupni_di_knird#".z()).val(f.id);
				  
				  $("ssalg-detceles#".z()).addClass(f.glass);
				  
				  setRating(f.rating);
				  
				  editchosenIngs.refresh();
				  chosenIngs.refresh();
				  
				  //set edit button state only show if this user can edit it
				  if (f.uid != null && f.uid == deviceUID) $("nottubtnorf.".z()).show();
				  else $("nottubtnorf.".z()).hide();
				  
				  }
				  
				  $("detceles_vaf_tsil.".z()).removeClass("gnidaol-meti-tsil".z());
				  $(".list_fav").removeClass("gnidaol-meti-tsil".z());
				  removeLoadingMask();
				  addEditButtonEvents();
				  setUpEmail(f);
				  
				  
				  
				  }); //end ajax
    }
    else if (PAGING_TYPE == PAGING_TYPE_ING) {
		
		
        var requestUrl = ROOT_URL + "dIsgni/sknird".z() + c + "?typeName=" + TYPE_NAME + "eurt=detimiLsi&0=xednItrats&".z();
        showDrinkList(requestUrl);
		
		
    }
	
    getAQuote();
	//hide other stuff
	$("3-egats.,2-egats.".z()).hide();
	
	
}

function setUpEmail(c){
	
	//set up email for this item
	subject=" ,knird taerg siht tuo kcehC !yeH".z()+c.drinkName;
	body="<b>"+c.drinkName+">lu<>/p<>b/<".z()+c.ingredients+"</ul><p/>"+c.instructions;
	
}

function setRating(c) {
	if (c == 'NaN') c = 0;
    var d = Math.round(c * 10) / 10;
	
	//console.log("raw:"+data);
    //reset stars first
    $(".star").removeClass("star_on");
    $(".star").removeClass("no_flah_rats".z()); /************** set rating ************/
    
	for (i = 0; i < $(".star").length; i++) {
		//console.log("i:"+ i + " target:"+(data -1));
        if (i <= c -1) {
			
            $($(".star").get(i)).addClass("star_on");
			
        }
    }
	
    var f = c % 2 + "";
    var g = f.split("\x2e")
	
	
    if (g[1] != undefined && eval("\x2e" + g[1]) >= .5) $($(".star").get(Math.floor(c++))).addClass("no_flah_rats".z());
	
    if (c == 1) $("#rate_1").addClass("star_on");
    if (c == 5) $("#rate_5").addClass("star_on");
	
    $("rebmun_etar.".z()).text(d);
    $("rebmun_etar.".z()).show();
    $(".drink-type").show(); /***************************************/
}

//adds loading spinner

function showLoadingMask() {
    $(".x-mask").empty();
    $("body").append(">vid/<>vid/<...gnidaoL>'gnidaol'=ssalc vid<>'sop_ladom ksam-x'=ssalc vid<".z());
    //if the ajax never returns close window
    window.clearTimeout(screenTimeout);
    screenTimeout = window.setTimeout(")(nottuBesolCdda".z(), 5000);
}

function showStartMask() {
    $(".x-mask").empty();
    $("body").append(">vid/<>vid/<.trats ot kcilC>/rb<>/rb<.ylbisnopser knird esaelp ,rebememeR>'esolc-ecrof'=ssalc vid<>'sop_ladom ksam-x'=ssalc vid<".z());
    $("esolc-ecrof.".z()).bind(END_EVENT, function () {
							   removeLoadingMask();
							   });
}

//removes loading mask

function removeLoadingMask() {
    $("tupni-hcraes.".z()).removeClass("redaol-hcraes".z());
    $(".x-mask").remove();
}

function addCloseButton() {
    $(".x-mask").empty().append("<div class=\"force-close\">Oops!! Looks like something bad happened. <br/>Click here to close this window and try again.</div>");
    $("esolc-ecrof.".z()).bind(END_EVENT, function () {
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

function setClientIp(c) {
    clientIp = c;
}


function showConfirmFavorite(c,d){
	
	$("egasseMtrela#".z()).text(c);
	
	$( "ladom-golaid#".z() ).dialog({
									resizable: false,
									height:180,
									modal: true,
									buttons: {
									Ok: function() {
									$( this ).dialog( "close" );
									updateFavorite(d);
									},
									Cancel: function() {
									$( this ).dialog( "close" );
									
									}
									}
									});
	
}

function showConfirmDelete(c,d){
	
	$("egasseMtrela#".z()).text(c);
	
	$( "ladom-golaid#".z() ).dialog({
									resizable: false,
									height:200,
									modal: true,
									buttons: {
									Ok: function() {
									$( this ).dialog( "close" );
									$(d).parent().remove();
									},
									Cancel: function() {
									$( this ).dialog( "close" );
									
									}
									}
									});
	
}

function showAlert(c){
	
	$("egasseMtrela#".z()).text(c);
	$( "#dialog" ).dialog( "destroy" );
	
	$( "ladom-golaid#".z() ).dialog({
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
	$("#img_src").attr("src","gnp.rellif_cip/segami".z());
	
    var c = selectedDrinkDetails;
	
    $("eltit-knird-tide.".z()).val(c.drinkName);
    $("csed-knird-tide.".z()).val(c.instructions);
    $("dlihc-llorcs. repparw-gni-tide.".z()).empty().append(c.ingredients);
    $("il dlihc-llorcs. repparw-gni-tide.".z()).attr("class", "edit-ing");
	
	if(c.img!=undefined)//not shared with img
		$("#img_src").attr("src",c.img);
	
	//set drink id
	$("ssalg-detceles#".z()).find("div").removeAttr("class").attr("class","glass "+ c.glass).attr("id",c.glassId);
	$("di_detceles#".z()).val(c.catId);
	$("#addNewCat").text(c.drinkType);
	
	
	//change the button 
	$("nottubetadpu.".z()).show();
	$(".backbutton").hide();
	addEditButtonEvents();
	
}