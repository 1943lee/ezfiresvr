package com.ezfire.common;

import com.vividsolutions.jts.geom.Coordinate;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by lcy on 2018/1/22.
 */
public class ComMethod {
	/**
	 * 判断地理坐标是否合法
	 * @param longitude 经度
	 * @param latitude 纬度
	 * @return bool
	 */
	public static boolean isValidPoint(double longitude, double latitude) {
		return (longitude > 70.0 && longitude < 140.0) && (latitude > 3.0 && latitude < 60.0);
	}

	/**
	 * 获取Bean实体类属性名,统一转大写
	 * @param originClass 类名
	 * @return 属性名数组
	 */
	public static String[] getBeanFields(Class originClass) {
		if(null == originClass) return null;
		Field[] fields = originClass.getDeclaredFields();
		String[] results = new String[fields.length];
		int i = 0;
		for(Field field : fields) {
			results[i++] = field.getName().toUpperCase();
		}
		return  results;
	}

	/**
	 * 根据经纬度获取距离
	 * @param startX
	 * @param startY
	 * @param endX
	 * @param endY
	 * @return
	 */
	public static double getSphericalDistance(double startX, double startY, double endX, double endY) {
		Coordinate startPt = new Coordinate(startX, startY);
		Coordinate endPt = new Coordinate(endX, endY);

		double EarthRadius = 6378.137;
		startPt = webMercatorToGeographic(startPt);
		endPt = webMercatorToGeographic(endPt);
		double lon1 = startPt.x / 180 * Math.PI;
		double lon2 = endPt.x / 180 * Math.PI;
		double lat1 = startPt.y / 180 * Math.PI;
		double lat2 = endPt.y / 180 * Math.PI;
		double distance = 2 * Math.asin(Math.sqrt(Math.pow((Math.sin((lat1 - lat2) / 2)), 2) +
				Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin((lon1 - lon2) / 2), 2))) * EarthRadius * 1000;

		return (double)(Math.round(distance*1)/1.0);
	}

	/**
	 * web墨卡托转地理坐标
	 */
	public static Coordinate webMercatorToGeographic(Coordinate coord) {
		if (coord == null) {
			return null;
		}
		if(isGeoCoordinate(coord)) {
			return coord;
		}
		double x = coord.x;
		double y = coord.x;
		double num3 = x / 6378137.0;
		double num4 = num3 * 57.295779513082323;
		double num5 = Math.floor((double) ((num4 + 180.0) / 360.0));
		double num6 = num4 - (num5 * 360.0);
		double num7 = 1.5707963267948966 - (2.0 * Math.atan(Math.exp((-1.0 * y) / 6378137.0)));
		return new Coordinate(num6 + (num5 * 360.0), num7 * 57.295779513082323);
	}

	/**
	 * 判断是否是地理坐标系
	 */
	private static boolean isGeoCoordinate(Coordinate coord) {
		if(coord == null) {
			return false;
		}

		if(coord.x >= -180 && coord.x <= 180 && coord.y >= -90 && coord.y <= 90) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * 字符串转码，处理中文输入
	 * @param str
	 * @return
	 */
	public static String encodeStr(String str) {
		try {
			return new String(str.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 字符串转码，处理中文输入
	 * @param str
	 * @return
	 */
	public static String[] encodeStrs(String[] str) {
		String[] res = new String[str.length];
		for(int i = 0; i < str.length; i++) {
			try {
				res[i] = new String(str[i].getBytes("ISO-8859-1"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return res;
	}

	/**
	 * 判断输入字符串是否为指定时间格式
	 * @param str
	 * @param format
	 * @return
	 */
	public static boolean isValidDate(String str, SimpleDateFormat format) {
		boolean convertSuccess = true;

		try {
			// 设置lenient为false.
			// 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
			format.setLenient(false);
			format.parse(str);
		} catch (ParseException e) {
			// e.printStackTrace();
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			convertSuccess = false;
		}
		return convertSuccess;
	}
}
