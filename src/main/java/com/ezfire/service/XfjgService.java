package com.ezfire.service;

/**
 * Created by lcy on 2018/1/22.
 */
public interface XfjgService {
	String getXfjgs(String nbbm, String[] includes);

	String getXfjgById(String dwbh, String[] includes);

	String getXfjgByIds(String[] dwbhs, String[] includes);
}
