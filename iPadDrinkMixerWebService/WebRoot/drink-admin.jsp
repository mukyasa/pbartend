
<%@page import="com.jersey.dao.DbConnectionTest"%><!DOCTYPE html>

<html>
 <head>
  <title>Form to create a new resource</title>
  <meta name = "viewport" content = "width = device-width, user-scalable = no"/>
  <script type="text/javascript" src="http://code.jquery.com/jquery-1.4.3.min.js"></script>
  <script>
  
  var rootURL = "<%=DbConnectionTest.ROOT%>/rest/drinks/";
  
$(document).ready(function(){
	
	$("#ratings").click(function(){
	
	var requestUrl = rootURL+"adminRatings";
	
	   $.getJSON(requestUrl, function (data) {

			  if (data != null) {
			  $("#list_wrapper").empty();
			  if (data.drinkDetails.length != undefined)
			  {
			  	for (i = 0; i < data.drinkDetails.length; i++)
			 	 	$("#list_wrapper").append("<li class=\"rating-item\" id=\"" + data.drinkDetails[i].drinkId + "\"><b>"+data.drinkDetails[i].drinkName+"</b> - Drink Id: "+data.drinkDetails[i].drinkId+"<div>"+data.drinkDetails[i].version+"</div><div>"+data.drinkDetails[i].ipAddress+"</div><div class=\"uid\">"+data.drinkDetails[i].uid+"</div></li>");
			 	
			 	}else 	
			 	 	$("#list_wrapper").append("<li class=\"rating-item\" id=\"" + data.drinkDetails.drinkId + "\"><b>"+data.drinkDetails.drinkName+"</b> - Drink Id: "+data.drinkDetails.drinkId+"<div>"+data.drinkDetails.version+"</div><div>"+data.drinkDetails.ipAddress+"</div><div class=\"uid\">"+data.drinkDetails.uid+"</div></li>");

			 	 	eventHandlers();
			  }
			  
		});
  
	});
	
	$("#sharedDrinks").click(function(){
	
	var requestUrl = rootURL+"adminShared";
	
	   $.getJSON(requestUrl, function (data) {
			  
			  if (data != null) {
			  $("#list_wrapper").empty();
			  if (data.drinkDetails.length != undefined)
			  {
			  
			  
			  	for (i = 0; i < data.drinkDetails.length; i++)
			 	 	$("#list_wrapper").append("<li class=\"list_item\" id=\"" + data.drinkDetails[i].id + "\"><img src=\""+data.drinkDetails[i].img+"\" width=\"40\" height=\"40\"/><b class=\"drinkName\">"+data.drinkDetails[i].drinkName+"</b><div>"+data.drinkDetails[i].instructions+"</div><div class=\"uid\">"+data.drinkDetails[i].uid+"</div></li>");
			 	 }else
			 	 	$("#list_wrapper").append("<li class=\"list_item\" id=\"" + data.drinkDetails.id + "\"><img src=\""+data.drinkDetails.img+"\" width=\"40\" height=\"40\"/><b class=\"drinkName\">"+data.drinkDetails.drinkName+"</b><div>"+data.drinkDetails.instructions+"</div><div class=\"uid\">"+data.drinkDetails.uid+"</div></li>");
			 	 
			 	 	eventHandlers();
			  }
			  
		});
  
	});
});
function eventHandlers(){

	$(".list_item").click(function(){
		var uid = $(this).find(".uid").text();
		var id = $(this).attr("id");
		var drinkName = $(this).find(".drinkName").text(); 
		if(confirm(drinkName+"\n\nAre you sure you want to delete?"))
		{
			var requestUrl = rootURL+"deleteShared"+id;

			$.getJSON(requestUrl, function (data) {
				$("#sharedDrinks").trigger("click");
			});
		}
	});
	
	$(".rating-item").click(function(){

		var id = $(this).attr("id");
		var isShared=false;
		if(id > 9636)
			isShared=true;
		
		var requestUrl = rootURL+"details"+id+"?detailTypeShared="+isShared;

		$.getJSON(requestUrl, function (data) {
		
		if (data != null) {
		
			  $("#list_wrapper").empty();
			 	 $("#list_wrapper").append("<li class=\"list_item\" id=\"" + data.id + "\"><img src=\""+data.img+"\" width=\"40\" height=\"40\"/><b class=\"drinkName\">"+data.drinkName+"</b><div>"+data.instructions+"</div><div class=\"uid\">"+data.uid+"</div></li>");
			 	 
			  }
			  
		});
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
 		margin-bottom:10px;
 		 -webkit-border-radius: 6px;
 	}
 </style>
 </head>
<body>
<div class="button" id="sharedDrinks">Get All Shared Drinks</div>


<div class="button" id="ratings">Get All Ratings</div>
	
<ul id="list_wrapper"></ul>
	
</body>
</html>

