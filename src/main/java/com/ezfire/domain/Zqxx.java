package com.ezfire.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by lcy on 2018/1/21.
 */
@ApiModel(value = "灾情基本信息",description = "字段全部大写")
public class Zqxx {
	@ApiModelProperty(value = "灾情编号")
	private String zqxx;
	@ApiModelProperty(value = "关联编号")
	private String glbh;
	@ApiModelProperty(value = "增援编号")
	private String zybh;
	@ApiModelProperty(value = "灾情分类，‘0’表示普通灾情，‘1’表示突出灾情", allowableValues = "0,1")
	private String zqfl;
	@ApiModelProperty(value = "灾情名称")
	private String zqmc;
	@ApiModelProperty(value = "经度")
	private double jd;
	@ApiModelProperty(value = "纬度")
	private double wd;
	@ApiModelProperty(value = "灾情地点")
	private double zqdd;

	public String getZqxx() {
		return zqxx;
	}

	public void setZqxx(String zqxx) {
		this.zqxx = zqxx;
	}

	public String getGlbh() {
		return glbh;
	}

	public void setGlbh(String glbh) {
		this.glbh = glbh;
	}

	public String getZybh() {
		return zybh;
	}

	public void setZybh(String zybh) {
		this.zybh = zybh;
	}

	public String getZqfl() {
		return zqfl;
	}

	public void setZqfl(String zqfl) {
		this.zqfl = zqfl;
	}

	public String getZqmc() {
		return zqmc;
	}

	public void setZqmc(String zqmc) {
		this.zqmc = zqmc;
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

	public double getZqdd() {
		return zqdd;
	}

	public void setZqdd(double zqdd) {
		this.zqdd = zqdd;
	}
}
