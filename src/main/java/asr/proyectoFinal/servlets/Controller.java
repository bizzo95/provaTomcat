package asr.proyectoFinal.servlets;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.nio.Buffer;
import java.nio.file.Files;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cloudant.client.api.model.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ibm.watson.developer_cloud.assistant.v1.model.DialogNodeNextStep.Behavior;
import com.ibm.watson.developer_cloud.http.HttpMediaType;

import com.ibm.watson.developer_cloud.language_translator.v3.util.Language;
import com.ibm.watson.developer_cloud.personality_insights.v3.PersonalityInsights;
import com.ibm.watson.developer_cloud.personality_insights.v3.model.Profile;
import com.ibm.watson.developer_cloud.personality_insights.v3.model.ProfileOptions;
import com.ibm.watson.developer_cloud.personality_insights.v3.model.Trait;
import com.ibm.watson.developer_cloud.service.security.IamOptions;
import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.RecognizeOptions;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechRecognitionResults;

import asr.proyectoFinal.dao.CloudantPalabraStore;
import asr.proyectoFinal.dominio.Palabra;
import asr.proyectoFinal.services.Traduttore;

/**
 * Servlet implementation class Controller
 */
@WebServlet(urlPatterns = {"/log", "/add", "/trad"})
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		PrintWriter out = response.getWriter();
		
		//Set the HTML page
		out.println("<!DOCTYPE html PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN' 'http://www.w3.org/TR/html4/loose.dtda'>"
				+"<html>"
				+"<head>"
				+"<meta http-equiv='Content-Type' content='text/html; charset=ISO-8859-1'>"
				+"<title>Italianos project</title>"
				+"<link rel='stylesheet' href='https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css' integrity='sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T' crossorigin='anonymous'>"
				+"<script src='https://code.jquery.com/jquery-3.3.1.slim.min.js' integrity='sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo' crossorigin='anonymous'></script>"
				+"<script src='https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js' integrity='sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1' crossorigin='anonymous'></script>"
				+"<script src='https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js' integrity='sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM' crossorigin='anonymous'></script>"
				+"<link rel='stylesheet' href='style.css'>"
				+"<link rel='shortcut icon' href='italia.png' />"
				+"</head>"
				+"<body>");
		
		//Servlet case
		CloudantPalabraStore store = new CloudantPalabraStore();
		System.out.println(request.getServletPath());
		switch(request.getServletPath())
		{
			case "/log":
				
				
/////////////////////////////////////////////////////////////////
				

				
				
//////////////////////////////////////////////////////////////////
			
				
				
				/*PersonalityInsights servicePI = new PersonalityInsights("2019-09-09");
				IamOptions options = new IamOptions.Builder()
				  .apiKey("s0cAhv538Lz8jcqUUdegmrsEAzh3595XdIjeGqeLvSG-")
				  .build();
				servicePI.setIamCredentials(options);
				
				servicePI.setEndPoint("https://gateway-lon.watsonplatform.net/personality-insights/api");

				// Demo content from Moby Dick by Hermann Melville (Chapter 1)
				String text = "Call me Ishmael please. Some years ago-never mind how long precisely-having "
					    + "little or no money in my purse, and nothing particular to interest me on shore, "
					    + "I thought I would sail about a little and see the watery part of the world. "
					    + "It is a way I have of driving off the spleen and regulating the circulation. "
					    + "Whenever I find myself growing grim about the mouth; whenever it is a damp, "
					    + "drizzly November in my soul; whenever I find myself involuntarily pausing before "
					    + "coffin warehouses, and bringing up the rear of every funeral I meet; and especially "
					    + "whenever my hypos get such an upper hand of me, that it requires a strong moral "
					    + "principle to prevent me from deliberately stepping into the street, and methodically "
					    + "knocking people's hats off-then, I account it high time to get to sea as soon as I can. "
					    + "This is my substitute for pistol and ball. With a philosophical flourish Cato throws himself "
					    + "upon his sword; I quietly take to the ship. There is nothing surprising in this. "
					    + "If they but knew it, almost all men in their degree, some time or other, cherish "
					    + "very nearly the same feelings towards the ocean with me. There now is your insular "
					    + "city of the Manhattoes, belted round by wharves as Indian isles by coral reefs-commerce surrounds "
					    + "it with her surf. Right and left, the streets take you waterward.";

				ProfileOptions optionsPI = new ProfileOptions.Builder()
				  .text(text)
				  .build();

				List<Trait> profile = servicePI.profile(optionsPI).execute().getPersonality();
				
				
				String content = "";
				StringBuilder contentBuilder = new StringBuilder();
				try {
				    BufferedReader in = new BufferedReader(new FileReader("Chart.js-2.8.0/samples/charts/bar/horizontal.html"));
				    String str;
				    while ((str = in.readLine()) != null) {
				    	contentBuilder.append(str);
				    }
				    in.close();
				} catch (IOException ioe) {
					System.out.println("IOException" + ioe);
					out.println("IOException" + ioe);
				}
				content = contentBuilder.toString();
				
				
				
				for(int i=0;i<profile.size();i++) { 
					String PIJSON = profile.get(i).toString();
					JsonParser parserPI = new JsonParser();
					JsonObject rootObjPI = parserPI.parse(PIJSON).getAsJsonObject();
					content += new BigDecimal(rootObjPI.get("percentile").toString()).toPlainString() + ", ";
					System.out.println(rootObjPI.get("name").toString() + " " + new BigDecimal(rootObjPI.get("percentile").toString()).toPlainString());
				}
				
				
				String content2 = "";
				StringBuilder contentBuilder2 = new StringBuilder();
				try {
				    BufferedReader in2 = new BufferedReader(new FileReader("Chart.js-2.8.0/samples/charts/bar/horizontalpt2.html"));
				    String str2;
				    while ((str2 = in2.readLine()) != null) {
				    	contentBuilder2.append(str2);
				    }
				    in2.close();
				} catch (IOException ioe) {
					System.out.println("IOException" + ioe);
					out.println("IOException" + ioe);
				}
				content2 = contentBuilder2.toString();
				
				//String pagehtml = content + content2;
				
				System.out.println("stampo " + content2);
				//out.println(pagehtml);
				out.println("<html><body><form action='hablar' method='POST'>"
				+"<li>Inserire username: <input type='text' name='username'>"
				+"<li>Inserire password: <input type='password' name='password'>"
				+"<input type='submit' value='Invialo'>"
				+"</form>");
				
			/////////////////////////////////////////////	
				
				
				
				
				*/
				////////////////////////////////////////////////////
				
						
				
				if(store.getDB() == null)
					  out.println("DB non esistente");
				else {
					if (store.getAll(request.getParameter("username"), request.getParameter("password"))) {
						response.sendRedirect("/asrTomcatEjemploCloudant/main.jsp");
					} 
					else {
						response.sendRedirect("/asrTomcatEjemploCloudant/?e");
					}
				}
				
				
				break;
				
			case "/add":
				Palabra palabra = new Palabra();
				String parametro = request.getParameter("username");
				String psw = request.getParameter("password");


				if(store.getDB() == null) 
				{
					out.println("Errore DB vuoto ");
				}
				else
				{
					palabra.setName(parametro);
					palabra.setPassword(psw);
					if (!store.ckeckExisting(parametro)) {
						out.println(String.format("Utente registrato: %s", palabra.getName()));
						store.persist(palabra);
						response.sendRedirect("/asrTomcatEjemploCloudant?n=" + palabra.getName());
					}			    	  
					else {
						response.sendRedirect("/asrTomcatEjemploCloudant?u");
					}
				}
				
				break;
				
			/*case "/trad":
				String text = request.getParameter("text");
				String source = request.getParameter("source");
				String dest = request.getParameter("dest");
				Traduttore tra = new Traduttore();
				String traduzione = tra.getTranslation(text, source, dest);
				
				out.println("Ecco la traduzione " + traduzione);
				response.sendRedirect("/asrTomcatEjemploCloudant/main.jsp?azione=" + traduzione);
				
				break;
			*/	
				
		}
		
		
		
		
		out.println("</body>"
					+ "</html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
