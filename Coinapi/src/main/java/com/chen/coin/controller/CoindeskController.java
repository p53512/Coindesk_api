package com.chen.coin.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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
		vo.getTime().getUpdated();
		vo.getTime().getUpdatedISO();
		vo.getTime().getUpdateduk();
		return null;
	}

	@GetMapping("/todos")
	public Iterable<Coindesk> getTodoList() throws Exception {
		Iterable<Coindesk> coinList = coindeskService.getcoindes();
		return coinList;
	}

	public static void main(String[] args) throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		String str = restTemplate.getForObject("https://api.coindesk.com/v1/bpi/currentprice.json", String.class);
		JSONObject json = callCoindesk();
		CoindeskVo vo = (CoindeskVo) JSONObject.toBean(json, CoindeskVo.class);
		
		SimpleDateFormat ISOsdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		SimpleDateFormat UTCsdf = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss", Locale.US);
		SimpleDateFormat sd = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String str1 = null;
		UTCsdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date date = null;
		try {
			date = UTCsdf.parse(vo.getTime().getUpdated());
			str1 = sd.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
		
		System.err.println(str1);
		System.err.println("======================================================");
		
		System.err.println(vo.getTime().getUpdated());
		System.err.println(vo.getTime().getUpdatedISO());
		System.err.println(vo.getTime().getUpdateduk());
	}

	public void saveCoin() {
		JSONObject json = callCoindesk();
		CoindeskVo apivo = (CoindeskVo) JSONObject.toBean(json, CoindeskVo.class);
		Coindesk vo = new Coindesk();
		vo.setCode(apivo.getBpi().getEUR().getCode());
		vo.setCodename("歐元");
		vo.setDescription(apivo.getBpi().getEUR().getDescription());
		vo.setRate(apivo.getBpi().getEUR().getRate());
		double rate_float = Double.valueOf(apivo.getBpi().getEUR().getRate_float());
		vo.setRate_float(rate_float);
		vo.setSymbol(apivo.getBpi().getEUR().getSymbol());
		coindeskService.save(vo);
		
	}

	@PutMapping("/todos/{id}")
	public void upadteTodo(String coin, Coindesk coindesk) {
		coindeskService.updateTodo(coin, coindesk);
	}

	@DeleteMapping("/todos/{id}")
	public void deleteTodo(String coin) {
		coindeskService.deleteTodo(coin);
	}

}
