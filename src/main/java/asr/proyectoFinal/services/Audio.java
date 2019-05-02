package asr.proyectoFinal.services;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.ibm.watson.developer_cloud.service.security.IamOptions;
import com.ibm.watson.developer_cloud.text_to_speech.v1.TextToSpeech;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.SynthesizeOptions;
import com.ibm.watson.developer_cloud.text_to_speech.v1.websocket.BaseSynthesizeCallback;

public class Audio
{
	public static String getAudio(String text, String name) throws IOException
	{
		TextToSpeech service = new TextToSpeech();
		IamOptions options = new IamOptions.Builder()
		  .apiKey("fHMBqYgV1rNTs7QOwLAQNFc9pb74qK2iiepIQP-TOm7A")
		  .build();
		service.setIamCredentials(options);
		
		service.setEndPoint("https://gateway-lon.watsonplatform.net/text-to-speech/api");
		
		SynthesizeOptions synthesizeOptions = new SynthesizeOptions.Builder()
		  .text(text)
		  .accept(SynthesizeOptions.Accept.AUDIO_OGG_CODECS_OPUS)
		  .build();
		// a callback is defined to handle certain events, like an audio transmission or a timing marker
		// in this case, we'll build up a byte array of all the received bytes to build the resulting file
		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		service.synthesizeUsingWebSocket(synthesizeOptions, new BaseSynthesizeCallback() {
		  @Override
		  public void onAudioStream(byte[] bytes) {
		    // append to our byte array
		    try {
		      byteArrayOutputStream.write(bytes);
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
		  }
		});

		// quick way to wait for synthesis to complete, since synthesizeUsingWebSocket() runs asynchronously

	try {
		Thread.sleep(7000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

		// create file with audio data
		File dir = new File(".");
		dir.mkdir();
	//ruta dove inviare C:\\Users\\loren\\asrTomcatEjemploCloudant-master\\src\\main\\java\\asr\\proyectoFinal\\canciones
		
		//Date dat= new Date();
		//String data_corr= dat.getDate()+ "/" + dat.getMonth() + "/" + dat.getYear()+"_"+dat.getHours()+"-"+dat.getMinutes()+"-"+dat.getSeconds();
		name+= ".mp3";
		File filename = new File(dir, name);
		OutputStream fileOutputStream = new FileOutputStream(filename);			
		byteArrayOutputStream.writeTo(fileOutputStream);
		

		// clean up
		byteArrayOutputStream.close();
		
		fileOutputStream.close();
	
		
		return name;
	}
}