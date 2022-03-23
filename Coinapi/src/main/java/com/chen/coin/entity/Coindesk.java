package com.chen.coin.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;


@Entity
@Table
public class Coindesk {
	
	@Id
	private String code;
	@Column
	private String codename;
	@Column
	private String symbol;
	@Column
	private String rate;
	@Column
	private String description;
	@Column
	private double rate_float;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCodename() {
		return codename;
	}
	public void setCodename(String codename) {
		this.codename = codename;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public double getRate_float() {
		return rate_float;
	}
	public void setRate_float(double rate_float) {
		this.rate_float = rate_float;
	}
	
}

