package com.childhoodmemories.webgames.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.util.EncodingUtils;
import org.json.JSONObject;

import android.util.Log;

public class JsonUtil {
	
	public static JSONObject getJsonObj(String fullName) throws IOException{

        File file = new File(fullName);
        Log.d("0-0-0-0-0-0",fullName);
            try {
                InputStream instream = new FileInputStream(file); 
            	StringBuilder stringBuilder = new StringBuilder();
                if (instream != null) 
                {
                	
                    InputStreamReader inputreader = new InputStreamReader(instream);
                    BufferedReader buffreader = new BufferedReader(inputreader);
                    
                    String line;
                    //∑÷––∂¡»°
                    while (( line = buffreader.readLine()) != null) {
                    	stringBuilder.append(line);
                    }
                    inputreader.close();
                    instream.close();
                }
                String content = stringBuilder.toString();
                Log.d("======000000=====", content);
                
                
            }
            catch (java.io.FileNotFoundException e) 
            {
                Log.d("================", "The File doesn't not exist.");
            } 
            catch (IOException e) 
            {
                 Log.d("===========", e.getMessage());
            }


		
		return new JSONObject();
	}
	
	
	
}
