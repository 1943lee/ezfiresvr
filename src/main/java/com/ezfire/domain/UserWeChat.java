package com.ezfire.domain;

import com.ezfire.domain.comDomains.IdValue;
import com.ezfire.domain.comDomains.SSXFDZ;
import com.ezfire.domain.comDomains.SZDXFJG;
import com.ezfire.domain.comDomains.SZDXZQH;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by lcy on 2018/3/5.
 */
@ApiModel(description = "微信用户信息")
public class UserWeChat {
	@ApiModelProperty(value = "人员编号")
	private String rybh;
	@ApiModelProperty(value = "身份证号")
	private String sfzh;
	@ApiModelProperty(value = "姓名")
	private String xm;
	@ApiModelProperty(value = "性别")
	private IdValue xb;
	@ApiModelProperty(value = "民族")
	private IdValue mz;
	@ApiModelProperty(value = "籍贯")
	private String jg;
	@ApiModelProperty(value = "岗位")
	private IdValue gw;
	@ApiModelProperty(value = "照片")
	private String zp;
	@ApiModelProperty(value = "出生日期")
	private String csrq;
	@ApiModelProperty(value = "联系电话")
	private String lxdh;
	@ApiModelProperty(value = "人员类别，1.现役部队干部，2.现役部队士兵，3.合同制消防员")
	private IdValue rylb;
	@ApiModelProperty(value = "是否执勤，0.否，1.是")
	private String sfzq;
	@ApiModelProperty(value = "所属消防队站")
	private SSXFDZ ssxfdz;
	@ApiModelProperty(value = "所在地消防机构")
	private SZDXFJG szdxfjg;
	@ApiModelProperty(value = "所在地行政区划")
	private SZDXZQH szdxzqh;

	public String getRybh() {
		return rybh;
	}

	public void setRybh(String rybh) {
		this.rybh = rybh;
	}

	public String getSfzh() {
		return sfzh;
	}

	public void setSfzh(String sfzh) {
		this.sfzh = sfzh;
	}

	public String getXm() {
		return xm;
	}

	public void setXm(String xm) {
		this.xm = xm;
	}

	public IdValue getXb() {
		return xb;
	}

	public void setXb(IdValue xb) {
		this.xb = xb;
	}

	public IdValue getMz() {
		return mz;
	}

	public void setMz(IdValue mz) {
		this.mz = mz;
	}

	public String getJg() {
		return jg;
	}

	public void setJg(String jg) {
		this.jg = jg;
	}

	public IdValue getGw() {
		return gw;
	}

	public void setGw(IdValue gw) {
		this.gw = gw;
	}

	public String getZp() {
		return zp;
	}

	public void setZp(String zp) {
		this.zp = zp;
	}

	public String getCsrq() {
		return csrq;
	}

	public void setCsrq(String csrq) {
		this.csrq = csrq;
	}

	public String getLxdh() {
		return lxdh;
	}

	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
	}

	public IdValue getRylb() {
		return rylb;
	}

	public void setRylb(IdValue rylb) {
		this.rylb = rylb;
	}

	public String getSfzq() {
		return sfzq;
	}

	public void setSfzq(String sfzq) {
		this.sfzq = sfzq;
	}

	public SSXFDZ getSsxfdz() {
		return ssxfdz;
	}

	public void setSsxfdz(SSXFDZ ssxfdz) {
		this.ssxfdz = ssxfdz;
	}

	public SZDXFJG getSzdxfjg() {
		return szdxfjg;
	}

	public void setSzdxfjg(SZDXFJG szdxfjg) {
		this.szdxfjg = szdxfjg;
	}

	public SZDXZQH getSzdxzqh() {
		return szdxzqh;
	}

	public void setSzdxzqh(SZDXZQH szdxzqh) {
		this.szdxzqh = szdxzqh;
	}
}
