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
		
		//Servlet case
		CloudantPalabraStore store = new CloudantPalabraStore();
		System.out.println(request.getServletPath());
		switch(request.getServletPath())
		{
			case "/log":
										
				if(store.getDB() == null)
					  out.println("DB non esistente");
				else {
					if (store.getAll(request.getParameter("username"), request.getParameter("password"))) {
						response.sendRedirect("/TexTool/main.jsp");
					} 
					else {
						response.sendRedirect("/TexTool/?e");
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
						response.sendRedirect("/TexTool?n=" + palabra.getName());
					}			    	  
					else {
						response.sendRedirect("/TexTool?u");
					}
				}
				
				break;
				
				
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
