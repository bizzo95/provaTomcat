package asr.proyectoFinal.servlets;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.Buffer;
import java.nio.file.Files;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ibm.watson.developer_cloud.language_translator.v3.LanguageTranslator;
import com.ibm.watson.developer_cloud.language_translator.v3.model.TranslateOptions;
import com.ibm.watson.developer_cloud.language_translator.v3.model.TranslationResult;
import com.ibm.watson.developer_cloud.language_translator.v3.util.Language;
import com.ibm.watson.developer_cloud.service.security.IamOptions;

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
		out.println("<html><head><meta charset=\"UTF-8\"></head><body>");
		
		CloudantPalabraStore store = new CloudantPalabraStore();
		System.out.println(request.getServletPath());
		switch(request.getServletPath())
		{
			case "/listar":
				if(store.getDB() == null)
					  out.println("No hay DB");
				else
					out.println("Las Palabras en la BD Cloudant: :))<br />" + store.getAll());
				
				
				LanguageTranslator service = new LanguageTranslator("23-03-2018");
				service.setUsernameAndPassword("4q0_p1C2cXn-LZLGYaA1xYC8jbwAiWMgBtu71PuOnxA4", "https://gateway-lon.watsonplatform.net/language-translator/api");
				IamOptions iamOptions = new IamOptions.Builder()
				  .apiKey("<iam_api_key>")
				  .build();
				service.setIamCredentials(iamOptions);

				TranslateOptions translateOptions = new TranslateOptions.Builder()
				  .addText("dog")
				  .source(Language.ENGLISH)
				  .target(Language.SPANISH)
				  .build();
				TranslationResult translationResult = service.translate(translateOptions).execute();

				out.println("<h1> RISULTATO</h1>" + translationResult);
				
				
				
				break;
				
			case "/insertar":
				Palabra palabra = new Palabra();
				String parametro = request.getParameter("palabra");
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
						store.persist(palabra);
					    out.println(String.format("Almacenada la palabra: %s", palabra.getName(), " ", palabra.getPassword()));			    	  
					}
				}
				break;
		}
		out.println("</html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
