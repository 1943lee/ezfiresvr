package com.ezfire.common;

/**
 * Created by lcy on 2018/1/22.
 */
public class ComMethod {
	public static boolean isValidPoint(double longitude, double latitude) {
		return (longitude > 70.0 && longitude < 140.0) && (latitude > 3.0 && latitude < 60.0);
	}
}
