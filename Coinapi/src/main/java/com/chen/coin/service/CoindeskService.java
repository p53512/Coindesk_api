package com.chen.coin.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chen.coin.dao.CoindeskRepository;
import com.chen.coin.entity.Coindesk;

import net.sf.json.JSONException;

@Service("CoindeskService")
public class CoindeskService {
	@Autowired
	CoindeskRepository coindeskRepository;

	public Iterable<Coindesk> getcoindes() {
		return coindeskRepository.findAll();
	}

	public Optional<Coindesk> findById(String coin) {
		Optional<Coindesk> rescoin = coindeskRepository.findById(coin);
		return rescoin;
	}

	@Transactional
	public Coindesk save(Coindesk coin) {
		Coindesk rescoin = coindeskRepository.save(coin);
		return rescoin;
	}

	@Transactional
	public Coindesk updateCoin(String coin, Coindesk coindesk) throws Exception {
			Optional<Coindesk> isExistTodo = findById(coin);
			if (!isExistTodo.isPresent()) {
				throw new Exception();
			}
			Coindesk newCoin = isExistTodo.get();
			newCoin.setCode(coindesk.getCode());
			newCoin.setCodename(coindesk.getCodename());
			newCoin.setDescription(coindesk.getDescription());
			newCoin.setRate(coindesk.getRate());
			newCoin.setRate_float(coindesk.getRate_float());
			newCoin.setSymbol(coindesk.getSymbol());
			Coindesk rescoin = coindeskRepository.save(newCoin);
			return rescoin;

	}

	public Boolean deleteTodo(String coin) {
		Optional<Coindesk> isExistTodo = findById(coin);
		if (!isExistTodo.isPresent()) {
			return false;
		}
		coindeskRepository.deleteById(coin);
		return true;
	}

}
