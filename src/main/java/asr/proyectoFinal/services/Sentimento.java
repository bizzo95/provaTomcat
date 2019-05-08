package asr.proyectoFinal.services;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.math.BigDecimal;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ibm.watson.developer_cloud.personality_insights.v3.PersonalityInsights;
import com.ibm.watson.developer_cloud.personality_insights.v3.model.ProfileOptions;
import com.ibm.watson.developer_cloud.personality_insights.v3.model.Trait;
import com.ibm.watson.developer_cloud.service.security.IamOptions;

public class Sentimento
{
	public static ArrayList<String> getSentimenti(String text)
	{
		PersonalityInsights servicePI = new PersonalityInsights("2019-09-09");
		IamOptions options = new IamOptions.Builder()
		  .apiKey("s0cAhv538Lz8jcqUUdegmrsEAzh3595XdIjeGqeLvSG-")
		  .build();
		servicePI.setIamCredentials(options);
		
		servicePI.setEndPoint("https://gateway-lon.watsonplatform.net/personality-insights/api");

		ProfileOptions optionsPI = new ProfileOptions.Builder()
		  .text(text)
		  .build();

		List<Trait> profile = servicePI.profile(optionsPI).execute().getPersonality();		
		ArrayList<String> sentimenti = new ArrayList<String>();
		
		for(int i=0;i<profile.size();i++) { 
			String PIJSON = profile.get(i).toString();
			JsonParser parserPI = new JsonParser();
			JsonObject rootObjPI = parserPI.parse(PIJSON).getAsJsonObject();
			sentimenti.add(rootObjPI.get("name").toString());
			double d = Double.parseDouble(new BigDecimal(rootObjPI.get("percentile").toString()).toPlainString()) * 100;
			sentimenti.add(Double.toString(d));
		}
		return sentimenti;
	}
}