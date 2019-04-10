<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtda">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Proyecto ASR</title>
</head>
<body>
<h1>Menu</h1>
<hr />
<ul>
<p><h1>Sign in: </h1></p>
	<form action="listar" method="POST">
	<li>Inserire username: <input type="text" name="username">
	<li>Inserire password: <input type="password" name="password">
	<input type="submit" value="Invia">
</form> 
<br><br><br>
<p><h1>Sign up: </h1></p>
<form action="insertar" method="POST" id="form">
	<li>Inserire username: <input type="text" name="username" id="usr" class="inp">
	<li>Inserire password: <input type="password" name="password" id="psw" class="inp">
	<li>Reinserire password: <input type="password" name="password2" id="psw2" class="inp">
	<input type="submit" value="Invia" id="invia" >
	<p id="p"></p>
</form>
 
</ul>
</body>
</html>

<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script>
var query = window.location.search.substring(1);
if (query=="e") document.write("Credenziali Errate");
var check1 = false;
var check2 = false;

$(document).ready(function(){
	
	  $("#psw").keyup(function(){
	    if ($(this).val().length < 8) {
	    	$("#p").text("Password troppo corta");
	    	check1 = false;
	    }
	    else {
	    	$("#p").text("");
	    	check1 = true
	    }
	  });
	  
	  $("#psw2").keyup(function(){
		    if ($(this).val() != $("#psw").val()) {
		    	$("#p").text("Password differenti");
		    	check2 = false;
		    }
		    else {
		    	$("#p").text("");
		    	check2 = true
		    }
		  });
	  
	  $("#invia").mouseover(function(){
		  console.log(check1)
		  console.log(check2)
		  console.log($("#usr").val().length)
		 if( check1 != true || check2 != true || $("#usr").val().length <= 0) {
			 $(this).attr("disabled", true);
			 $("#p").text("Compila tutti i campi correttamente");
		 }
		 else {
			 $(this).attr("disabled", false);
			 $("#p").text("");
		 }
	  });  
	  
	  $(".inp").keyup(function(){
			$("#invia").attr("disabled", false); 
	  });  
	  
	  
});
</script>