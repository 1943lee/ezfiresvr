package com.ezfire.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.ezfire.common.ComDefine;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by lcy on 2018/1/23.
 */
@ApiModel(value = "周边资源")
public class AroundResources {
	@ApiModelProperty(value = "资源分类")
	private ComDefine.fireWaterSourceCode resourceType;
	@ApiModelProperty(value = "资源分类描述",position = 1)
	@JSONField(ordinal = 1)
	private String resourceTypeDescription;
	@ApiModelProperty(value = "资源列表",position = 3)
	@JSONField(jsonDirect=true,ordinal = 3)
	private String content;
	@ApiModelProperty(value = "资源总数",position = 2)
	@JSONField(ordinal = 2)
	private long totalCount;

	public ComDefine.fireWaterSourceCode getResourceType() {
		return resourceType;
	}

	public void setResourceType(ComDefine.fireWaterSourceCode resourceType) {
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
