<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="asr.proyectoFinal.services.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.google.gson.JsonArray" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Italianos project</title>
<script src="jquery-3.3.1.min.js"></script>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
<link rel="stylesheet" href="style.css">
<link rel="shortcut icon" href="italia.png" />
</head>
<body>

<div id="container" class="container-fluid">
<p>Benvenuto amico!, ora puoi utilizzare tutti i tools a tua disposizione</p>

<form action="main.jsp" method="GET">
<div class="form-group">
  <label for="comment"><h5>Testo:</h5></label>
  <textarea name="text" class="form-control" rows="5" id="comment">I stand here today humbled by the task before us, grateful for the trust you have bestowed, mindful of the sacrifices borne by our ancestors. I thank President Bush for his service to our nation, as well as the generosity and cooperation he has shown throughout this transition. Forty-four Americans have now taken the presidential oath. The words have been spoken during rising tides of prosperity and the still waters of peace. Yet, every so often the oath is taken amidst gathering clouds and raging storms. At these moments, America has carried on not simply because of the skill or vision of those in high office, but because We the People have remained faithful to the ideals of our forbearers, and true to our founding documents.</textarea>
</div>


<div class="container">
  <div class="row">
    <div class="col-sm-3" id="traduttore">
		<h5>Traduttore</h5>
			<div class="form-group">
				<label for="exampleFormControlSelect1">Immetti la lingua sorgente:</label>
				<select class="form-control" id="source" name="source">	
				  <option value="en">Inglese</option>
				  <option value="it">Italiano</option>
				  <option value="es">Spagnolo</option>
				</select>
				<label for="exampleFormControlSelect1">Immetti la lingua destinazione:</label>
				<select class="form-control" id="dest" name="dest">
				  <option value="it">Italiano</option>
				  <option value="es">Spagnolo</option>
				  <option value="fr">Francese</option>
				</select>
			</div>
			<button class="btn btn-info" id="traduci" name="action" value="1">Traduci</button>
    </div>
    <div class="col-sm-3">
      <h5>Analisi sentimenti</h5>
      Effettua l'analisi del Big5, analizzando il testo sulle 5 dimensioni della personalità. Sono necessarie almeno <u>100 parole</u>.
      <button class="btn btn-warning" id="traduci" name="action" value="2">Analisi Big5</button>
    </div>
    <div class="col-sm-3">
      Pollac
      <button class="btn btn-primary" id="traduci" name="action" value="3">Analisi Keywords</button>
    </div>
	<div class="col-sm-3">
	<h5>Scarica l'audio</h5>
	Inserisci il nome del tuo file di testo e con un click troverai il tuo testo in formato audio sul tuo Desktop.
	  <label>Nome file:</label>
      <input type="text" name="name" class="form-control">
      <button class="btn btn-light" id="traduci" name="action" value="4">Scarica audio</button>
    </div>
  </div>
</div>

</form> 

<div class="form-group" id="divRis">
  <label for="comment"><h5>Risultato: </h5></label>
