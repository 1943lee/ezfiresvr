package com.ezfire.domain;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by lcy on 2018/1/23.
 */
@ApiModel(description = "周边资源")
public class AroundResource {
	@ApiModelProperty(value = "资源分类id")
	private String resourceType;
	@ApiModelProperty(value = "资源分类描述",position = 1)
	@JSONField(ordinal = 1)
	private String resourceTypeDescription;
	@ApiModelProperty(value = "资源内容",position = 3)
	@JSONField(jsonDirect=true,ordinal = 3)
	private String content;
	@ApiModelProperty(value = "距离，单位米",position = 2)
	@JSONField(ordinal = 2)
	private double distance;

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public String getResourceTypeDescription() {
		return resourceTypeDescription;
	}

	public void setResourceTypeDescription(String resourceTypeDescription) {
		this.resourceTypeDescription = resourceTypeDescription;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}
}
