package com.ezfire.common;

import java.lang.reflect.Field;

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
		Field[] fields = originClass.getDeclaredFields();
		String[] results = new String[fields.length];
		int i = 0;
		for(Field field : fields) {
			results[i++] = field.getName().toUpperCase();
		}
		return  results;
	}
}
