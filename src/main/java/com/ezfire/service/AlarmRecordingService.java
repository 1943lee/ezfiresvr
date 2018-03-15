package com.ezfire.service;

import java.util.Map;

/**
 * Created by lcy on 2018/3/15.
 */
public interface AlarmRecordingService {
	String getRecordingByConditions(Map<String, Object> conditions);
}
