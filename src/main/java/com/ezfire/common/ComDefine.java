package com.ezfire.common;

import org.elasticsearch.common.unit.TimeValue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by lcy on 2018/1/21.
 */
public class ComDefine {

	/**
	 * ES查询超时设置
	 */
	public final static TimeValue elasticTimeOut = new TimeValue(60, TimeUnit.SECONDS);

	public final static int elasticMaxSearchSize = 10000;

	/**
	 * ES中SHAPE字段
	 */
	public final static String esGeoShapeColumn = "SHAPE";

	/**
	 * redis车辆状态分组前缀
	 */
	public final static String redisVehicleStatusPrefix = "vehStatusInfo:";
	/**
	 * redis车辆位置分组前缀
	 */
	public final static String redisVehicleLocationPrefix = "vehLocationInfo:";

	/**
	 * 灾情信息
	 */
	public final static String fire_zqxx_read = "fire_zqxx_read";
	public final static String fire_zqxx_write = "fire_zqxx_write";
	/**
	 * 调派信息
	 */
	public final static String fire_dpxx_read = "fire_dpxx_read";
	public final static String fire_dpxx_write = "fire_dpxx_write";
	/**
	 * 消火栓
	 */
	public final static String fire_xhs_read = "fire_xhs_read";
	public final static String fire_xhs_write = "fire_xhs_write";
	/**
	 * 消防水池
	 */
	public final static String fire_xfsc_read = "fire_xfsc_read";
	public final static String fire_xfsc_write = "fire_xfsh_write";
	/**
	 * 消防水鹤
	 */
	public final static String fire_xfsh_read = "fire_xfsh_read";
	public final static String fire_xfsh_write = "fire_xfsh_write";
	/**
	 * 取水码头
	 */
	public final static String fire_qsmt_read = "fire_qsmt_read";
	public final static String fire_qsmt_write = "fire_qsmt_write";
	/**
	 * 天然水源
	 */
	public final static String fire_trsy_read = "fire_trsy_read";
	public final static String fire_trsy_write = "fire_trsy_write";
	/**
	 * 消防单位（机构）
	 */
	public final static String fire_xfdw_read = "fire_xfdw_read";
	public final static String fire_xfdw_write = "fire_xfdw_write";
	/**
	 * 重点单位（防火）
	 */
	public final static String fire_dwxx_read = "fire_dwxx_read";
	public final static String fire_dwxx_write = "fire_dwxx_write";
	/**
	 * 重点单位（灭火）
	 */
	public final static String fire_dwxx_miehuo_read = "fire_dwxx_miehuo_read";
	/**
	 * 建筑信息
	 */
	public final static String fire_jzxx_read = "fire_jzxx_read";
	/**
	 * 油气管线
	 */
	public final static String fire_yqgx_read = "fire_yqgx_read";
	/**
	 * 公路隧道
	 */
	public final static String fire_glsd_read = "fire_glsd_read";
	/**
	 * 石化信息
	 */
	public final static String fire_shxx_read = "fire_shxx_read";
	/**
	 * 核电站
	 */
	public final static String fire_hdz_read = "fire_hdz_read";
	/**
	 * 水电站、水库
	 */
	public final static String fire_sdz_read = "fire_sdz_read";
	/**
	 * 联勤保障单位
	 */
	public final static String fire_lqbzdw_read = "fire_lqbzdw_read";
	/**
	 * 应急联动单位
	 */
	public final static String fire_yjlddw_read = "fire_yjlddw_read";
	/**
	 * 灭火救援专家
	 */
	public final static String fire_mhjyzj_read = "fire_mhjyzj_read";
	/**
	 * 文书信息
	 */
	public final static String fire_wsxx_read = "fire_wsxx_read";
	/**
	 * 人员信息
	 */
	public final static String fire_ryxx_read = "fire_ryxx_read";
	/**
	 * 灾情指令信息
	 */
	public final static String fire_zqzl_read = "fire_zqzl_read";
	/**
	 * 车辆信息
	 */
	public final static String fire_clxx_read = "fire_clxx_read";
	/**
	 * 灾情录音信息
	 */
	public final static String fire_zqly_read = "fire_zqly_read";

	/**
	 * 消防水源
	 */
	public enum fireWaterSourceCode {
		all("全部","",""),
		xhs("消火栓",ComDefine.fire_xhs_read,"xhs"),
		xfsc("消防水池",ComDefine.fire_xfsc_read,"xfsc"),
		xfsh("消防水鹤",ComDefine.fire_xfsh_read,"xfsh"),
		qsmt("取水码头",ComDefine.fire_qsmt_read,"qsmt"),
		trsy("天然水源",ComDefine.fire_trsy_read,"trsy");

		private String value;
		private String index;
		private String type;

		fireWaterSourceCode(String value,String index,String type) {
			this.value = value;
			this.index = index;
			this.type = type;
		}

		public String getValue() {
			return value;
		}

		public String getIndex() {
			return index;
		}

		public String getType() {
			return type;
		}

		public static List<fireWaterSourceCode> getAll() {
			List<fireWaterSourceCode> list = new ArrayList<>();
			list.add(xhs);
			list.add(xfsc);
			list.add(xfsh);
			list.add(qsmt);
			list.add(trsy);
			return list;
		}
	}

	/**
	 * 缓存字典项key
	 */
	public enum CacheNameDefine {
		fire_dictionary_vehicle_type("10003","消防-装备类型"),
		fire_dictionary_fireDanger_type("10006","消防-危化品类别");

		private String value;
		private String description;

		CacheNameDefine(String value,String description) {
			this.value = value;
			this.description = description;
		}

		public String getValue() {
			return value;
		}

		public String getDescription() {
			return description;
		}
	}
}
