package com.ezfire.domain;

import com.ezfire.domain.comDomains.SZDXFJG;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by lcy on 2018/1/24.
 */
@ApiModel(description = "调派信息")
public class Dpxx {
	@ApiModelProperty(value = "灾情编号")
	private String zqbh;
	@ApiModelProperty(value = "调派编号")
	private String dpbh;
	@ApiModelProperty(value = "发送机构")
	private SZDXFJG fsjg;
	@ApiModelProperty(value = "发送时间")
	private String fssj;
	@ApiModelProperty(value = "接收机构")
	private SZDXFJG jsjg;
	@ApiModelProperty(value = "反馈时间")
	private String fksj;
	@ApiModelProperty(value = "调派专家数")
	private int dpzjs;
	@ApiModelProperty(value = "调派专家")
	private DPZJ dpzj;
	@ApiModelProperty(value = "调派专家数")
	private int dprys;
	@ApiModelProperty(value = "调派人员")
	private DPRY dpry;
	@ApiModelProperty(value = "调派车辆数")
	private int dpcls;
	@ApiModelProperty(value = "调派车辆")
	private DPCL dpcl;
	@ApiModelProperty(value = "调派器材")
	private int dpqcs;
	@ApiModelProperty(value = "调派器材数")
	private DPQC dpqc;

	@ApiModel(description = "调派专家")
	private static class DPZJ {
		@ApiModelProperty(value = "专家姓名")
		private String zjxm;
		@ApiModelProperty(value = "身份证号")
		private String sfzh;
		@ApiModelProperty(value = "所属单位")
		private String ssdw;
		@ApiModelProperty(value = "专家领域")
		private String zjly;
		@ApiModelProperty(value = "开始时间")
		private String kssj;
		@ApiModelProperty(value = "结束时间")
		private String jssj;

		public String getZjxm() {
			return zjxm;
		}

		public void setZjxm(String zjxm) {
			this.zjxm = zjxm;
		}

		public String getSfzh() {
			return sfzh;
		}

		public void setSfzh(String sfzh) {
			this.sfzh = sfzh;
		}

		public String getSsdw() {
			return ssdw;
		}

		public void setSsdw(String ssdw) {
			this.ssdw = ssdw;
		}

		public String getZjly() {
			return zjly;
		}

		public void setZjly(String zjly) {
			this.zjly = zjly;
		}

		public String getKssj() {
			return kssj;
		}

		public void setKssj(String kssj) {
			this.kssj = kssj;
		}

		public String getJssj() {
			return jssj;
		}

		public void setJssj(String jssj) {
			this.jssj = jssj;
		}
	}

	@ApiModel(description = "调派人员")
	private static class DPRY {
		@ApiModelProperty(value = "人员编号")
		private String rybh;
		@ApiModelProperty(value = "身份证号")
		private String sfzh;
		@ApiModelProperty(value = "人员姓名")
		private String ryxm;
		@ApiModelProperty(value = "联系电话")
		private String lxdh;
		@ApiModelProperty(value = "所属岗位")
		private String ssgw;
		@ApiModelProperty(value = "所属单位")
		private String ssdw;
		@ApiModelProperty(value = "开始时间")
		private String kssj;
		@ApiModelProperty(value = "结束时间")
		private String jssj;

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

		public String getRyxm() {
			return ryxm;
		}

		public void setRyxm(String ryxm) {
			this.ryxm = ryxm;
		}

		public String getLxdh() {
			return lxdh;
		}

		public void setLxdh(String lxdh) {
			this.lxdh = lxdh;
		}

		public String getSsgw() {
			return ssgw;
		}

		public void setSsgw(String ssgw) {
			this.ssgw = ssgw;
		}

		public String getSsdw() {
			return ssdw;
		}

		public void setSsdw(String ssdw) {
			this.ssdw = ssdw;
		}

		public String getKssj() {
			return kssj;
		}

		public void setKssj(String kssj) {
			this.kssj = kssj;
		}

		public String getJssj() {
			return jssj;
		}

		public void setJssj(String jssj) {
			this.jssj = jssj;
		}
	}

