package com.ezfire.service;

import com.ezfire.domain.RestfulParams.AlarmCondition;

import java.util.Map;

/**
 * Created by lcy on 2018/1/21.
 */
public interface ZqxxService {
	String getZqxxByZQBH(String zqbh);

	String getZqxxByCondition(Map<String, Object> condition);

	String getNearestZqxx(double longitude, double latitude, double radius, int dateRange);

	String getZqxxSearch(AlarmCondition conditions);
}
