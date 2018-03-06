package com.ezfire.domain;

import com.ezfire.domain.comDomains.SZDXFJG;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by lcy on 2018/2/28.
 */
@ApiModel(description = "文书信息")
public class Wsxx {
	@ApiModelProperty(value = "灾情编号")
	private String zqbh;
	@ApiModelProperty(value = "文书编号")
	private String wsbh;
	@ApiModelProperty(value = "文书类型")
	private String wslx;
	@ApiModelProperty(value = "文书状态")
	private String wszt;
	@ApiModelProperty(value = "文书内容")
	private String wsnr;
	@ApiModelProperty(value = "反馈人员")
	private String fkry;
	@ApiModelProperty(value = "反馈机构")
	private SZDXFJG fkjg;
	@ApiModelProperty(value = "更新时间")
	private String gxsj;

	public String getZqbh() {
		return zqbh;
	}

	public void setZqbh(String zqbh) {
		this.zqbh = zqbh;
	}

	public String getWsbh() {
		return wsbh;
	}

	public void setWsbh(String wsbh) {
		this.wsbh = wsbh;
	}

	public String getWslx() {
		return wslx;
	}

	public void setWslx(String wslx) {
		this.wslx = wslx;
	}

	public String getWszt() {
		return wszt;
	}

	public void setWszt(String wszt) {
		this.wszt = wszt;
	}

	public String getWsnr() {
		return wsnr;
	}

	public void setWsnr(String wsnr) {
		this.wsnr = wsnr;
	}

	public String getFkry() {
		return fkry;
	}

	public void setFkry(String fkry) {
		this.fkry = fkry;
	}

	public SZDXFJG getFkjg() {
		return fkjg;
	}

	public void setFkjg(SZDXFJG fkjg) {
		this.fkjg = fkjg;
	}

	public String getGxsj() {
		return gxsj;
	}

	public void setGxsj(String gxsj) {
		this.gxsj = gxsj;
	}
}
