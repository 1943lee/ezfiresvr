package com.ezfire.service;

/**
 * Created by lcy on 2018/1/24.
 */
public interface DpxxService {
	String getDpxxByZQBH(String zqbh, String[] includes);
	//String getDpxxByZQBHWithIncludes(String zqbh, String[] includes);

	String getDpxxZqbhByClbhOrCphm(String key, int type, String[] includes);
	//String getDpxxZqbhByClbhOrCphmWithIncludes(String key, int type, String[] includes);
}
