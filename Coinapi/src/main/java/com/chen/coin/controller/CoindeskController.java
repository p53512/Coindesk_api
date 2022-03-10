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
import com.chen.coin.entity.Money;
import com.chen.coin.entity.Time;
import com.chen.coin.service.CoindeskService;
import com.sun.xml.bind.annotation.OverrideAnnotationOf;

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

	public List<Coindesk> turnCoinAPi() {
		List<Coindesk> list = new ArrayList<Coindesk>();
		JSONObject json = callCoindesk();
		CoindeskVo vo = (CoindeskVo) JSONObject.toBean(json, CoindeskVo.class);
		Time time = turnTime(vo.getTime());
		vo.setTime(time);
		coindeskService.findById(vo.getBpi().getEUR().getCode());
		coindeskService.findById(vo.getBpi().getGBP().getCode());
		coindeskService.findById(vo.getBpi().getUSD().getCode());
		
		list.add(coindeskService.findById(vo.getBpi().getEUR().getCode()));
		list.add(coindeskService.findById(vo.getBpi().getGBP().getCode()));
		list.add(coindeskService.findById(vo.getBpi().getUSD().getCode()));
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
		
		Time time = turnTime(vo.getTime());
		
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

	private static Time turnTime(Time vo) {
		SimpleDateFormat ISOsdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		SimpleDateFormat UTCsdf = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss", Locale.ENGLISH);
		SimpleDateFormat UKsdf = new SimpleDateFormat("MMM dd, yyyy 'at' HH:mm", Locale.ENGLISH);
		SimpleDateFormat sd = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		UTCsdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		ISOsdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		UKsdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date UTCdate = null;
		Date ISOdate = null;
		Date UKdate = null;
		try {
			UTCdate = UTCsdf.parse(vo.getUpdated());
			ISOdate = ISOsdf.parse(vo.getUpdatedISO());
			UKdate = UKsdf.parse(vo.getUpdateduk());
			vo.setUpdated(sd.format(UTCdate));
			vo.setUpdatedISO(sd.format(ISOdate));
			vo.setUpdateduk(sd.format(UKdate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
		return vo;
	}
}
