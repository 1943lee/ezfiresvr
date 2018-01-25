package com.ezfire.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by lcy on 2018/1/21.
 */
@ApiModel(value = "灾情基本信息",description = "字段全部大写")
public class Zqxx {
	@ApiModelProperty(value = "灾情编号")
	private String zqbh;
	@ApiModelProperty(value = "关联编号")
	private String glbh;
	@ApiModelProperty(value = "增援编号")
	private String zybh;
	@ApiModelProperty(value = "灾情分类，‘0’表示普通灾情，‘1’表示突出灾情", allowableValues = "0,1")
	private String zqfl;
	@ApiModelProperty(value = "灾情名称")
	private String zqmc;
	@ApiModelProperty(value = "灾情全称")
	private String zqqc;
	@ApiModelProperty(value = "经度")
	private double jd;
	@ApiModelProperty(value = "纬度")
	private double wd;
	@ApiModelProperty(value = "灾情地点")
	private double zqdd;
	@ApiModelProperty(value = "立案时间")
	private String lasj;
	@ApiModelProperty(value = "报警时间")
	private String bjsj;
	@ApiModelProperty(value = "灾情类型")
	private ZQLX zqlx;
	@ApiModelProperty(value = "灾情等级")
	private ZQDJ zqdj;
	@ApiModelProperty(value = "灾情状态")
	private ZQZT zqzt;
	@ApiModelProperty(value = "灾情对象")
	private ZQDX zqdx;
	@ApiModelProperty(value = "灾情位置")
	private ZQWZ zqwz;
	@ApiModelProperty(value = "灾情概述")
	private String zqgs;
	@ApiModelProperty(value = "灾情标识")
	private String zqbs;
	@ApiModelProperty(value = "燃烧楼层")
	private String rslc;
	@ApiModelProperty(value = "结案时间")
	private String jasj;
	@ApiModelProperty(value = "现场指挥人员")
	private XCZHRY xczhry;
	@ApiModelProperty(value = "所在地消防机构")
	private SZDXFJG szdxfjg;
	@ApiModelProperty(value = "所在地行政区划")
	private SZDXZQH szdxzqh;

	private static class ZQLX {
		private String id;
		private String value;
		private String chain;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getChain() {
			return chain;
		}

		public void setChain(String chain) {
			this.chain = chain;
		}
	}

	private static class ZQDJ {
		private String id;
		private String value;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}

	private static class ZQZT {
		private String id;
		private String value;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}

	private static class ZQDX {
		private String dxlx;
		private String dxbh;
		private String dxmc;
		private String dxgs;

		public String getDxlx() {
			return dxlx;
		}

		public void setDxlx(String dxlx) {
			this.dxlx = dxlx;
		}

		public String getDxbh() {
			return dxbh;
		}

		public void setDxbh(String dxbh) {
			this.dxbh = dxbh;
		}

		public String getDxmc() {
			return dxmc;
		}

		public void setDxmc(String dxmc) {
			this.dxmc = dxmc;
		}

		public String getDxgs() {
			return dxgs;
		}

		public void setDxgs(String dxgs) {
			this.dxgs = dxgs;
		}
	}

	private static class ZQWZ {
		private String sx;
		private String jd;
		private String wd;

		public String getSx() {
			return sx;
		}

		public void setSx(String sx) {
			this.sx = sx;
		}

		public String getJd() {
			return jd;
		}

		public void setJd(String jd) {
			this.jd = jd;
		}

		public String getWd() {
			return wd;
		}

		public void setWd(String wd) {
			this.wd = wd;
		}
	}

	private static class XCZHRY {
		private String ryxm;
		private String gwzz;
		private String lxdh;
		private String xssx;

		public String getRyxm() {
			return ryxm;
		}

		public void setRyxm(String ryxm) {
			this.ryxm = ryxm;
		}

		public String getGwzz() {
			return gwzz;
		}

		public void setGwzz(String gwzz) {
			this.gwzz = gwzz;
		}

