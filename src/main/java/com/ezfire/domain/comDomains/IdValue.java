package com.ezfire.domain.comDomains;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by lcy on 2018/3/6.
 */
@ApiModel(description = "id&value")
public class IdValue {
	@ApiModelProperty(value = "id")
	private String id;
	@ApiModelProperty(value = "value")
	private String value;

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
}
