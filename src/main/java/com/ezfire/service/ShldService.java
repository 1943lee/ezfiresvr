package com.ezfire.service;

/**
 * Created by lcy on 2018/1/25.
 */
public interface ShldService {
	String getLqbzdwByXzqhnbbm(String nbbm, int from, int size);

	String getYjlddwByXzqhnbbm(String nbbm, int from, int size);

	String getMhjyzjByXzqhnbbmAndXzjb(String nbbm, int xzjb, int from, int size);
}
