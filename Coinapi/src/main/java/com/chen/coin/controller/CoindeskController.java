package com.chen.coin.controller;


import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.chen.coin.entity.Coindesk;
import com.chen.coin.service.CoindeskService;

@RestController
public class CoindeskController {

	@Autowired
	CoindeskService coindeskService;

	public static JSONObject callCoindesk() throws JSONException {
		RestTemplate restTemplate = new RestTemplate();
		String str = restTemplate.getForObject("https://api.coindesk.com/v1/bpi/currentprice.json", String.class);
		JSONObject json = new JSONObject(str);
		JSONObject a = json.getJSONObject("bpi").getJSONObject("EUR");
		System.err.println(a);
		
		return json;
	}
	
	public static List<Coindesk> turnCoinAPi() {
		List<Coindesk> list = new ArrayList<Coindesk>();
		Coindesk coin = new Coindesk();
		
		return null;
	}

	@GetMapping("/todos")
	public Iterable<Coindesk> getTodoList() throws Exception {
		Iterable<Coindesk> coinList = coindeskService.getcoindes();
		return coinList;
	}

	public static void main(String[] args) throws Exception {
//		    JSONObject json = readJsonFromUrl("https://api.coindesk.com/v1/bpi/currentprice.json");
		RestTemplate restTemplate = new RestTemplate();
		String str = restTemplate.getForObject("https://api.coindesk.com/v1/bpi/currentprice.json", String.class);
		JSONObject json = callCoindesk();
		Coindesk coin = new Coindesk();
		List list = new ArrayList();
		
		

		
		
		
	}
	
	@PutMapping("/todos/{id}")
	    public void upadteTodo( Integer id,  Coindesk coindesk) {
		coindeskService.updateTodo(id ,coindesk);
	    }
	    
	@DeleteMapping("/todos/{id}")
	    public void deleteTodo( Integer id) {
		coindeskService.deleteTodo(id);
	    }

}
