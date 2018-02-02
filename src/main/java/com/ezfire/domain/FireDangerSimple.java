package com.ezfire.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by lcy on 2018/2/2.
 */
@ApiModel(description = "危化品简单信息")
public class FireDangerSimple {
	@ApiModelProperty(value = "编号(主键)")
	private String bh;
	@ApiModelProperty(value = "中文名")
	private String zwm;
	@ApiModelProperty(value = "英文名")
	private String ywm;
	@ApiModelProperty(value = "类别")
	private String lb;
	@ApiModelProperty(value = "项目")
	private String xm;
	@ApiModelProperty(value = "分子式")
	private String fzs;

	public String getBh() {
		return bh;
	}

	public void setBh(String bh) {
		this.bh = bh;
	}

	public String getZwm() {
		return zwm;
	}

	public void setZwm(String zwm) {
		this.zwm = zwm;
	}

	public String getYwm() {
		return ywm;
	}

	public void setYwm(String ywm) {
		this.ywm = ywm;
	}

	public String getLb() {
		return lb;
	}

	public void setLb(String lb) {
		this.lb = lb;
	}

	public String getXm() {
		return xm;
	}

	public void setXm(String xm) {
		this.xm = xm;
	}

	public String getFzs() {
		return fzs;
	}

	public void setFzs(String fzs) {
		this.fzs = fzs;
	}
}
