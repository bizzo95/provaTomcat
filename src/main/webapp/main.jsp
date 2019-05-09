<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="asr.proyectoFinal.services.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.google.gson.JsonArray" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Proyecto italiano</title>
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

<form action="main.jsp" method="POST">
<div class="form-group">
  <label for="comment"><h6>Texto:</h6></label>
  <textarea name="text" class="form-control" rows="5" id="comment"></textarea>
</div>


<div class="container" id="main">
  <div class="row">
    <div class="col-sm-3" id="traduttore">
		<h5>Traductor <span class="glyphicon glyphicon-text-size"></h5>
			<div class="form-group">
				<label for="exampleFormControlSelect1">Insertas lengua naciente:</label>
				<select class="form-control" id="source" name="source">	
				  <option value="en">Inglés</option>
				  <option value="it">Italiano</option>
				  <option value="es">Español</option>
				</select> 
				<label for="exampleFormControlSelect1">Insertas lengua destino:</label>
				<select class="form-control" id="dest" name="dest">
				  <option value="it">Italiano</option>
				  <option value="es">Español</option>
				  <option value="fr">Francés</option>
				</select>
			</div>
			<button class="btn btn-info bottone" id="traduci" name="action" value="1">Traducir</button>
    </div>
    <div class="col-sm-3">
      <h5>Análisis sentimientos </h5>
     Efectúa el análisis de los Big5, analizando el texto sobre las 5 dimensiones de la personalidad (Insertas al menos <u>100 palabras</u>)
      <button class="btn btn-warning bottone" id="btnSent" name="action" value="2">Análisis de los Big5</button>
    </div>
    <div class="col-sm-3">
    	<h5>Comprensión de lengua</h6>
	   	Efectúa el análisis del texto:<br>
		<u>Keywords</u>: devuelve palabras llaves importantes del contenido.<br>
		<u>Entities</u>: identifica personas, ciudad, organizaciones, etc..<br>
		<u>Emotions</u>: provee un análisis de la emoción por las palabras clave encontradas en precedencia.<br>
		 <button class="btn btn-primary bottone" id="btnKey" name="action" value="3">Comprensión </button>
    </div>
	<div class="col-sm-3">
	<h5>Análisis de los tonos</h5>
	Servicio que permite de analizar los tonos de una frase devolviendo los tonos más relevantes y explicándolos en porcentaje
      <button class="btn btn-light bottone" id="btnTone" name="action" value="4">Tonos</button>
    </div>
  </div>
</div>

</form> 

<div class="form-group" id="divRis">
  
<% try {
		String param = request.getParameter("action");
		int p = Integer.parseInt(param);
		String text = request.getParameter("text");%>
		<label for="comment"><h6>Resultado:</h6></label>
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
						<tr><td><b>No se encontraron entidades</b></td><td></td><td></td></tr>
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
						<tr><td><b>No se encontraron palabras clave</b></td><td></td><td></td></tr>
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
		  		  //System.out.println(tones);
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
				  int k=0;

				  if(tones.size()==1){%>
						<div class="container">
							No se detectó ningún tono prevaleciente
						</div>
					<%}else{%>
					<div class="container">
					  <div class="progress" >
					  <%for(c=0; c<feelings.size(); c++){
					  		if(k==0){%>
					  			<div class="progress-bar " role="progressbar" style="width:<%=dati.get(c)%>%; background-color:#FF6633">
					  	      <%=feelings.get(c)%>
					  	      (<%=dati.get(c)%>)
					  	    </div>
					  		<%}
					  		if(k==1){%>
					  			<div class="progress-bar " role="progressbar" style="width:<%=dati.get(c)%>%; background-color:#6633FF">
					  	      <%=feelings.get(c)%>
					  	      (<%=dati.get(c)%>)
					  	    </div>
					  		<%}
					  		if(k==2){%>
								<div class="progress-bar " role="progressbar" style="width:<%=dati.get(c)%>%;  background-color:#CC0033">
						      <%=feelings.get(c)%>
						      (<%=dati.get(c)%>)
						      <% k=0; %>
						    </div>
							<%}
					  		if(k==2){%>
							<div class="progress-bar " role="progressbar" style="width:<%=dati.get(c)%>%;  background-color:##00CC99">
					      	<%=feelings.get(c)%>
					      	(<%=dati.get(c)%>)
					      	<% k=0; %>
					    	</div>
							<%}
					k++;
					}
					%>
					  </div>
					</div>
					<%
					}
					%>
	  			
	  			
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
		if ($("#source option:selected").text() == "Inglés") {
			$("#dest").empty();
			$("#dest").append(new Option("Italiano", "it"));
			$("#dest").append(new Option("Español", "es"));
			$("#dest").append(new Option("Francés", "fr"));
		}
		if ($("#source option:selected").text() == "Italiano") {
				$("#dest").empty();
				$("#dest").append(new Option("Inglés", "en"));
				$("#dest").append(new Option("Alemán", "de"));
		}
		if ($("#source option:selected").text() == "Español") {
				$("#dest").empty();
				$("#dest").append(new Option("Inglés", "en"));
				$("#dest").append(new Option("Francés", "fr"));
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

