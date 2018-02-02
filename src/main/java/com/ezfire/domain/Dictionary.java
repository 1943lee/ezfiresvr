package com.ezfire.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "字典项")
public class Dictionary {
	@ApiModelProperty(value = "字典编号")
	private String zdbh;
	@ApiModelProperty(value = "字典名称")
	private String zdmc;
	@ApiModelProperty(value = "字典类型")
	private String zdlx;
	@ApiModelProperty(value = "字典")
	private String zdjb;
	@ApiModelProperty(value = "字典简称")
	private String zdjc;
	@ApiModelProperty(value = "字典描述")
	private String zdms;
	@ApiModelProperty(value = "字典拼音")
	private String zdpy;
	@ApiModelProperty(value = "字典缩写")
	private String zdsx;
	@ApiModelProperty(value = "字典内部编码")
	private String nbbm;
	@ApiModelProperty(value = "字典父级编号")
	private String fjbh;
	@ApiModelProperty(value = "是否显示")
	private String sfxs;
	@ApiModelProperty(value = "是否默认")
	private String sfmr;

	public String getZdbh() {
		return zdbh;
	}

	public void setZdbh(String zdbh) {
		this.zdbh = zdbh;
	}

	public String getZdmc() {
		return zdmc;
	}

	public void setZdmc(String zdmc) {
		this.zdmc = zdmc;
	}

	public String getZdlx() {
		return zdlx;
	}

	public void setZdlx(String zdlx) {
		this.zdlx = zdlx;
	}

	public String getZdjb() {
		return zdjb;
	}

	public void setZdjb(String zdjb) {
		this.zdjb = zdjb;
	}

	public String getZdjc() {
		return zdjc;
	}

	public void setZdjc(String zdjc) {
		this.zdjc = zdjc;
	}

	public String getZdms() {
		return zdms;
	}

	public void setZdms(String zdms) {
		this.zdms = zdms;
	}

	public String getZdpy() {
		return zdpy;
	}

	public void setZdpy(String zdpy) {
		this.zdpy = zdpy;
	}

	public String getZdsx() {
		return zdsx;
	}

	public void setZdsx(String zdsx) {
		this.zdsx = zdsx;
	}

	public String getNbbm() {
		return nbbm;
	}

	public void setNbbm(String nbbm) {
		this.nbbm = nbbm;
	}

	public String getFjbh() {
		return fjbh;
	}

	public void setFjbh(String fjbh) {
		this.fjbh = fjbh;
	}

	public String getSfxs() {
		return sfxs;
	}

	public void setSfxs(String sfxs) {
		this.sfxs = sfxs;
	}

	public String getSfmr() {
		return sfmr;
	}

	public void setSfmr(String sfmr) {
		this.sfmr = sfmr;
	}
}