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
	@ApiModelProperty(value = "职务")
	private String zw;
	@ApiModelProperty(value = "隶属单位单位编号")
	private String lsdw;
	@ApiModelProperty(value = "隶属单位单位名称")
	private String dwmc;
	@ApiModelProperty(value = "隶属单位单位内部编码")
	private String dwnbbm;
	@ApiModelProperty(value = "隶属单位单位级别，0表示部局，累加往后类推")
	private String dwjb;
	@ApiModelProperty(value = "行政区划编号")
	private String xzqhbh;
	@ApiModelProperty(value = "行政区划名称")
	private String xzqhmc;
	@ApiModelProperty(value = "行政区划内部编码")
	private String xzqhnbbm;
	@ApiModelProperty(value = "身份证号")
	private String sfzh;
	@ApiModelProperty(value = "联系电话")
	private String lxdh;
	@ApiModelProperty(value = "手机号码")
	private String sjhm;

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
		this.jyxm = jyxm.trim();
	}

	public String getLsdw() {
		return lsdw;
	}

	public void setLsdw(String lsdw) {
		this.lsdw = lsdw.trim();
	}

	public String getSfzh() {
		return sfzh;
	}

	public void setSfzh(String sfzh) {
		this.sfzh = sfzh.trim();
	}

	public String getLxdh() {
		return lxdh;
	}

	public void setLxdh(String lxdh) {
		this.lxdh = lxdh.trim();
	}

	public String getZw() {
		return zw;
	}

	public void setZw(String zw) {
		this.zw = zw.trim();
	}

	public String getDwmc() {
		return dwmc;
	}

	public void setDwmc(String dwmc) {
		this.dwmc = dwmc;
	}

	public String getDwnbbm() {
		return dwnbbm;
	}

	public void setDwnbbm(String dwnbbm) {
		this.dwnbbm = dwnbbm;
	}

	public String getDwjb() {
		return dwjb;
	}

	public void setDwjb(String dwjb) {
		this.dwjb = dwjb;
	}

	public String getXzqhbh() {
		return xzqhbh;
	}

	public void setXzqhbh(String xzqhbh) {
		this.xzqhbh = xzqhbh;
	}

	public String getXzqhmc() {
		return xzqhmc;
	}

	public void setXzqhmc(String xzqhmc) {
		this.xzqhmc = xzqhmc;
	}

	public String getXzqhnbbm() {
		return xzqhnbbm;
	}

	public void setXzqhnbbm(String xzqhnbbm) {
		this.xzqhnbbm = xzqhnbbm;
	}

	public String getSjhm() {
		return sjhm;
	}

	public void setSjhm(String sjhm) {
		this.sjhm = sjhm.trim();
	}
}
