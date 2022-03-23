package com.chen.coin.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
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

import com.chen.coin.entity.Bpi;
import com.chen.coin.entity.Coindesk;
import com.chen.coin.entity.CoindeskVo;
import com.chen.coin.entity.Money;
import com.chen.coin.entity.Time;
import com.chen.coin.entity.Turndate;
import com.chen.coin.service.CoindeskService;
import com.sun.xml.bind.annotation.OverrideAnnotationOf;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

@RestController
@RequestMapping("/api")
public class CoindeskController {

	@Autowired
	CoindeskService coindeskService;

	@GetMapping("/callCoindesk/{url}")
	public ResponseEntity<JSONObject> callCoindesk(@PathVariable String url) throws JSONException, UnsupportedEncodingException {
		String encodedURL = URLDecoder.decode(url, "UTF-8");
		RestTemplate restTemplate = new RestTemplate();
		String str = restTemplate.getForObject(encodedURL, String.class);
		JSONObject json = JSONObject.fromObject(str);

		return ResponseEntity.status(HttpStatus.OK).body(json);
	}

	@GetMapping("/turnCoinApi/{url}")  // B. 幣別相關資訊（幣別，幣別中文名稱，以及匯率）。
	public ResponseEntity<Turndate> turnCoinAPi(@PathVariable String url) throws Exception {
		String encodedURL = URLDecoder.decode(url, "UTF-8");
		RestTemplate restTemplate = new RestTemplate();
		String str = restTemplate.getForObject(encodedURL, String.class);
		JSONObject json = JSONObject.fromObject(str);
		CoindeskVo vo = (CoindeskVo) JSONObject.toBean(json, CoindeskVo.class);
		Time time = turnTime(vo.getTime());
		
		Turndate returnDate = turnCoin(vo);
		returnDate.setTime(time);
		
		return ResponseEntity.status(HttpStatus.OK).body(returnDate);
	}

	@GetMapping("/getCoindesk")
	public ResponseEntity getCoindesk() throws Exception {
		Iterable<Coindesk> coinList = coindeskService.getcoindes();
		return ResponseEntity.status(HttpStatus.OK).body(coinList);
	}

	@GetMapping("/getCoindesk/{id}")
	public Optional<Coindesk> getCoindesk(@PathVariable String id) throws Exception {
		Optional<Coindesk> coinList = coindeskService.findById(id);
		return coinList;
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

	@PutMapping("/updateCoindesk/{coin}")
	public ResponseEntity upadteCoindesk(@PathVariable(value="coin") String coin,@RequestBody Coindesk coindesk) {
		Coindesk rescoin = null;
		try {
			rescoin = coindeskService.updateCoin(coin ,coindesk);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return ResponseEntity.status(HttpStatus.OK).body(rescoin);
	}

	@DeleteMapping("/deleteCoin/{id}")
	public ResponseEntity  deleteCoin(@PathVariable String id) {
		 Boolean rlt = coindeskService.deleteTodo(id);
		 if (!rlt) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id 不存在");
	        }
	        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
	}

	private Time turnTime(Time vo) {
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
	private Turndate turnCoin(CoindeskVo vo) throws Exception {
	Coindesk EURcoindesk = new Coindesk();
	Coindesk GBPcoindesk = new Coindesk();
	Coindesk USDcoindesk = new Coindesk();
	
	EURcoindesk.setRate(vo.getBpi().getEUR().getRate());
	EURcoindesk.setRate_float( Double.valueOf(vo.getBpi().getEUR().getRate_float()));
	GBPcoindesk.setRate(vo.getBpi().getGBP().getRate());
	GBPcoindesk.setRate_float( Double.valueOf(vo.getBpi().getGBP().getRate_float()));
	USDcoindesk.setRate(vo.getBpi().getUSD().getRate());
	USDcoindesk.setRate_float( Double.valueOf(vo.getBpi().getUSD().getRate_float()));
	
	Coindesk EURrescoin = coindeskService.updateCoin(vo.getBpi().getEUR().getCode() ,EURcoindesk);
	Coindesk GBPrescoin = coindeskService.updateCoin(vo.getBpi().getGBP().getCode() ,GBPcoindesk);
	Coindesk USDrescoin = coindeskService.updateCoin(vo.getBpi().getUSD().getCode() ,USDcoindesk);
	
	Turndate returnDate = new Turndate();
	Bpi bpi = new Bpi();
	Money EUR = new Money();
	Money GBP = new Money();
	Money USD = new Money();
	returnDate.setBpi(bpi);
	returnDate.getBpi().setEUR(EUR);
	returnDate.getBpi().setGBP(GBP);
	returnDate.getBpi().setUSD(USD);
	returnDate.getBpi().getEUR().setCode(EURrescoin.getCode());
	returnDate.getBpi().getEUR().setCodename(EURrescoin.getCodename());
	returnDate.getBpi().getEUR().setRate(EURrescoin.getRate());
	returnDate.getBpi().getEUR().setRate_float(String.valueOf(EURrescoin.getRate_float()));
	returnDate.getBpi().getGBP().setCode(GBPrescoin.getCode());
	returnDate.getBpi().getGBP().setCodename(GBPrescoin.getCodename());
	returnDate.getBpi().getGBP().setRate(GBPrescoin.getRate());
	returnDate.getBpi().getGBP().setRate_float(String.valueOf(GBPrescoin.getRate_float()));
	returnDate.getBpi().getUSD().setCode(USDrescoin.getCode());
	returnDate.getBpi().getUSD().setCodename(USDrescoin.getCodename());
	returnDate.getBpi().getUSD().setRate(USDrescoin.getRate());
	returnDate.getBpi().getUSD().setRate_float(String.valueOf(USDrescoin.getRate_float()));
	
			
	return returnDate;
	}
}
