package com.ezfire.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by lcy on 2018/1/21.
 */
@ApiModel(value = "灾情基本信息")
public class Zqxx {

	@ApiModelProperty(required = true, notes = "灾情编号", position = 0)
	private String ZQBH;
	@ApiModelProperty(notes = "关联编号")
	private String GLBH;
	@ApiModelProperty(notes = "增援编号")
	private String ZYBH;
	@ApiModelProperty(notes = "灾情分类，‘0’表示普通灾情，‘1’表示突出灾情", allowableValues = "0,1")
	private String ZQFL;
	@ApiModelProperty(notes = "灾情名称")
	private String ZQMC;

	public String getZQBH() {
		return ZQBH;
	}

	public void setZQBH(String ZQBH) {
		this.ZQBH = ZQBH;
	}

	public String getGLBH() {
		return GLBH;
	}

	public void setGLBH(String GLBH) {
		this.GLBH = GLBH;
	}

	public String getZYBH() {
		return ZYBH;
	}

	public void setZYBH(String ZYBH) {
		this.ZYBH = ZYBH;
	}

	public String getZQFL() {
		return ZQFL;
	}

	public void setZQFL(String ZQFL) {
		this.ZQFL = ZQFL;
	}

	public String getZQMC() {
		return ZQMC;
	}

	public void setZQMC(String ZQMC) {
		this.ZQMC = ZQMC;
	}
}
