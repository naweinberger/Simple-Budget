package com.example.simplebudget;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.widget.Toast;

public class JsonReader {
	public String getJson() {
		return "";
	}
	
	public static StringBuilder inputStreamToString(InputStream is) {
		   String rLine = "";
		   StringBuilder answer = new StringBuilder();
		   BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		 
		   try {
		    while ((rLine = rd.readLine()) != null) {
		     answer.append(rLine);
		    }
		   }
		 
		   catch (IOException e) {
		    // e.printStackTrace();
		    
		   }
		   return answer;
		  }
}
