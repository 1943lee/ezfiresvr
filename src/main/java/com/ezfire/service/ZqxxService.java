package com.ezfire.service;

import java.util.Map;

/**
 * Created by lcy on 2018/1/21.
 */
public interface ZqxxService {
	String getZqxxByZQBH(String zqbh);

	String getZqxxByCondition(Map<String, Object> condition);
}
