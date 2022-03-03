package com.chen.coin.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;




@Entity
@Table
public class Coindesk {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
	
	
	@Column
	String time;
	@Column
	String updated;
	@Column
	String updatedISO;
	@Column
	String updateduk;
	//中文
	@Column
	String coinname;
	@Column
	String coin;
	@Column
	String exchangerate;
	@Column
	String discllaimer;
	@Column
	String chartName;
	@Column
	String description;
	@Column
	String symbol;
	@Column
	String rate;
	@Column
	String rate_float;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getCoinname() {
		return coinname;
	}
	public void setCoinname(String coinname) {
		this.coinname = coinname;
	}
	public String getCoin() {
		return coin;
	}
	public void setCoin(String coin) {
		this.coin = coin;
	}
	public String getExchangerate() {
		return exchangerate;
	}
	public void setExchangerate(String exchangerate) {
		this.exchangerate = exchangerate;
	}
	public String getUpdated() {
		return updated;
	}
	public void setUpdated(String updated) {
		this.updated = updated;
	}
	public String getUpdatedISO() {
		return updatedISO;
	}
	public void setUpdatedISO(String updatedISO) {
		this.updatedISO = updatedISO;
	}
	public String getUpdateduk() {
		return updateduk;
	}
	public void setUpdateduk(String updateduk) {
		this.updateduk = updateduk;
	}
	public String getDiscllaimer() {
		return discllaimer;
	}
	public void setDiscllaimer(String discllaimer) {
		this.discllaimer = discllaimer;
	}
	public String getChartName() {
		return chartName;
	}
	public void setChartName(String chartName) {
		this.chartName = chartName;
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
	public String getRate_float() {
		return rate_float;
	}
	public void setRate_float(String rate_float) {
		this.rate_float = rate_float;
	}
	
	
}

