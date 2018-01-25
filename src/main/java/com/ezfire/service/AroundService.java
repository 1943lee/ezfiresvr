package com.ezfire.service;

import com.ezfire.common.ComDefine;

/**
 * Created by lcy on 2018/1/22.
 */
public interface AroundService {
	String getWaterAround(ComDefine.fireWaterSourceCode syfl, double longitude, double latitude, double radius, int size);

	String getBwmbAround(String mbfl, double longitude, double latitude, double radius, int size);
}
