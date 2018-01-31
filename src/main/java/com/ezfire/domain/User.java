package com.ezfire.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by lcy on 2018/1/31.
 */
@ApiModel(value = "用户信息")
public class User {
	@ApiModelProperty(value = "登录名")
	private String dlm;
	@ApiModelProperty(value = "警号")
	private String jh;
	@ApiModelProperty(value = "警员姓名")
	private String jyxm;
	@ApiModelProperty(value = "隶属单位")
	private String lsdw;
	@ApiModelProperty(value = "身份证号")
	private String sfzh;
	@ApiModelProperty(value = "联系电话")
	private String lxdh;

	public String getDlm() {
		return dlm;
	}

	public void setDlm(String dlm) {
		this.dlm = dlm;
	}

	public String getJh() {
		return jh;
	}

	public void setJh(String jh) {
		this.jh = jh;
	}

	public String getJyxm() {
		return jyxm;
	}

	public void setJyxm(String jyxm) {
		this.jyxm = jyxm;
	}

	public String getLsdw() {
		return lsdw;
	}

	public void setLsdw(String lsdw) {
		this.lsdw = lsdw;
	}

	public String getSfzh() {
		return sfzh;
	}

	public void setSfzh(String sfzh) {
		this.sfzh = sfzh;
	}

	public String getLxdh() {
		return lxdh;
	}

	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
	}
}
