package com.chen.coin.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping("/api")
public class CoindeskController {

	@Autowired
	CoindeskService coindeskService;

	@GetMapping("/callCoindesk")
	public static JSONObject callCoindesk(@PathVariable String uri) throws JSONException {
		RestTemplate restTemplate = new RestTemplate();
		String str = restTemplate.getForObject(uri, String.class);
		JSONObject json = JSONObject.fromObject(str);
		
		CoindeskVo vo = (CoindeskVo) JSONObject.toBean(json, CoindeskVo.class);
		System.err.println(vo.getBpi().getEUR().getCode());

		return json;
	}

	@GetMapping("/turnCoinAPi")
	public List<Coindesk> turnCoinAPi(@PathVariable String uri) {
		List<Coindesk> list = new ArrayList<Coindesk>();
		JSONObject json = callCoindesk(uri);
		CoindeskVo vo = (CoindeskVo) JSONObject.toBean(json, CoindeskVo.class);
		Time time = turnTime(vo.getTime());
		vo.setTime(time);
		coindeskService.findById(vo.getBpi().getEUR().getCode());
		coindeskService.findById(vo.getBpi().getGBP().getCode());
		coindeskService.findById(vo.getBpi().getUSD().getCode());
		
		return null;
	}

	@GetMapping("/getCoindesk")
	public ResponseEntity  getCoindesk() throws Exception {
		Iterable<Coindesk> coinList = coindeskService.getcoindes();
		return ResponseEntity.status(HttpStatus.OK).body(coinList);
	}

	@GetMapping("/getCoindesk/{id}")
	public Optional<Coindesk> getCoindesk(@PathVariable String id) throws Exception {
		Optional<Coindesk> coinList = coindeskService.findById(id);
		return coinList;
	}
	public static void main(String[] args) throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		String str = restTemplate.getForObject("https://api.coindesk.com/v1/bpi/currentprice.json", String.class);
		JSONObject json = callCoindesk(str);
		CoindeskVo vo = (CoindeskVo) JSONObject.toBean(json, CoindeskVo.class);
		Time time = turnTime(vo.getTime());
		
	}
	@PostMapping("/saveCoindesk")
	public ResponseEntity saveCoin(@RequestBody Coindesk coindesk) {
		Coindesk vo = new Coindesk();
		vo.setCode(coindesk.getCode());
		vo.setCodename(coindesk.getCodename());
		vo.setDescription(coindesk.getDescription());
		vo.setRate(coindesk.getRate());
		vo.setRate_float(coindesk.getRate_float());
		vo.setSymbol(coindesk.getSymbol());
		Coindesk rescoin = coindeskService.save(vo);
		return ResponseEntity.status(HttpStatus.CREATED).body(rescoin);
	}

	@PutMapping("/updateCoindesk/{id}")
	public ResponseEntity upadteCoindesk(@PathVariable String coin,@RequestBody Coindesk coindesk) {
		Coindesk rescoin = null;
		try {
			rescoin = coindeskService.updateCoin(coin ,coindesk);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return ResponseEntity.status(HttpStatus.OK).body(rescoin);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity  deleteTodo(@PathVariable String coin) {
		 Boolean rlt = coindeskService.deleteTodo(coin);
		 if (!rlt) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id 不存在");
	        }
	        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
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
