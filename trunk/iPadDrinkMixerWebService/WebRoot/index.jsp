<!DOCTYPE html>
<html>
 <head>
  <title>Form to create a new resource</title>
  
  <script type="text/javascript" src="http://code.jquery.com/jquery-1.4.3.min.js"></script>
  <script>
$(document).ready(function(){
	
	 $("#JqAjaxForm").submit(function(){

       dataString = $("#JqAjaxForm").serialize();

	   $.ajax({
			type: "POST",
			url: "/iPad/rest/drinks/create",
			data: dataString,
			dataType: "json",
			success: function(data) {
				console.log(data);
				}
			});
			
			 return false; 
	});
  
});
  
  </script>
  
 </head>
 <style>
 	label{
 	font-weight:bold;
 	}
 </style>
<body>
	<form action="/iPad/rest/todos" method="POST">
	<label for="id">ID</label><br/>
	<input name="id" />
	<br/>
	<label for="summary">Summary</label><br/>
	<input name="summary" />
	<br/>
	<label>Description:</label><br/>
	<TEXTAREA NAME="description" COLS=40 ROWS=6></TEXTAREA>
	<br/>
	<input type="submit" value="Submit" />
	</form>
	

	
	<form id="JqAjaxForm">
	<label>title</label><br/>
	<input name="dt" />
	<br/>
	
	<label>category</label><br/>
	<input name="cat" />
	<br/>
	
	<label >ingredients</label><br/>
	<input name="ings" />
	<br/>
	
	<label for="summary">glass</label><br/>
	<input name="g" />
	<br/>
	
	<label>uid</label><br/>
	<input name="uid" />
	<br/>
	
	
	<label>Description:</label><br/>
	<TEXTAREA NAME="instructions" COLS=40 ROWS=6></TEXTAREA>
	<br/>
	<input type="submit" value="Submit" />
	</form>
	
	
</body>
</html>

