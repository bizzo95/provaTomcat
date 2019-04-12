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
import com.ibm.watson.developer_cloud.language_translator.v3.LanguageTranslator;
import com.ibm.watson.developer_cloud.language_translator.v3.model.TranslateOptions;
import com.ibm.watson.developer_cloud.language_translator.v3.model.TranslationResult;
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

/**
 * Servlet implementation class Controller
 */
@WebServlet(urlPatterns = {"/listar", "/insertar", "/hablar"})
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		PrintWriter out = response.getWriter();
		//out.println("<html><head><meta charset=\"UTF-8\"></head><body>");
		
		CloudantPalabraStore store = new CloudantPalabraStore();
		System.out.println(request.getServletPath());
		switch(request.getServletPath())
		{
			case "/listar":
				
				
/////////////////////////////////////////////////////////////////
				

				
				
//////////////////////////////////////////////////////////////////
			
				
				
				PersonalityInsights servicePI = new PersonalityInsights("2019-09-09");
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
				
				
				
				LanguageTranslator service = new LanguageTranslator("2018-04-09");
				service.setUsernameAndPassword("user","password");
				//service.setEndPoint("https://gateway-lon.watsonplatform.net/asssistant/api");
				service.setEndPoint("https://gateway-lon.watsonplatform.net/language-translator/api");
				IamOptions iamOptions = new IamOptions.Builder()
				  .apiKey("o1pXiTW5Roc878T6X7f2Y5SKa1trWLc2ZhlhKe-uGzfA")
				  .build();
				service.setIamCredentials(iamOptions);

				TranslateOptions translateOptions = new TranslateOptions.Builder()
				  .addText("the dog all over the table")
				  .source(Language.ENGLISH)
				  .target(Language.SPANISH)
				  .build();
				TranslationResult translationResult = service.translate(translateOptions).execute();
				

				String traduccionJSON = translationResult.toString();
				JsonParser parser = new JsonParser();
				JsonObject rootObj = parser.parse(traduccionJSON).getAsJsonObject();
				JsonArray traducciones = rootObj.getAsJsonArray("translations");
				String traduccionPrimera = "";
				if(traducciones.size()>0) traduccionPrimera = traducciones.get(0).getAsJsonObject().get("translation").getAsString();
				

				out.println("Risultato traduzione " + traduccionPrimera);
				
				////////////////////////////////////////////////////
				
						
				
				if(store.getDB() == null)
					  out.println("No hay DB");
				else {
					if (store.getAll(request.getParameter("username"), request.getParameter("password"))) {
						out.println("Accesso consentito a");
					} 
					else {
						response.sendRedirect("/asrTomcatEjemploCloudant?e");
					}
					//out.println("Las Palabras en la BD Cloudant ;):  <br />" + store.getAll(request.getParameter("username"), request.getParameter("password")));
				}
				
				
				break;
				
			case "/insertar":
				Palabra palabra = new Palabra();
				String parametro = request.getParameter("username");
				String psw = request.getParameter("password");

				if(parametro==null)
				{
					out.println("usage: /insertar?palabra=palabra_a_traducir");
				}
				else
				{
					if(store.getDB() == null) 
					{
						out.println(String.format("Palabra: %s", palabra));
					}
					else
					{
						palabra.setName(parametro);
						palabra.setPassword(psw);
						if (!store.ckeckExisting(parametro)) {
							out.println(String.format("Utente registrato: %s", palabra.getName()));
							store.persist(palabra);
						}			    	  
						else {
							response.sendRedirect("/asrTomcatEjemploCloudant?u");
						}
					}
				}
				break;
				
			case "/hablar":
				System.out.println("ciao");
				out.println("ciao");
				break;
				
				
				
		}
		
		
		
		
		out.println("</body></html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
