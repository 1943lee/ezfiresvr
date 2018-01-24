package com.ezfire.common;

import org.elasticsearch.common.unit.TimeValue;

import java.util.concurrent.TimeUnit;

/**
 * Created by lcy on 2018/1/21.
 */
public class ComDefine {

	/**
	 * ES查询超时设置
	 */
	public static TimeValue elasticTimeOut = new TimeValue(60, TimeUnit.SECONDS);

	public static int elasticMaxSearchSize = 10000;

	/**
	 * ES中SHAPE字段
	 */
	public static String esGeoShapeColumn = "SHAPE";

	/**
	 * 灾情信息
	 */
	public static String fire_zqxx_read = "fire_zqxx_read";
	public static String fire_zqxx_write = "fire_zqxx_write";
	/**
	 * 消火栓
	 */
	public static String fire_xhs_read = "fire_xhs_read";
	public static String fire_xhs_write = "fire_xhs_write";
	/**
	 * 消防水池
	 */
	public static String fire_xfsc_read = "fire_xfsc_read";
	public static String fire_xfsc_write = "fire_xfsh_write";
	/**
	 * 消防水鹤
	 */
	public static String fire_xfsh_read = "fire_xfsh_read";
	public static String fire_xfsh_write = "fire_xfsh_write";
	/**
	 * 取水码头
	 */
	public static String fire_qsmt_read = "fire_qsmt_read";
	public static String fire_qsmt_write = "fire_qsmt_write";
	/**
	 * 天然水源
	 */
	public static String fire_trsy_read = "fire_trsy_read";
	public static String fire_trsy_write = "fire_trsy_write";
	/**
	 * 消防单位（机构）
	 */
	public static String fire_xfdw_read = "fire_xfdw_read";
	public static String fire_xfdw_write = "fire_xfdw_write";

	/**
	 * 消防水源
	 */
	public enum fireWaterSourceCode {
		all("全部"),
		xhs("消火栓"),
		xfsc("消防水池"),
		xfsh("消防水鹤"),
		qsmt("取水码头"),
		trsy("天然水源");

		private String value;

		fireWaterSourceCode(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}
}
