package asr.proyectoFinal.services;
import java.util.ArrayList;
import java.util.HashMap;
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
import com.ibm.watson.developer_cloud.tone_analyzer.v3.ToneAnalyzer;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneChatOptions;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneChatScore;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.Utterance;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.UtteranceAnalysis;

public class Toni
{
	public static ArrayList<String> getToni(String text)
	{
		IamOptions options = new IamOptions.Builder()
				  .apiKey("1TaN_tydzQHN6GPBEhikNOsrR2dieuJSLKUGO6jF_1gd")
				  .build();

				ToneAnalyzer toneAnalyzer = new ToneAnalyzer("2017-09-21", options);
				toneAnalyzer.setEndPoint("https://gateway-lon.watsonplatform.net/tone-analyzer/api");

				
				List<Utterance> utterances = new ArrayList<Utterance>();
				utterances.add(new Utterance.Builder()
				  .text(text)
				  .user("customer")
				  .build());

				ToneChatOptions toneChatOptions = new ToneChatOptions.Builder()
				  .utterances(utterances)
				  .build();
				
				//HashMap<String, ArrayList<String>> valor = new HashMap<String, ArrayList<String>>();
				ArrayList<String> valToni = new ArrayList<String>();
				
				
			    UtteranceAnalysis utteranceAnalyses = toneAnalyzer.toneChat(toneChatOptions).execute().getUtterancesTone().get(0);
			    String nombres = utteranceAnalyses.getUtteranceText();
			    valToni.add(nombres);
			    Double totaltone=0.0;
			    //out.println(utteranceAnalyses.getUtteranceText()+"-->"+utteranceAnalyses.getTones());
				for (ToneChatScore a:utteranceAnalyses.getTones()) {
					totaltone+= a.getScore();
				}
				for (ToneChatScore a:utteranceAnalyses.getTones()) {
					Double valoretone;
					String nometone =a.getToneName();
					String rate= Double.toString(Math.rint(valoretone= a.getScore()*100/totaltone));
					valToni.add(nometone);
					valToni.add(rate);	
				}
				System.out.println(totaltone);
		return valToni;
	}
}