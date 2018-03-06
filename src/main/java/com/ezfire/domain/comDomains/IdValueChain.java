package com.ezfire.domain.comDomains;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by lcy on 2018/3/6.
 */
@ApiModel(description = "id&value&Chain")
public class IdValueChain {
	@ApiModelProperty(value = "id")
	private String id;
	@ApiModelProperty(value = "value")
	private String value;
	@ApiModelProperty(value = "chain")
	private String chain;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getChain() {
		return chain;
	}

	public void setChain(String chain) {
		this.chain = chain;
	}
}
