package com.ezfire.service.ServiceImpl;

import com.ezfire.common.EsQueryUtils;
import com.ezfire.service.VehicleService;
import org.springframework.stereotype.Service;

/**
 * Created by lcy on 2018/3/9.
 */
@Service
public class VehicleServiceImpl implements VehicleService {
	@Override
	public String getVehicleBasic(String key, int type) {
		String colName = "";
		if(type == 0) {
			colName = "CLBH";
		}
		else if(type == 1) {
			colName = "CPHM";
		}

		if(colName.isEmpty() || key.isEmpty()) {
			return null;
		}

		return EsQueryUtils.queryById("fire_clxx_read", "clxx", key, colName, null, null);
	}
}
