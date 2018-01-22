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
}
