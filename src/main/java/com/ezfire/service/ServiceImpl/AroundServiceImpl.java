package com.ezfire.service.ServiceImpl;

import com.alibaba.fastjson.JSON;
import com.ezfire.common.ComDefine;
import com.ezfire.common.ComMethod;
import com.ezfire.common.ESClient;
import com.ezfire.common.EsQueryUtils;
import com.ezfire.domain.AroundBwmb;
import com.ezfire.domain.AroundResources;
import com.ezfire.service.AroundService;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lcy on 2018/1/22.
 */
@Service
public class AroundServiceImpl implements AroundService {
	private static Logger s_logger = LoggerFactory.getLogger(AroundServiceImpl.class);
	private static RestHighLevelClient client = ESClient.getHightClient();

	@Override
	public String getWaterAround(ComDefine.fireWaterSourceCode syfl, double longitude, double latitude, double radius, int size) {
		Map<String, AroundResources> resultMap = new HashMap<>();
		switch (syfl) {
			case xhs:
				resultMap.put("xhs",getResourcesAround(syfl,ComDefine.fire_xhs_read,"xhs",longitude,latitude,radius,size));
				break;
			case xfsc:
				resultMap.put("xfsc",getResourcesAround(syfl,ComDefine.fire_xfsc_read,"xfsc",longitude,latitude,radius,size));
				break;
			case xfsh:
				resultMap.put("xfsh",getResourcesAround(syfl,ComDefine.fire_xfsh_read,"xfsh",longitude,latitude,radius,size));
				break;
			case qsmt:
				resultMap.put("qsmt",getResourcesAround(syfl,ComDefine.fire_qsmt_read,"qsmt",longitude,latitude,radius,size));
				break;
			case trsy:
				resultMap.put("trsy",getResourcesAround(syfl,ComDefine.fire_trsy_read,"trsy",longitude,latitude,radius,size));
				break;
			case all:
				resultMap.put("xhs",getResourcesAround(ComDefine.fireWaterSourceCode.xhs,ComDefine.fire_xhs_read,"xhs",longitude,latitude,radius,size));
				resultMap.put("xfsc",getResourcesAround(ComDefine.fireWaterSourceCode.xfsc,ComDefine.fire_xfsc_read,"xfsc",longitude,latitude,radius,size));
				resultMap.put("xfsh",getResourcesAround(ComDefine.fireWaterSourceCode.xfsh,ComDefine.fire_xfsh_read,"xfsh",longitude,latitude,radius,size));
				resultMap.put("qsmt",getResourcesAround(ComDefine.fireWaterSourceCode.qsmt,ComDefine.fire_qsmt_read,"qsmt",longitude,latitude,radius,size));
				resultMap.put("trsy",getResourcesAround(ComDefine.fireWaterSourceCode.trsy,ComDefine.fire_trsy_read,"trsy",longitude,latitude,radius,size));
				break;
			default:
				break;
		}

		return JSON.toJSONString(resultMap);
	}

