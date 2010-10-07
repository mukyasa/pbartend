<!DOCTYPE html>
<html>
 <head>
  <title>Form to create a new resource</title>
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
</body>
</html>
