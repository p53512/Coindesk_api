package com.chen.coin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chen.coin.dao.CoindeskDao;
import com.chen.coin.entity.Coindesk;


@Service("CoindeskService")
public class CoindeskService {
	@Autowired
	CoindeskDao coindeskDao;
	
	 public Iterable<Coindesk> getcoindes() {
	        return coindeskDao.findAll();
	    }
}