	@Override
	public String getBwmbAround(String mbfl, double longitude, double latitude, double radius, int size) {
		Map<String, AroundBwmb> resultMap = new HashMap<>();
		String esIndex = "";
		String esType = "";
		switch (mbfl) {
			// "00"表示查询全部分类
			case "00":
				resultMap.put("01",getBwmbsAround("01","重点单位(防火)",ComDefine.fire_dwxx_read,"dwxx",longitude,latitude,radius,size));
				resultMap.put("02",getBwmbsAround("02","重点单位(灭火)",ComDefine.fire_dwxx_miehuo_read,"dwxx",longitude,latitude,radius,size));
				resultMap.put("03",getBwmbsAround("03","建筑信息",ComDefine.fire_jzxx_read,"jzxx",longitude,latitude,radius,size));
				resultMap.put("04",getBwmbsAround("04","油气管线",ComDefine.fire_yqgx_read,"yqgx",longitude,latitude,radius,size));
				resultMap.put("05",getBwmbsAround("05","公路隧道",ComDefine.fire_glsd_read,"glsd",longitude,latitude,radius,size));
				resultMap.put("06",getBwmbsAround("06","石油化工",ComDefine.fire_shxx_read,"shxx",longitude,latitude,radius,size));
				resultMap.put("07",getBwmbsAround("07","核电站",ComDefine.fire_hdz_read,"hdz",longitude,latitude,radius,size));
				resultMap.put("08",getBwmbsAround("08","水库水电站",ComDefine.fire_sdz_read,"sdz",longitude,latitude,radius,size));
			case "01":
				resultMap.put("01",getBwmbsAround("01","重点单位(防火)",ComDefine.fire_dwxx_read,"dwxx",longitude,latitude,radius,size));
				break;
			case "02":
				resultMap.put("02",getBwmbsAround("02","重点单位(灭火)",ComDefine.fire_dwxx_miehuo_read,"dwxx",longitude,latitude,radius,size));
				break;
			case "03":
				resultMap.put("03",getBwmbsAround("03","建筑信息",ComDefine.fire_jzxx_read,"jzxx",longitude,latitude,radius,size));
				break;
			case "04":
				resultMap.put("04",getBwmbsAround("04","油气管线",ComDefine.fire_yqgx_read,"yqgx",longitude,latitude,radius,size));
				break;
			case "05":
				resultMap.put("05",getBwmbsAround("05","公路隧道",ComDefine.fire_glsd_read,"glsd",longitude,latitude,radius,size));
				break;
			case "06":
				resultMap.put("06",getBwmbsAround("06","石油化工",ComDefine.fire_shxx_read,"shxx",longitude,latitude,radius,size));
				break;
			case "07":
				resultMap.put("07",getBwmbsAround("07","核电站",ComDefine.fire_hdz_read,"hdz",longitude,latitude,radius,size));
				break;
			case "08":
				resultMap.put("08",getBwmbsAround("08","水库水电站",ComDefine.fire_sdz_read,"sdz",longitude,latitude,radius,size));
				break;
			default:
				break;
		}

		return JSON.toJSONString(resultMap);
	}

	private AroundResources getResourcesAround(ComDefine.fireWaterSourceCode syfl, String indexName, String typeName, double longitude, double latitude, double radius, int size) {
		AroundResources aroundResult = new AroundResources();
		aroundResult.setResourceType(syfl);
		aroundResult.setResourceTypeDescription(syfl.getValue());
		// 检查坐标是否合法，size是否超出
		if(!ComMethod.isValidPoint(longitude, latitude) || size > 10000) {
			return aroundResult;
		}

		SearchHits searchHits = EsQueryUtils.getAroundResourceHits(indexName,typeName,longitude,latitude,radius,size);
		if(null == searchHits) {
			return aroundResult;
		}
		List<Map<String,Object>> results = new ArrayList<>();
		for (SearchHit searchHit : searchHits) {
			results.add(searchHit.getSource());
		}
		aroundResult.setContent(JSON.toJSONString(results));
		aroundResult.setTotalCount(searchHits.getTotalHits());

		return aroundResult;
	}

	private AroundBwmb getBwmbsAround(String mbType, String mbTypeDescription, String indexName, String typeName, double longitude, double latitude, double radius, int size) {
		AroundBwmb aroundBwmb = new AroundBwmb();
		aroundBwmb.setResourceType(mbType);
		aroundBwmb.setResourceTypeDescription(mbTypeDescription);
		// 检查坐标是否合法，size是否超出
		if(!ComMethod.isValidPoint(longitude, latitude) || size > 10000) {
			return aroundBwmb;
		}

		SearchHits searchHits = EsQueryUtils.getAroundResourceHits(indexName,typeName,longitude,latitude,radius,size);
		if(null == searchHits) {
			return aroundBwmb;
		}
		List<Map<String,Object>> results = new ArrayList<>();
		for (SearchHit searchHit : searchHits) {
			results.add(searchHit.getSource());
		}
		aroundBwmb.setContent(JSON.toJSONString(results));
		aroundBwmb.setTotalCount(searchHits.getTotalHits());

		return aroundBwmb;
	}
}
