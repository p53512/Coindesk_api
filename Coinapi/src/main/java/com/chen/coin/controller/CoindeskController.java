package com.chen.coin.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chen.coin.entity.Coindesk;
import com.chen.coin.service.CoindeskService;


@RestController
public class CoindeskController {

	
	 @Autowired
	 CoindeskService coindeskService;
	 
	 @GetMapping("/todos")
	    public Iterable<Coindesk> getTodoList () throws Exception{
	        Iterable<Coindesk> todoList = coindeskService.getcoindes();
	        return todoList;
	    }
//	 public static void main(String[] args) throws IOException, JSONException {
//		    JSONObject json = readJsonFromUrl("https://graph.facebook.com/19292868552");
//		    System.out.println(json.toString());
//		    System.out.println(json.get("id"));
//		  }
//	 public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException{
//		InputStream is = new URL(url).openStream();
//	    try {
//	      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
//	      String jsonText = readAll(rd);
//	      JSONObject json = new JSONObject(jsonText);
//	      return json;
//	    } finally {
//	      is.close();
//	    }
//	    
//	    private static String readAll(Reader rd) throws IOException {
//	        StringBuilder sb = new StringBuilder();
//	        int cp;
//	        while ((cp = rd.read()) != -1) {
//	          sb.append((char) cp);
//	        }
//	        return sb.toString();
//	      }
//	}
	
}