<% try {
		String param = request.getParameter("action");
		int p = Integer.parseInt(param);
		String text = request.getParameter("text");
	    switch (p) {
	  		case 1: 
				String source = request.getParameter("source");
				String dest = request.getParameter("dest");
				Traduttore tra = new Traduttore(); %>
				<div id="ris" class="container-fluid"><%= tra.getTranslation(text, source, dest) %></div>
				<%break;
				
	  		case 2:
	  			Sentimento sent = new Sentimento();%>
	  			<div id="ris" class="container-fluid">
	  			<% 
	  			ArrayList<String> sentimenti = sent.getSentimenti(text);
	  			for (int i=0; i<sentimenti.size(); i=i+2){ %>
	  			<label><%= sentimenti.get(i) %></label><div class="progress"><div class="progress-bar" role="progressbar" style="width:<%= sentimenti.get(i+1) %>%" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100"></div></div>
	  			<%} %>
	  			</div>
	  			<%break;
	  			
	  		case 3:
	  			Understanding u =new Understanding();
	  			ArrayList<JsonArray> risultato = u.getnlu(text);%>
	  			<p></p>
				<button type="button" class="btn btn-primary" id="ent" >Entities</button>
				<button type="button" class="btn btn-primary" id="key">Keywords</button>
				<button type="button" class="btn btn-primary" id="emo">Emotions </button>
		 		
	  			
	  			
				<div class="container-fluid" id="tab-ent" style="display:none;">
					<% JsonArray entity = new JsonArray();
					entity = risultato.get(1);	
					%>
					<table class="table table-striped">
					<thead>
					<tr>
					<th scope="col"></th>
					<th scope="col">Relevance</th>
					<th scope="col">Final Score</th>
					</tr>
					</thead>
					<tbody>
					<%
					for(int j=0;j<entity.size();j++){%>
					<tr>
					<th scope="row"><%=(entity.get(j).getAsJsonObject().get("text").getAsString()) %></th>
					<td><%=entity.get(j).getAsJsonObject().get("relevance").getAsString()%></td>
					<td><%=entity.get(j).getAsJsonObject().get("sentiment").getAsJsonObject().get("score").getAsString()%></td>
					</tr>
						<%}%>			
					</tbody>
					</table>				    
				</div>
				
				<%JsonArray keywords = new JsonArray();
				   keywords = risultato.get(0); %>
				   
				   <div class="container-fluid" id="tab-key" style="display:none;">
					<table class="table table-striped">
					<thead>
					<tr>
					<th scope="col"></th>
					<th scope="col">Relevance</th>
					<th scope="col">Final Score</th>
					</tr>
					</thead>
					<tbody>
					<%
					for(int k=0;k<keywords.size();k++){%>
					<tr>
					<th scope="row"><%=(keywords.get(k).getAsJsonObject().get("text").getAsString())%></th>
					<td><%=keywords.get(k).getAsJsonObject().get("relevance").getAsString()%></td>
					<td> <%=keywords.get(k).getAsJsonObject().get("sentiment").getAsJsonObject().get("score").getAsString()%></td>
					</tr>
						<%}%>			
					</tbody>
					</table>				    
				</div>
				   
				<div class="container-fluid" id="tab-emo" style="display:none;">
					<table class="table table-striped">
					<thead>
					<tr>
					<th scope="col"></th>
					<th scope="col">Anger</th>
					<th scope="col">Disgust</th>
					<th scope="col">Fear</th>
					<th scope="col">Joy</th>
					<th scope="col">Sadness</th>
					</tr>
					</thead>
					<tbody>
					<tr>
					<th scope="row">Score</th>
					<td><%=keywords.get(0).getAsJsonObject().get("emotion").getAsJsonObject().get("anger").getAsString()%></td>
					<td><%=keywords.get(1).getAsJsonObject().get("emotion").getAsJsonObject().get("disgust").getAsString()%></td>
					<td><%=keywords.get(2).getAsJsonObject().get("emotion").getAsJsonObject().get("fear").getAsString()%></td>
					<td><%=keywords.get(3).getAsJsonObject().get("emotion").getAsJsonObject().get("joy").getAsString()%></td>
					<td><%=keywords.get(4).getAsJsonObject().get("emotion").getAsJsonObject().get("sadness").getAsString()%></td>
					</tr>			
					</tbody>
					</table>				    
				</div>
					
	  			<%break;
	  		
	  		case 4:
	  			String name = request.getParameter("name");
	  			Audio aud = new Audio();%>
	  			<div id="ris" class="container-fluid"> Il file <%=aud.getAudio(text, name) %> è stato staricato con successo sul Desktop</div>
	  			<%break;
	  	}
	    }catch (Exception e){}%>

</div>


 
</div>

</body>
</html>

<script>
$( document ).ready(function() {
	//var query = window.location.search.substring(1).split("&")[0].split("=")[1].split("+");
	//console.log(query);
	
	$("#source").change(function(){
		if ($("#source option:selected").text() == "Inglese") {
			$("#dest").empty();
			$("#dest").append(new Option("Italiano", "it"));
			$("#dest").append(new Option("Spagnolo", "es"));
			$("#dest").append(new Option("Francese", "fr"));
		}
		if ($("#source option:selected").text() == "Italiano") {
				$("#dest").empty();
				$("#dest").append(new Option("Inglese", "en"));
				$("#dest").append(new Option("Tedesco", "de"));
		}
		if ($("#source option:selected").text() == "Spagnolo") {
				$("#dest").empty();
				$("#dest").append(new Option("Inglese", "en"));
				$("#dest").append(new Option("Francese", "fr"));
				$("#dest").append(new Option("Catalano", "ca"));
		}
	});

	
	$("#und").click(function () {
		  $("#ris").toggle();
		});

	$("#ent").click(function () {
		 $("#tab-emo").css("display","none");
  		  $("#tab-key").css("display","none");
		  $("#tab-ent").toggle();
		});

	$("#key").click(function () {
		
		$("#tab-emo").css("display","none");
			$("#tab-ent").css("display","none");
		  $("#tab-key").toggle();
		});

	$("#emo").click(function () {
		$("#tab-ent").css("display","none");
			$("#tab-key").css("display","none");
		  $("#tab-emo").toggle();
		});
	
	
});
</script>