		public String getLxdh() {
			return lxdh;
		}

		public void setLxdh(String lxdh) {
			this.lxdh = lxdh;
		}

		public String getXssx() {
			return xssx;
		}

		public void setXssx(String xssx) {
			this.xssx = xssx;
		}
	}

	private static class SZDXFJG {
		private String xfjgbh;
		private String xfjgmc;
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

	private static class SZDXZQH {
		private String xzqhbh;
		private String xzqhmc;
		private String xzqhnbbm;

		public String getXzqhbh() {
			return xzqhbh;
		}

		public void setXzqhbh(String xzqhbh) {
			this.xzqhbh = xzqhbh;
		}

		public String getXzqhmc() {
			return xzqhmc;
		}

		public void setXzqhmc(String xzqhmc) {
			this.xzqhmc = xzqhmc;
		}

		public String getXzqhnbbm() {
			return xzqhnbbm;
		}

		public void setXzqhnbbm(String xzqhnbbm) {
			this.xzqhnbbm = xzqhnbbm;
		}
	}

	public String getZqbh() {
		return zqbh;
	}

	public void setZqbh(String zqbh) {
		this.zqbh = zqbh;
	}

	public String getGlbh() {
		return glbh;
	}

	public void setGlbh(String glbh) {
		this.glbh = glbh;
	}

	public String getZybh() {
		return zybh;
	}

	public void setZybh(String zybh) {
		this.zybh = zybh;
	}

	public String getZqfl() {
		return zqfl;
	}

	public void setZqfl(String zqfl) {
		this.zqfl = zqfl;
	}

	public String getZqmc() {
		return zqmc;
	}

	public void setZqmc(String zqmc) {
		this.zqmc = zqmc;
	}

	public double getJd() {
		return jd;
	}

	public void setJd(double jd) {
		this.jd = jd;
	}

	public double getWd() {
		return wd;
	}

	public void setWd(double wd) {
		this.wd = wd;
	}

	public double getZqdd() {
		return zqdd;
	}

	public void setZqdd(double zqdd) {
		this.zqdd = zqdd;
	}

	public String getZqqc() {
		return zqqc;
	}

	public void setZqqc(String zqqc) {
		this.zqqc = zqqc;
	}

	public String getLasj() {
		return lasj;
	}

	public void setLasj(String lasj) {
		this.lasj = lasj;
	}

	public String getBjsj() {
		return bjsj;
	}

	public void setBjsj(String bjsj) {
		this.bjsj = bjsj;
	}

	public ZQLX getZqlx() {
		return zqlx;
	}

	public void setZqlx(ZQLX zqlx) {
		this.zqlx = zqlx;
	}

	public ZQDJ getZqdj() {
		return zqdj;
	}

	public void setZqdj(ZQDJ zqdj) {
		this.zqdj = zqdj;
	}

	public ZQZT getZqzt() {
		return zqzt;
	}

	public void setZqzt(ZQZT zqzt) {
		this.zqzt = zqzt;
	}

	public ZQDX getZqdx() {
		return zqdx;
	}

	public void setZqdx(ZQDX zqdx) {
		this.zqdx = zqdx;
	}

	public ZQWZ getZqwz() {
		return zqwz;
	}

	public void setZqwz(ZQWZ zqwz) {
		this.zqwz = zqwz;
	}

	public String getZqgs() {
		return zqgs;
	}

	public void setZqgs(String zqgs) {
		this.zqgs = zqgs;
	}

	public String getZqbs() {
		return zqbs;
	}

	public void setZqbs(String zqbs) {
		this.zqbs = zqbs;
	}

	public String getRslc() {
		return rslc;
	}

	public void setRslc(String rslc) {
		this.rslc = rslc;
	}

	public String getJasj() {
		return jasj;
	}

	public void setJasj(String jasj) {
		this.jasj = jasj;
	}

	public XCZHRY getXczhry() {
		return xczhry;
	}

	public void setXczhry(XCZHRY xczhry) {
		this.xczhry = xczhry;
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
