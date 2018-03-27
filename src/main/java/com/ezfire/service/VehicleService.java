package com.ezfire.service;

/**
 * Created by lcy on 2018/3/9.
 */
public interface VehicleService {
	String getVehicleBasic(String key, int type, String[] includes);

	String getVehicleBasics(String[] keys, int type, String[] includes);

	String getVehicleStatus(String[] keys);

	String getVehicleLocation(String[] keys);
}
