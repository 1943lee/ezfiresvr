package com.ezfire.domain;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by lcy on 2018/1/25.
 */
public class AroundBwmb {
	@ApiModelProperty(value = "目标分类")
	private String resourceType;
	@ApiModelProperty(value = "目标分类描述",position = 1)
	@JSONField(ordinal = 1)
	private String resourceTypeDescription;
	@ApiModelProperty(value = "资源列表",position = 3)
	@JSONField(jsonDirect=true,ordinal = 3)
	private String content;
	@ApiModelProperty(value = "资源总数",position = 2)
	@JSONField(ordinal = 2)
	private long totalCount;

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

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
}
