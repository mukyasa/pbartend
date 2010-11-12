<!DOCTYPE html>
<meta name = "viewport" content = "width = device-width, user-scalable = no"/>
<html>
 <head>
  <title>Form to create a new resource</title>
  
  <script type="text/javascript" src="http://code.jquery.com/jquery-1.4.3.min.js"></script>
  <script>
  
  var rootURL = "/iPad/rest/drinks/";
  
$(document).ready(function(){
	
	
	
	$("#sharedDrinks").click(function(){
	
	var requestUrl = rootURL+"adminShared";
	
	   $.getJSON(requestUrl, function (data) {
			  
			  if (data != null) {
			  $("#list_wrapper").empty();
			  	for (i = 0; i < data.drinkDetails.length; i++)
			 	 	$("#list_wrapper").append("<li class=\"list_item\" id=\"" + data.drinkDetails[i].id + "\"><img src=\""+data.drinkDetails[i].img+"\" width=\"40\" height=\"40\"/><b class=\"drinkName\">"+data.drinkDetails[i].drinkName+"</b><div>"+data.drinkDetails[i].instructions+"</div><div class=\"uid\">"+data.drinkDetails[i].uid+"</div></li>");
			 	 	
			 	 	eventHandlers();
			  }
			  
		});
  
	});
});
function eventHandlers(){

	$(".list_item").click(function(){
		var uid = $(this).find(".uid").text();
		var drinkName = $(this).find(".drinkName").text(); 
		confirm(drinkName+"\n\nAre you sure you want to delete?");
	});

}
  
  </script>
   <style>
 	label{
 	font-weight:bold;
 	}
 	img{
 	float:left;
 	margin-right:10px;
 	}
 	ul,li{
 		list-style: none;
 		clear:both;
 		margin:0;
 		padding:5px;
 		cursor: pointer;
 	}
 	li{
 		border-bottom:1px solid red;
 	}
 	.button{ 
 		font-size: 14px;
 		height:30px;
 		width:100%;
 		border:1px solid #333;
 		background:#ccc;
 		text-align: center;
 		padding-top:10px;
 		 -webkit-border-radius: 6px;
 	}
 </style>
 </head>
<body>
<div class="button" id="sharedDrinks">Get All Shared Drinks</div>
	
	<ul id="list_wrapper"></ul>
	
</body>
</html>