	@ApiModel(description = "调派车辆")
	private static class DPCL {
		@ApiModelProperty(value = "车辆编号")
		private String clbh;
		@ApiModelProperty(value = "车辆名称")
		private String clmc;
		@ApiModelProperty(value = "车牌号码")
		private String cphm;
		@ApiModelProperty(value = "车辆类型")
		private String cllx;
		@ApiModelProperty(value = "所属单位")
		private String ssdw;
		@ApiModelProperty(value = "开始时间")
		private String kssj;
		@ApiModelProperty(value = "结束时间")
		private String jssj;

		public String getClbh() {
			return clbh;
		}

		public void setClbh(String clbh) {
			this.clbh = clbh;
		}

		public String getClmc() {
			return clmc;
		}

		public void setClmc(String clmc) {
			this.clmc = clmc;
		}

		public String getCphm() {
			return cphm;
		}

		public void setCphm(String cphm) {
			this.cphm = cphm;
		}

		public String getCllx() {
			return cllx;
		}

		public void setCllx(String cllx) {
			this.cllx = cllx;
		}

		public String getSsdw() {
			return ssdw;
		}

		public void setSsdw(String ssdw) {
			this.ssdw = ssdw;
		}

		public String getKssj() {
			return kssj;
		}

		public void setKssj(String kssj) {
			this.kssj = kssj;
		}

		public String getJssj() {
			return jssj;
		}

		public void setJssj(String jssj) {
			this.jssj = jssj;
		}
	}

	@ApiModel(description = "调派器材")
	private static class DPQC {
		@ApiModelProperty(value = "器材编号")
		private String qcbh;
		@ApiModelProperty(value = "器材名称")
		private String qcmc;
		@ApiModelProperty(value = "器材类型")
		private String qclx;
		@ApiModelProperty(value = "器材数量")
		private String qcsl;
		@ApiModelProperty(value = "开始时间")
		private String kssj;
		@ApiModelProperty(value = "结束时间")
		private String jssj;

		public String getQcbh() {
			return qcbh;
		}

		public void setQcbh(String qcbh) {
			this.qcbh = qcbh;
		}

		public String getQcmc() {
			return qcmc;
		}

		public void setQcmc(String qcmc) {
			this.qcmc = qcmc;
		}

		public String getQclx() {
			return qclx;
		}

		public void setQclx(String qclx) {
			this.qclx = qclx;
		}

		public String getQcsl() {
			return qcsl;
		}

		public void setQcsl(String qcsl) {
			this.qcsl = qcsl;
		}

		public String getKssj() {
			return kssj;
		}

		public void setKssj(String kssj) {
			this.kssj = kssj;
		}

		public String getJssj() {
			return jssj;
		}

		public void setJssj(String jssj) {
			this.jssj = jssj;
		}
	}

	public String getZqbh() {
		return zqbh;
	}

	public void setZqbh(String zqbh) {
		this.zqbh = zqbh;
	}

	public String getDpbh() {
		return dpbh;
	}

	public void setDpbh(String dpbh) {
		this.dpbh = dpbh;
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

	public SZDXFJG getJsjg() {
		return jsjg;
	}

	public void setJsjg(SZDXFJG jsjg) {
		this.jsjg = jsjg;
	}

	public String getFksj() {
		return fksj;
	}

	public void setFksj(String fksj) {
		this.fksj = fksj;
	}

	public int getDpzjs() {
		return dpzjs;
	}

	public void setDpzjs(int dpzjs) {
		this.dpzjs = dpzjs;
	}

	public DPZJ getDpzj() {
		return dpzj;
	}

	public void setDpzj(DPZJ dpzj) {
		this.dpzj = dpzj;
	}

	public int getDprys() {
		return dprys;
	}

	public void setDprys(int dprys) {
		this.dprys = dprys;
	}

	public DPRY getDpry() {
		return dpry;
	}

	public void setDpry(DPRY dpry) {
		this.dpry = dpry;
	}

	public int getDpcls() {
		return dpcls;
	}

	public void setDpcls(int dpcls) {
		this.dpcls = dpcls;
	}

	public DPCL getDpcl() {
		return dpcl;
	}

	public void setDpcl(DPCL dpcl) {
		this.dpcl = dpcl;
	}

	public int getDpqcs() {
		return dpqcs;
	}

	public void setDpqcs(int dpqcs) {
		this.dpqcs = dpqcs;
	}

	public DPQC getDpqc() {
		return dpqc;
	}

	public void setDpqc(DPQC dpqc) {
		this.dpqc = dpqc;
	}
}
