package com.chen.coin.entity;


public class CoindeskVo {

	private Time time;
	private String disclaimer;
	private String chartName;
	private Bpi bpi;
	private Coindesk coindesk;
	
	public Time getTime() {
		return time;
	}

	public Coindesk getCoindesk() {
		return coindesk;
	}

	public void setCoindesk(Coindesk coindesk) {
		this.coindesk = coindesk;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	public String getDisclaimer() {
		return disclaimer;
	}

	public void setDisclaimer(String disclaimer) {
		this.disclaimer = disclaimer;
	}

	public String getChartName() {
		return chartName;
	}

	public void setChartName(String chartName) {
		this.chartName = chartName;
	}

	public Bpi getBpi() {
		return bpi;
	}

	public void setBpi(Bpi bpi) {
		this.bpi = bpi;
	}

}
