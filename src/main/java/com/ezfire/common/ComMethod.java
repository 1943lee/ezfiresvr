package com.ezfire.common;

import java.lang.reflect.Field;

/**
 * Created by lcy on 2018/1/22.
 */
public class ComMethod {
	/**
	 * 判断地理坐标是否合法
	 * @param longitude
	 * @param latitude
	 * @return
	 */
	public static boolean isValidPoint(double longitude, double latitude) {
		return (longitude > 70.0 && longitude < 140.0) && (latitude > 3.0 && latitude < 60.0);
	}

	/**
	 * 获取Bean实体类属性名
	 * @param originClass
	 * @return
	 */
	public static String[] getBeanFields(Class originClass) {
		Field[] fields = originClass.getDeclaredFields();
		String[] results = new String[fields.length];
		int i = 0;
		for(Field field : fields) {
			results[i++] = field.getName();
		}
		return  results;
	}
}
