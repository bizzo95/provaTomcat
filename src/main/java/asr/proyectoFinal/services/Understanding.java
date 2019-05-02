package asr.proyectoFinal.services;

import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.NaturalLanguageUnderstanding;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.AnalysisResults;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.AnalyzeOptions;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.EntitiesOptions;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.KeywordsOptions;
import com.ibm.watson.developer_cloud.service.security.IamOptions;

public class Understanding 
{
public static ArrayList<JsonArray> getnlu (String text) {
	
	IamOptions options = new IamOptions.Builder().apiKey("NRef7k6sK8Okdh2YVvKdBq2bgTlgODq8DPuk2c8s-F5K").build();
	NaturalLanguageUnderstanding naturalLanguageUnderstanding = new NaturalLanguageUnderstanding("2018-11-16", options);
	naturalLanguageUnderstanding.setEndPoint("https://gateway-lon.watsonplatform.net/natural-language-understanding/api");

	 
	EntitiesOptions entitiesOptions = new EntitiesOptions.Builder()
		.emotion(true)
		.sentiment(true)
		.mentions(true)
		.limit(50)
		.build();

	KeywordsOptions keywordsOptions = new KeywordsOptions.Builder()
		 .emotion(true)
		 .sentiment(true)
	     .limit(5)
	     .build();
		
	com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.Features features=new com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.Features.Builder()  
		  .entities(entitiesOptions)
		  .keywords(keywordsOptions)
		  .build();

	AnalyzeOptions parameters = new AnalyzeOptions.Builder()
		  .text(text)
		  .features(features)
		  .build();

	AnalysisResults response1 = naturalLanguageUnderstanding
		  .analyze(parameters)
		  .execute();
		
	String nluJSON = response1.toString();
	JsonParser parser = new JsonParser();
	JsonObject rootObj = parser.parse(nluJSON).getAsJsonObject();
	JsonArray nlu = rootObj.getAsJsonArray("usage:");				
	JsonArray KEY= rootObj.getAsJsonArray("keywords");
	JsonArray ent =rootObj.getAsJsonArray("entities");
				
	//arraylist di keywords
	ArrayList<String>a=new ArrayList<String>();
	ArrayList<String>b=new ArrayList<String>();
	ArrayList<String>c=new ArrayList<String>();
	ArrayList<String>emo=new ArrayList<String>();
	
	//arraylist di entities
	ArrayList<String>d=new ArrayList<String>();
	ArrayList<String>e=new ArrayList<String>();
	ArrayList<String>f=new ArrayList<String>();
	ArrayList<String>g=new ArrayList<String>();
	ArrayList<String>h=new ArrayList<String>();
		
		for(int i=0;i<KEY.size();i++) {
			 a.add(KEY.get(i).getAsJsonObject().get("text").getAsString());
			 b.add(KEY.get(i).getAsJsonObject().get("relevance").getAsString());
			 c.add(KEY.get(i).getAsJsonObject().get("sentiment").getAsJsonObject().get("score").getAsString());
			 emo.add(KEY.get(i).getAsJsonObject().get("emotion").getAsJsonObject().get("anger").getAsString());
			 emo.add(KEY.get(i).getAsJsonObject().get("emotion").getAsJsonObject().get("disgust").getAsString());
			 emo.add(KEY.get(i).getAsJsonObject().get("emotion").getAsJsonObject().get("fear").getAsString());
			 emo.add(KEY.get(i).getAsJsonObject().get("emotion").getAsJsonObject().get("joy").getAsString());
			 emo.add(KEY.get(i).getAsJsonObject().get("emotion").getAsJsonObject().get("sadness").getAsString());
			  }
		
		 
		 for(int i=0;i<ent.size();i++) {
			 d.add(ent.get(i).getAsJsonObject().get("type").getAsString());
			 e.add(ent.get(i).getAsJsonObject().get("text").getAsString());
			 f.add(ent.get(i).getAsJsonObject().get("relevance").getAsString());
			 g.add(ent.get(i).getAsJsonObject().get("sentiment").getAsJsonObject().get("score").getAsString());	 
		 }

 
		 ArrayList<JsonArray> result= new ArrayList<JsonArray>();
		 result.add(KEY);
		 result.add(ent);

	return result; 
} 	



}

