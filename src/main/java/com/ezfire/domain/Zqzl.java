package com.ezfire.domain;

import com.ezfire.domain.comDomains.SZDXFJG;
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
	@ApiModelProperty(value = "指令类型,1.本辖区指挥指令，2.跨辖区调度指令")
	private String zllx;
	@ApiModelProperty(value = "信息类型,1.文本,2.语音,3.图片,4.视频,5.文档,9.其他")
	private String xxlx;
	@ApiModelProperty(value = "信息主题")
	private String xxzt;
	@ApiModelProperty(value = "信息内容")
	private String xxnr;
	@ApiModelProperty(value = "文件资料")
	private WJZL wjzl;
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

	@ApiModel(description = "文件资料")
	private static class WJZL {
		@ApiModelProperty(value = "文件名称")
		private String wjmc;
		@ApiModelProperty(value = "文件后缀(大写)")
		private String wjhz;
		@ApiModelProperty(value = "文件地址(url)")
		private String wjdz;
		@ApiModelProperty(value = "缩略图片(url)")
		private String sltp;

		public String getWjmc() {
			return wjmc;
		}

		public void setWjmc(String wjmc) {
			this.wjmc = wjmc;
		}

		public String getWjhz() {
			return wjhz;
		}

		public void setWjhz(String wjhz) {
			this.wjhz = wjhz;
		}

		public String getWjdz() {
			return wjdz;
		}

		public void setWjdz(String wjdz) {
			this.wjdz = wjdz;
		}

		public String getSltp() {
			return sltp;
		}

		public void setSltp(String sltp) {
			this.sltp = sltp;
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

	public WJZL getWjzl() {
		return wjzl;
	}

	public void setWjzl(WJZL wjzl) {
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
