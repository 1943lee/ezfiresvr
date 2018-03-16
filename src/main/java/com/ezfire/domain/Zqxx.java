package com.ezfire.domain;

import com.ezfire.domain.comDomains.IdValue;
import com.ezfire.domain.comDomains.IdValueChain;
import com.ezfire.domain.comDomains.SZDXFJG;
import com.ezfire.domain.comDomains.SZDXZQH;
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
	@ApiModelProperty(value = "突出灾情")
	private TCZQ tczq;
	@ApiModelProperty(value = "灾情类型")
	private IdValueChain zqlx;
	@ApiModelProperty(value = "灾情等级")
	private ZQDJ zqdj;
	@ApiModelProperty(value = "灾情状态")
	private IdValue zqzt;
	@ApiModelProperty(value = "灾情对象")
	private ZQDX zqdx;
	@ApiModelProperty(value = "灾情位置")
	private ZQWZ zqwz;
	@ApiModelProperty(value = "灾情概述")
	private String zqgs;
	@ApiModelProperty(value = "灾情标识, 0为假警, 1为真警")
	private String zqbs;
	@ApiModelProperty(value = "燃烧楼层")
	private String rslc;
	@ApiModelProperty(value = "报警人员")
	private String bjry;
	@ApiModelProperty(value = "报警电话")
	private String bjdh;
	@ApiModelProperty(value = "报警方式")
	private String bjfs;
	@ApiModelProperty(value = "结案时间")
	private String jasj;
	@ApiModelProperty(value = "接受命令时间")
	private String jsmlsj;
	@ApiModelProperty(value = "出动警力时间")
	private String cdjlsj;
	@ApiModelProperty(value = "到达现场时间")
	private String ddxcsj;
	@ApiModelProperty(value = "战斗展开时间")
	private String zdzksj;
	@ApiModelProperty(value = "到场出水时间")
	private String dccssj;
	@ApiModelProperty(value = "火势控制时间")
	private String hskzsj;
	@ApiModelProperty(value = "基本扑灭时间")
	private String jbpmsj;
	@ApiModelProperty(value = "警力归队时间")
	private String jlgdsj;
	@ApiModelProperty(value = "现场指挥人员")
	private XCZHRY xczhry;
	@ApiModelProperty(value = "所在地消防机构")
	private SZDXFJG szdxfjg;
	@ApiModelProperty(value = "所在地行政区划")
	private SZDXZQH szdxzqh;
	@ApiModelProperty(value = "跨区域消防机构,单个值或数组形式")
	private SZDXFJG kqyxfjg;
	@ApiModelProperty(value = "跨区域行政区划,单个值或数组形式")
	private SZDXZQH kqyxzqh;

	@ApiModel(description = "突出灾情")
	private static class TCZQ {
		@ApiModelProperty(value = "部局")
		private String level_0;
		@ApiModelProperty(value = "总队")
		private String level_1;
		@ApiModelProperty(value = "支队")
		private String level_2;
		@ApiModelProperty(value = "大队")
		private String level_3;
		@ApiModelProperty(value = "中队")
		private String level_4;

		public String getLevel_0() {
			return level_0;
		}

		public void setLevel_0(String level_0) {
			this.level_0 = level_0;
		}

		public String getLevel_1() {
			return level_1;
		}

		public void setLevel_1(String level_1) {
			this.level_1 = level_1;
		}

		public String getLevel_2() {
			return level_2;
		}

		public void setLevel_2(String level_2) {
			this.level_2 = level_2;
		}

		public String getLevel_3() {
			return level_3;
		}

		public void setLevel_3(String level_3) {
			this.level_3 = level_3;
		}

		public String getLevel_4() {
			return level_4;
		}

		public void setLevel_4(String level_4) {
			this.level_4 = level_4;
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

	public TCZQ getTczq() {
		return tczq;
	}

	public void setTczq(TCZQ tczq) {
		this.tczq = tczq;
	}

	public ZQDJ getZqdj() {
		return zqdj;
	}

	public void setZqdj(ZQDJ zqdj) {
		this.zqdj = zqdj;
	}

	public IdValue getZqzt() {
		return zqzt;
	}

	public void setZqzt(IdValue zqzt) {
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

	public SZDXFJG getKqyxfjg() {
		return kqyxfjg;
	}

	public void setKqyxfjg(SZDXFJG kqyxfjg) {
		this.kqyxfjg = kqyxfjg;
	}

	public SZDXZQH getKqyxzqh() {
		return kqyxzqh;
	}

	public void setKqyxzqh(SZDXZQH kqyxzqh) {
		this.kqyxzqh = kqyxzqh;
	}

	public IdValueChain getZqlx() {
		return zqlx;
	}

	public void setZqlx(IdValueChain zqlx) {
		this.zqlx = zqlx;
	}

	public String getBjry() {
		return bjry;
	}

	public void setBjry(String bjry) {
		this.bjry = bjry;
	}

	public String getBjdh() {
		return bjdh;
	}

	public void setBjdh(String bjdh) {
		this.bjdh = bjdh;
	}

	public String getBjfs() {
		return bjfs;
	}

	public void setBjfs(String bjfs) {
		this.bjfs = bjfs;
	}

	public String getJsmlsj() {
		return jsmlsj;
	}

	public void setJsmlsj(String jsmlsj) {
		this.jsmlsj = jsmlsj;
	}

	public String getCdjlsj() {
		return cdjlsj;
	}

	public void setCdjlsj(String cdjlsj) {
		this.cdjlsj = cdjlsj;
	}

	public String getDdxcsj() {
		return ddxcsj;
	}

	public void setDdxcsj(String ddxcsj) {
		this.ddxcsj = ddxcsj;
	}

	public String getZdzksj() {
		return zdzksj;
	}

	public void setZdzksj(String zdzksj) {
		this.zdzksj = zdzksj;
	}

	public String getDccssj() {
		return dccssj;
	}

	public void setDccssj(String dccssj) {
		this.dccssj = dccssj;
	}

	public String getHskzsj() {
		return hskzsj;
	}

	public void setHskzsj(String hskzsj) {
		this.hskzsj = hskzsj;
	}

	public String getJbpmsj() {
		return jbpmsj;
	}

	public void setJbpmsj(String jbpmsj) {
		this.jbpmsj = jbpmsj;
	}

	public String getJlgdsj() {
		return jlgdsj;
	}

	public void setJlgdsj(String jlgdsj) {
		this.jlgdsj = jlgdsj;
	}
}
