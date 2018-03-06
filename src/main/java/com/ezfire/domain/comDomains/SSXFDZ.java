package com.ezfire.domain.comDomains;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by lcy on 2018/3/6.
 */
@ApiModel(description = "所属消防队站")
public class SSXFDZ {
	@ApiModelProperty(value = "消防队站形式")
	private String xfdzxs;
	@ApiModelProperty(value = "消防队站编号")
	private String xfdzbh;
	@ApiModelProperty(value = "消防队站名称")
	private String xfdzmc;

	public String getXfdzxs() {
		return xfdzxs;
	}

	public void setXfdzxs(String xfdzxs) {
		this.xfdzxs = xfdzxs;
	}

	public String getXfdzbh() {
		return xfdzbh;
	}

	public void setXfdzbh(String xfdzbh) {
		this.xfdzbh = xfdzbh;
	}

	public String getXfdzmc() {
		return xfdzmc;
	}

	public void setXfdzmc(String xfdzmc) {
		this.xfdzmc = xfdzmc;
	}
}
