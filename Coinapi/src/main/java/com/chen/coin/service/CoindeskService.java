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
	 
	 
	 public Coindesk findById(Integer id) {
		 Coindesk todo = coindeskDao.findById(id).get();
		 return todo;
	 }

	 public Coindesk updateTodo(Integer id,Coindesk coindesk) {
	        try {
	        	Coindesk resCoindesk = findById(id);
//	            Integer status = coindesk.getStatus();
//	            resCoindesk.setStatus(stastus);
	            return coindeskDao.save(resCoindesk);
	        }catch (Exception exception) {
	            return null;
	        }

	    }
	 

	 public Boolean deleteTodo(Integer id) {
	     try {
	    	 Coindesk resCoindesk = findById(id);
	    	 coindeskDao.deleteById(id);
	            return true;
	        } catch (Exception exception) {
	            return false;
	        }
	  }
	 
}
