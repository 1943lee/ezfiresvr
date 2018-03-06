package com.ezfire.domain.comDomains;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by lcy on 2018/3/5.
 */
@ApiModel(description = "所在地行政区划")
public class SZDXZQH {
	@ApiModelProperty(value = "行政区划编号")
	private String xzqhbh;
	@ApiModelProperty(value = "行政区划名称")
	private String xzqhmc;
	@ApiModelProperty(value = "行政区划内部编码")
	private String xzqhnbbm;

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
}
