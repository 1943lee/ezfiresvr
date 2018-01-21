package com.ezfire.common;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by lcy on 2018/1/21.
 */
public class JsonUtils {
	private static ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * bean转json
	 *
	 * @param bean
	 * @param <T>
	 * @return
	 */
	public static <T> String bean2Json(T bean) {
		try {
			return objectMapper.writeValueAsString(bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * map转json
	 *
	 * @param map
	 * @return
	 */
	public static String map2Json(Map map) {
		try {
			return objectMapper.writeValueAsString(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * list转json
	 *
	 * @param list
	 * @return
	 */
	public static String list2Json(List list) {
		try {
			return objectMapper.writeValueAsString(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * json转bean
	 *
	 * @param json
	 * @param beanClass
	 * @param <T>
	 * @return
	 */
	public static <T> T json2Bean(String json, Class<T> beanClass) {
		try {
			return objectMapper.readValue(json, beanClass);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * json转list
	 *
	 * @param json
	 * @param beanClass
	 * @param <T>
	 * @return
	 */
	public static <T> List<T> json2List(String json, Class<T> beanClass) {
		try {
			return (List<T>) objectMapper.readValue(json, getCollectionType(List.class, beanClass));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * json转map
	 *
	 * @param json
	 * @return
	 */
	public static Map json2Map(String json) {
		if (null == json || json.isEmpty()) return null;
		try {
			return (Map) objectMapper.readValue(json, Map.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
		return objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
	}

}
