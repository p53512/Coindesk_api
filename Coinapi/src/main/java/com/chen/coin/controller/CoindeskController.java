package com.chen.coin.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.chen.coin.entity.Bpi;
import com.chen.coin.entity.Coindesk;
import com.chen.coin.entity.CoindeskVo;
import com.chen.coin.service.CoindeskService;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

@RestController
public class CoindeskController {

	@Autowired
	CoindeskService coindeskService;

	public static JSONObject callCoindesk() throws JSONException {
		RestTemplate restTemplate = new RestTemplate();
		String str = restTemplate.getForObject("https://api.coindesk.com/v1/bpi/currentprice.json", String.class);
		JSONObject json = JSONObject.fromObject(str);
		CoindeskVo vo = (CoindeskVo) JSONObject.toBean(json, CoindeskVo.class);
		System.err.println(vo.getBpi().getEUR().getCode());
		
		return json;
	}
	
	public static List<Coindesk> turnCoinAPi() {
		List<Coindesk> list = new ArrayList<Coindesk>();
		Coindesk coin = new Coindesk();
		JSONObject json = callCoindesk();
		CoindeskVo vo = (CoindeskVo) JSONObject.toBean(json, CoindeskVo.class);
		List<Bpi> listbpi = new ArrayList<Bpi>();
		vo.getBpi().getEUR().getCode();
		vo.getBpi().getEUR();
		vo.getBpi().getEUR();
		
		
		
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
		CoindeskVo vo = (CoindeskVo) JSONObject.toBean(json, CoindeskVo.class);
		Coindesk coin = new Coindesk();
		coin.setCode(vo.getBpi().getEUR().getCode());
		coin.setCodename("歐元");
		
		
		
	}
	
	  public void saveCoin( Coindesk coindesk) {
			coindeskService.save(coindesk);
		    }
	
	@PutMapping("/todos/{id}")
	    public void upadteTodo( String coin,  Coindesk coindesk) {
		coindeskService.updateTodo(coin ,coindesk);
	    }
	    
	@DeleteMapping("/todos/{id}")
	    public void deleteTodo( String coin) {
		coindeskService.deleteTodo(coin);
	    }

}
