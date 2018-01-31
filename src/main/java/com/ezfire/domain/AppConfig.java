package com.ezfire.domain;

/**
 * Created by lcy on 2018/1/31.
 */
public class AppConfig {
	private String esHosts;
	private String esUserName;
	private String esPassword;

	public String getEsHosts() {
		return esHosts;
	}

	public void setEsHosts(String esHosts) {
		this.esHosts = esHosts;
	}

	public String getEsUserName() {
		return esUserName;
	}

	public void setEsUserName(String esUserName) {
		this.esUserName = esUserName;
	}

	public String getEsPassword() {
		return esPassword;
	}

	public void setEsPassword(String esPassword) {
		this.esPassword = esPassword;
	}
}
