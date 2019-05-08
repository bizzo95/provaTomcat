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
<!--<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">-->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
</head>
<body>

<div id="container" class="container-fluid">
<!--  <p>Benvenuto, ora puoi utilizzare tutti i tools a tua disposizione</p>-->

<form action="main.jsp" method="GET">
<div class="form-group">
  <label for="comment">Testo:</label>
  <textarea name="text" class="form-control" rows="5" id="comment"></textarea>
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
			<button class="btn btn-info bottone" id="traduci" name="action" value="1">Traduci</button>
    </div>
    <div class="col-sm-3">
      <h5>Analisi Sentimenti</h5>
      Effettua l'analisi del Big5, analizzando il testo sulle 5 dimensioni della personalit�. Sono necessarie almeno <u>100 parole</u>.
      <button class="btn btn-warning bottone" id="btnSent" name="action" value="2">Analisi Big5</button>
    </div>
    <div class="col-sm-3">
    	<h5>Language Understanding</h6>
	   	Effettua l'analisi dal testo:<br>
		- <u>Keywords</u>: restituisce parole chiave importanti nel contenuto.<br>
		- <u>Entities</u>: identifica persone, citt�, organizzazioni, etc.<br>
		- <u>Emotions</u>: fornisce una analisi dell'emozione per le entit� trovate in precedenza.<br>
		 <button class="btn btn-primary bottone" id="btnKey" name="action" value="3">Understanding</button>
    </div>
	<div class="col-sm-3">
	<h5>Analizza il tono</h5>
      <button class="btn btn-light bottone" id="traduci" name="action" value="4">Scarica audio</button>
    </div>
  </div>
</div>

</form> 

<div class="form-group" id="divRis">
  <label for="comment"><h5>Risultato: </h5></label>
<% try {
		String param = request.getParameter("action");
		int p = Integer.parseInt(param);
		String text = request.getParameter("text");%>
		
		<script>
			$("#comment").text("<%= text %>");
			$("#loading").css("display","block");
		</script>
		
	    <%switch (p) {
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
		 		
	  			
	  			
				<div class="container-fluid" id="tab-ent" class="tab" style="display:none;">
					<% JsonArray entity = new JsonArray();
					entity = risultato.get(1);
					%>
					<table class="table table-striped">
					<thead>
					<tr>
					<th scope="col"></th>
					<th scope="col">Relevance [0;1]</th>
					<th scope="col">Final Score [-1;1]</th>
					</tr>
					</thead>
					<tbody>
					<%
					if (entity.size()>0) {
						for(int j=0;j<entity.size();j++){%>
						<tr>
						<th scope="row"><%=(entity.get(j).getAsJsonObject().get("text").getAsString()) %></th>
						<td><%=entity.get(j).getAsJsonObject().get("relevance").getAsString()%></td>
						<td><%=entity.get(j).getAsJsonObject().get("sentiment").getAsJsonObject().get("score").getAsString()%></td>
						</tr>
							<%}%>
					<%}else {%>
						<tr><td><b>Nessuna entit� trovata</b></td><td></td><td></td></tr>
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
					<th scope="col">Relevance [0;1]</th>
					<th scope="col">Final Score [-1;1]</th>
					</tr>
					</thead>
					<tbody>
					<%
					if (entity.size()>0) {
					for(int k=0;k<keywords.size();k++){%>
					<tr>
					<th scope="row"><%=(keywords.get(k).getAsJsonObject().get("text").getAsString())%></th>
					<td><%=keywords.get(k).getAsJsonObject().get("relevance").getAsString()%></td>
					<td> <%=keywords.get(k).getAsJsonObject().get("sentiment").getAsJsonObject().get("score").getAsString()%></td>
					</tr>
						<%}%>
					<%}else {%>
						<tr><td><b>Nessuna keywords trovata</b></td><td></td><td></td></tr>
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
					<td><%=keywords.get(0).getAsJsonObject().get("emotion").getAsJsonObject().get("disgust").getAsString()%></td>
					<td><%=keywords.get(0).getAsJsonObject().get("emotion").getAsJsonObject().get("fear").getAsString()%></td>
					<td><%=keywords.get(0).getAsJsonObject().get("emotion").getAsJsonObject().get("joy").getAsString()%></td>
					<td><%=keywords.get(0).getAsJsonObject().get("emotion").getAsJsonObject().get("sadness").getAsString()%></td>
					</tr>			
					</tbody>
					</table>				    
				</div>
					
	  			<%break;
	  		case 4:
	  			
			      Toni tono = new Toni();
			  			
			  	  ArrayList<String> tones = new ArrayList<String>();
		  		  tones= tono.getToni(text);
		  		  //for(String a: tones) System.out.println(a);
				  ArrayList<String> feelings=new ArrayList<String>();
				  ArrayList<String> dati=new ArrayList<String>();
				  int c=1;
				  while(c<tones.size()-1){
					  feelings.add(tones.get(c));
					  c=c+2;  
				  }
				  c=2;
				  while(c<tones.size()){
					  dati.add(tones.get(c));
					  c=c+2;  
				  }
				  c=0;
				  %>
				<% ArrayList<String> colores =new ArrayList<String>();
				colores.add("success");
				colores.add("warning");
				colores.add("danger");
				%>
				
				<div id="ris" class="container-fluid">
				  <div class="progress" id="toneBar">
				  <%for(c=0; c<feelings.size(); c++){%>
				    <div class="progress-bar progress-bar-<%=colores.get(c)%>" role="progressbar" style="width:<%=dati.get(c)%>%">
				      <%=feelings.get(c)%>
				      (<%=dati.get(c)%>)
				    </div>
				    <%
				}
				%>
				  </div>
				</div>
	  			
	  			
	  	<%}%>
	    
	    <script>
			$("#loading").css("display","none");
		</script>
	    
	    <%}catch (Exception e){}%>

</div>

<div id="loading"><img id="imgLoading" src="loading.gif"></div>
 
</div>

</body>
</html>

<script>
$( document ).ready(function() {
	
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
	
	$(".bottone").click(function () {
		$("#loading").css("display","block");
		});
	
	
});
</script>

