package com.ezfire.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by lcy on 2018/1/21.
 */
@ApiModel(description = "灾情信息")
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

	@ApiModel(description = "灾情类型")
	private static class ZQLX {
		@ApiModelProperty(value = "类型id")
		private String id;
		@ApiModelProperty(value = "类型名称")
		private String value;
		@ApiModelProperty(value = "灾情类型内部编码")
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

	@ApiModel(description = "灾情等级")
	private static class ZQDJ {
		@ApiModelProperty(value = "灾情等级id")
		private String id;
		@ApiModelProperty(value = "灾情等级名称")
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

	@ApiModel(description = "灾情状态")
	private static class ZQZT {
		@ApiModelProperty(value = "灾情状态id")
		private String id;
		@ApiModelProperty(value = "灾情状态名称")
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

	@ApiModel(description = "灾情对象")
	private static class ZQDX {
		@ApiModelProperty(value = "灾情对象类型")
		private String dxlx;
		@ApiModelProperty(value = "灾情对象编号")
		private String dxbh;
		@ApiModelProperty(value = "灾情对象名称")
		private String dxmc;
		@ApiModelProperty(value = "灾情对象概述")
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

	@ApiModel(description = "灾情位置")
	private static class ZQWZ {
		@ApiModelProperty(value = "灾情位置序号")
		private String sx;
		@ApiModelProperty(value = "灾情位置经度")
		private String jd;
		@ApiModelProperty(value = "灾情位置纬度")
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

	@ApiModel(description = "现场操作人员")
	private static class XCZHRY {
		@ApiModelProperty(value = "人员姓名")
		private String ryxm;
		@ApiModelProperty(value = "岗位职责")
		private String gwzz;
		@ApiModelProperty(value = "联系电话")
		private String lxdh;
		@ApiModelProperty(value = "显示顺序")
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

	@ApiModel(description = "所在地行政区划")
	private static class SZDXZQH {
		@ApiModelProperty(value = "行政区划编号")
		private String xzqhbh;
		@ApiModelProperty(value = "行政区划名称")
		private String xzqhmc;
		@ApiModelProperty(value = "行政区划内部编码")
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
