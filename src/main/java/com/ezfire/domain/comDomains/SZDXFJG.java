package com.ezfire.domain.comDomains;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by lcy on 2018/3/5.
 */
@ApiModel(description = "所在地消防机构")
public class SZDXFJG {
	@ApiModelProperty(value = "消防机构编号")
	private String xfjgbh;
	@ApiModelProperty(value = "消防机构名称")
	private String xfjgmc;
	@ApiModelProperty(value = "消防机构内部编码")
	private String xfjgnbbm;

	public String getXfjgbh() {
		return xfjgbh;
	}

	public void setXfjgbh(String xfjgbh) {
		this.xfjgbh = xfjgbh;
	}

	public String getXfjgmc() {
		return xfjgmc;
	}

	public void setXfjgmc(String xfjgmc) {
		this.xfjgmc = xfjgmc;
	}

	public String getXfjgnbbm() {
		return xfjgnbbm;
	}

	public void setXfjgnbbm(String xfjgnbbm) {
		this.xfjgnbbm = xfjgnbbm;
	}
}
