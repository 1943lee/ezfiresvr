package com.ezfire.service;

/**
 * Created by lcy on 2018/2/2.
 */
public interface FireDangerService {
	String getAll(int from,int size,String nbbm,String zwm,String ywm,String fzs);

	String getDictionary();

	String getDetail(String bh);
}
