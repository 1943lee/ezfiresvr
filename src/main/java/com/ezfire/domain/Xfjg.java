package com.ezfire.domain;

import com.ezfire.domain.comDomains.IdValue;
import com.ezfire.domain.comDomains.SZDXZQH;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by lcy on 2018/1/22.
 */
@ApiModel(description = "消防机构")
public class Xfjg {
	@ApiModelProperty(value = "单位名称")
	private String dwmc;
	@ApiModelProperty(value = "单位编号")
	private String dwbh;
	@ApiModelProperty(value = "单位内部编码")
	private String dwnbbm;
	@ApiModelProperty(value = "单位缩写")
	private String dwsx;
	@ApiModelProperty(value = "单位级别")
	private String dwjb;
	@ApiModelProperty(value = "单位类别")
	private IdValue dwlb;
	@ApiModelProperty(value = "单位地址")
	private String dwdz;
	@ApiModelProperty(value = "单位描述")
	private String dwms;
	@ApiModelProperty(value = "所在地行政区划")
	private SZDXZQH szdxzqh;
	@ApiModelProperty(value = "经度")
	private double jd;
	@ApiModelProperty(value = "纬度")
	private double wd;

	public String getDwmc() {
		return dwmc;
	}

	public void setDwmc(String dwmc) {
		this.dwmc = dwmc;
	}

	public String getDwbh() {
		return dwbh;
	}

	public void setDwbh(String dwbh) {
		this.dwbh = dwbh;
	}

	public String getDwnbbm() {
		return dwnbbm;
	}

	public void setDwnbbm(String dwnbbm) {
		this.dwnbbm = dwnbbm;
	}

	public String getDwsx() {
		return dwsx;
	}

	public void setDwsx(String dwsx) {
		this.dwsx = dwsx;
	}

	public String getDwjb() {
		return dwjb;
	}

	public void setDwjb(String dwjb) {
		this.dwjb = dwjb;
	}

	public IdValue getDwlb() {
		return dwlb;
	}

	public void setDwlb(IdValue dwlb) {
		this.dwlb = dwlb;
	}

	public String getDwdz() {
		return dwdz;
	}

	public void setDwdz(String dwdz) {
		this.dwdz = dwdz;
	}

	public String getDwms() {
		return dwms;
	}

	public void setDwms(String dwms) {
		this.dwms = dwms;
	}

	public SZDXZQH getSzdxzqh() {
		return szdxzqh;
	}

	public void setSzdxzqh(SZDXZQH szdxzqh) {
		this.szdxzqh = szdxzqh;
	}

	public double getJd() {
		return jd;
	}

	public void setJd(double jd) {
		this.jd = jd;
	}

	public double getWd() {
		return wd;
	}

	public void setWd(double wd) {
		this.wd = wd;
	}
}
