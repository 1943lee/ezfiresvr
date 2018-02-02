package com.ezfire.domain.RestfulParams;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by lcy on 2018/2/1.
 */
@ApiModel(description = "灾情条件参数类")
public class AlarmCondition {
	@ApiModelProperty(value = "灾情编号，支持对象型，可为数组或者字符串")
	private Object zqbh;

	@ApiModelProperty(value = "需要返回的字段，与es一致，格式为字符串数组")
	private String[] includes;

	public Object getZqbh() {
		return zqbh;
	}

	public void setZqbh(Object zqbh) {
		this.zqbh = zqbh;
	}

	public String[] getIncludes() {
		return includes;
	}

	public void setIncludes(String[] includes) {
		this.includes = includes;
	}
}
