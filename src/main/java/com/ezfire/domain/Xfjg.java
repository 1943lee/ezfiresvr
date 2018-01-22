package com.ezfire.domain;

import io.swagger.annotations.ApiModel;

/**
 * Created by lcy on 2018/1/22.
 */
@ApiModel(value = "消防机构信息")
public class Xfjg {
	private String DWMC;
	private String DWBH;
	private String DWNBBM;
	private String DWXS;
	private String DWJB;
	private DWLB DWLB;
	private static class DWLB {
		private String ID;
		private String VALUE;

		public String getID() {
			return ID;
		}

		public void setID(String ID) {
			this.ID = ID;
		}

		public String getVALUE() {
			return VALUE;
		}

		public void setVALUE(String VALUE) {
			this.VALUE = VALUE;
		}
	}
	private String DWDZ;
	private String DWMS;
	private SZDXZQH SZDXZQH;
	private static class SZDXZQH {
		private String XZQHBH;
		private String XZQHMC;
		private String XZQHNBBM;

		public String getXZQHBH() {
			return XZQHBH;
		}

		public void setXZQHBH(String XZQHBH) {
			this.XZQHBH = XZQHBH;
		}

		public String getXZQHMC() {
			return XZQHMC;
		}

		public void setXZQHMC(String XZQHMC) {
			this.XZQHMC = XZQHMC;
		}

		public String getXZQHNBBM() {
			return XZQHNBBM;
		}

		public void setXZQHNBBM(String XZQHNBBM) {
			this.XZQHNBBM = XZQHNBBM;
		}
	}
	private String JD;
	private String WD;

	public String getDWMC() {
		return DWMC;
	}

	public void setDWMC(String DWMC) {
		this.DWMC = DWMC;
	}

	public String getDWBH() {
		return DWBH;
	}

	public void setDWBH(String DWBH) {
		this.DWBH = DWBH;
	}

	public String getDWNBBM() {
		return DWNBBM;
	}

	public void setDWNBBM(String DWNBBM) {
		this.DWNBBM = DWNBBM;
	}

	public String getDWXS() {
		return DWXS;
	}

	public void setDWXS(String DWXS) {
		this.DWXS = DWXS;
	}

	public String getDWJB() {
		return DWJB;
	}

	public void setDWJB(String DWJB) {
		this.DWJB = DWJB;
	}

	public String getDWDZ() {
		return DWDZ;
	}

	public void setDWDZ(String DWDZ) {
		this.DWDZ = DWDZ;
	}

	public String getDWMS() {
		return DWMS;
	}

	public void setDWMS(String DWMS) {
		this.DWMS = DWMS;
	}

	public String getJD() {
		return JD;
	}

	public void setJD(String JD) {
		this.JD = JD;
	}

	public String getWD() {
		return WD;
	}

	public void setWD(String WD) {
		this.WD = WD;
	}

	public Xfjg.DWLB getDWLB() {
		return DWLB;
	}

	public void setDWLB(Xfjg.DWLB DWLB) {
		this.DWLB = DWLB;
	}

	public Xfjg.SZDXZQH getSZDXZQH() {
		return SZDXZQH;
	}

	public void setSZDXZQH(Xfjg.SZDXZQH SZDXZQH) {
		this.SZDXZQH = SZDXZQH;
	}
}
