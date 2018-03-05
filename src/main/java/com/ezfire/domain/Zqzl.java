package com.ezfire.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by lcy on 2018/3/5.
 */
@ApiModel(description = "灾情指令")
public class Zqzl {
	@ApiModelProperty(value = "指令编号")
	private String zlbh;
	@ApiModelProperty(value = "灾情编号")
	private String zqbh;
	@ApiModelProperty(value = "指令类型")
	private String zllx;
	@ApiModelProperty(value = "信息主题")
	private String xxzt;
	@ApiModelProperty(value = "信息类型")
	private String xxlx;
	@ApiModelProperty(value = "信息内容")
	private String xxnr;
	@ApiModelProperty(value = "文件资料")
	private String wjzl;
	@ApiModelProperty(value = "发送人员")
	private String fsry;
	@ApiModelProperty(value = "发送机构")
	private SZDXFJG fsjg;
	@ApiModelProperty(value = "发送时间")
	private String fssj;
	@ApiModelProperty(value = "接收人员")
	private String jsry;
	@ApiModelProperty(value = "接收机构")
	private SZDXFJG jsjg;

	@ApiModel(description = "所在地消防机构")
	private static class SZDXFJG {
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

	public String getZlbh() {
		return zlbh;
	}

	public void setZlbh(String zlbh) {
		this.zlbh = zlbh;
	}

	public String getZqbh() {
		return zqbh;
	}

	public void setZqbh(String zqbh) {
		this.zqbh = zqbh;
	}

	public String getZllx() {
		return zllx;
	}

	public void setZllx(String zllx) {
		this.zllx = zllx;
	}

	public String getXxzt() {
		return xxzt;
	}

	public void setXxzt(String xxzt) {
		this.xxzt = xxzt;
	}

	public String getXxlx() {
		return xxlx;
	}

	public void setXxlx(String xxlx) {
		this.xxlx = xxlx;
	}

	public String getXxnr() {
		return xxnr;
	}

	public void setXxnr(String xxnr) {
		this.xxnr = xxnr;
	}

	public String getWjzl() {
		return wjzl;
	}

	public void setWjzl(String wjzl) {
		this.wjzl = wjzl;
	}

	public String getFsry() {
		return fsry;
	}

	public void setFsry(String fsry) {
		this.fsry = fsry;
	}

	public SZDXFJG getFsjg() {
		return fsjg;
	}

	public void setFsjg(SZDXFJG fsjg) {
		this.fsjg = fsjg;
	}

	public String getFssj() {
		return fssj;
	}

	public void setFssj(String fssj) {
		this.fssj = fssj;
	}

	public String getJsry() {
		return jsry;
	}

	public void setJsry(String jsry) {
		this.jsry = jsry;
	}

	public SZDXFJG getJsjg() {
		return jsjg;
	}

	public void setJsjg(SZDXFJG jsjg) {
		this.jsjg = jsjg;
	}
}
