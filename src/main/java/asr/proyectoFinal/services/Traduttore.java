package asr.proyectoFinal.services;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ibm.watson.developer_cloud.language_translator.v3.LanguageTranslator;
import com.ibm.watson.developer_cloud.language_translator.v3.model.TranslateOptions;
import com.ibm.watson.developer_cloud.language_translator.v3.model.TranslationResult;
import com.ibm.watson.developer_cloud.language_translator.v3.util.Language;
import com.ibm.watson.developer_cloud.service.security.IamOptions;

public class Traduttore
{
	public static String getTranslation(String text, String source, String dest)
	{
		LanguageTranslator service = new LanguageTranslator("2018-04-09");
		service.setUsernameAndPassword("user","password");
		service.setEndPoint("https://gateway-lon.watsonplatform.net/language-translator/api");
		IamOptions iamOptions = new IamOptions.Builder()
		  .apiKey("o1pXiTW5Roc878T6X7f2Y5SKa1trWLc2ZhlhKe-uGzfA")
		  .build();
		service.setIamCredentials(iamOptions);
		
		String model = source + "-" + dest;
		TranslateOptions translateOptions = new TranslateOptions.Builder()
		  .addText(text)
		  .modelId(model)
		  .build();
		TranslationResult translationResult = service.translate(translateOptions).execute();
		
		String tradJSON = translationResult.toString();
		JsonParser parser = new JsonParser();
		JsonObject tradObj = parser.parse(tradJSON).getAsJsonObject();
		JsonArray traduzione = tradObj.getAsJsonArray("translations");
		String risultato = traduzione.get(0).getAsJsonObject().get("translation").getAsString();
		
		return risultato;
	}
}