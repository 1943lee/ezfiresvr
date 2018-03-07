package com.ezfire.service.ServiceImpl;

import com.ezfire.common.ComDefine;
import com.ezfire.common.EsQueryUtils;
import com.ezfire.service.DxxxService;
import org.springframework.stereotype.Service;

/**
 * Created by lcy on 2018/1/24.
 */
@Service
public class DxxxServiceImpl implements DxxxService{

	@Override
	public String getBasicInfo(String type, String id) {
		if(null == id || id.isEmpty()) {
			return null;
		}
		String esIndex = "";
		String esType = "";
		String idColumn = "";
		switch (type) {
			case "01":
				esIndex = ComDefine.fire_dwxx_read;
				esType = "dwxx";
				idColumn = "DWBH";
				break;
			case "02":
				esIndex = ComDefine.fire_dwxx_miehuo_read;
				esType = "dwxx";
				idColumn = "DWBH";
				break;
			case "03":
				esIndex = ComDefine.fire_jzxx_read;
				esType = "jzxx";
				idColumn = "JZBH";
				break;
			/*case "04":
				esIndex = ComDefine.fire_yqgx_read;
				esType = "yqgx";
				idColumn = "GXBH";
				break;
			case "05":
				esIndex = ComDefine.fire_glsd_read;
				esType = "glsd";
				idColumn = "SDBH";
				break;*/
			case "06":
				esIndex = ComDefine.fire_shxx_read;
				esType = "shxx";
				idColumn = "DWBH";
				break;
			case "07":
				esIndex = ComDefine.fire_hdz_read;
				esType = "hdz";
				idColumn = "HDZBH";
				break;
			case "08":
				esIndex = ComDefine.fire_sdz_read;
				esType = "sdz";
				idColumn = "SDZBH";
				break;
		}

		if(esIndex.isEmpty() || esType.isEmpty() || idColumn.isEmpty()) {
			return null;
		}
		return EsQueryUtils.queryById(esIndex,esType,id,idColumn,null,null);
	}
}
