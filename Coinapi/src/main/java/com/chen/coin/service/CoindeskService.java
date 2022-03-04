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
	 
	 
	 public Coindesk findById(String coin) {
		 Coindesk rescoin = coindeskDao.findById(coin).get();
		 return rescoin;
	 }

	 public Coindesk save(Coindesk coin) {
		 
		 Coindesk rescoin =  coindeskDao.save(coin);
		 return rescoin;
	 }
	 
	 public Coindesk updateTodo(String coin,Coindesk coindesk) {
	        try {
	        	Coindesk resCoindesk = findById(coin);
//	            Integer status = coindesk.getStatus();
//	            resCoindesk.setStatus(stastus);
	            return coindeskDao.save(resCoindesk);
	        }catch (Exception exception) {
	            return null;
	        }

	    }
	 

	 public Boolean deleteTodo(String coin) {
	     try {
	    	 Coindesk resCoindesk = findById(coin);
	    	 coindeskDao.deleteById(coin);
	            return true;
	        } catch (Exception exception) {
	            return false;
	        }
	  }
	 
}
