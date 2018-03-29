package com.ezfire.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by lcy on 2018/3/15.
 */
@ApiModel(value = "灾情录音")
public class AlarmRecording {
	@ApiModelProperty(value = "录音编号")
	private String lybh;
	@ApiModelProperty(value = "灾情编号")
	private String zqbh;
	@ApiModelProperty(value = "主叫号码")
	private String zjhm;
	@ApiModelProperty(value = "被叫号码")
	private String bjhm;
	@ApiModelProperty(value = "录音文件")
	private LYWJ lywj;
	@ApiModelProperty(value = "录音开始时间")
	private String lykssj;
	@ApiModelProperty(value = "录音结束时间")
	private String lyjssj;
	@ApiModelProperty(value = "报警开始时间")
	private String bjkssj;
	@ApiModelProperty(value = "报警结束时间")
	private String bjjssj;
	@ApiModelProperty(value = "是否下发，0表示否，1表示是")
	private String sfxf;

	@ApiModel(value = "录音文件")
	static class LYWJ {
		@ApiModelProperty(value = "文件名称")
		private String wjmc;
		@ApiModelProperty(value = "文件后缀，大写")
		private String wjhz;
		@ApiModelProperty(value = "文件大小，字节")
		private String wjdx;
		@ApiModelProperty(value = "文件时长，秒")
		private String wjsc;
		@ApiModelProperty(value = "文件地址，url")
		private String wjdz;

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

		public String getWjdx() {
			return wjdx;
		}

		public void setWjdx(String wjdx) {
			this.wjdx = wjdx;
		}

		public String getWjsc() {
			return wjsc;
		}

		public void setWjsc(String wjsc) {
			this.wjsc = wjsc;
		}

		public String getWjdz() {
			return wjdz;
		}

		public void setWjdz(String wjdz) {
			this.wjdz = wjdz;
		}
	}

	public String getLybh() {
		return lybh;
	}

	public void setLybh(String lybh) {
		this.lybh = lybh;
	}

	public String getZqbh() {
		return zqbh;
	}

	public void setZqbh(String zqbh) {
		this.zqbh = zqbh;
	}

	public String getZjhm() {
		return zjhm;
	}

	public void setZjhm(String zjhm) {
		this.zjhm = zjhm;
	}

	public String getBjhm() {
		return bjhm;
	}

	public void setBjhm(String bjhm) {
		this.bjhm = bjhm;
	}

	public LYWJ getLywj() {
		return lywj;
	}

	public void setLywj(LYWJ lywj) {
		this.lywj = lywj;
	}

	public String getLykssj() {
		return lykssj;
	}

	public void setLykssj(String lykssj) {
		this.lykssj = lykssj;
	}

	public String getLyjssj() {
		return lyjssj;
	}

	public void setLyjssj(String lyjssj) {
		this.lyjssj = lyjssj;
	}

	public String getBjkssj() {
		return bjkssj;
	}

	public void setBjkssj(String bjkssj) {
		this.bjkssj = bjkssj;
	}

	public String getBjjssj() {
		return bjjssj;
	}

	public void setBjjssj(String bjjssj) {
		this.bjjssj = bjjssj;
	}

	public String getSfxf() {
		return sfxf;
	}

	public void setSfxf(String sfxf) {
		this.sfxf = sfxf;
	}
}
