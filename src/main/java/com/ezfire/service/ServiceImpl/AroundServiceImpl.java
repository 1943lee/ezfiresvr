package com.ezfire.service.ServiceImpl;

import com.alibaba.fastjson.JSON;
import com.ezfire.common.*;
import com.ezfire.domain.AroundResource;
import com.ezfire.service.AroundService;
import com.vividsolutions.jts.geom.Coordinate;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Created by lcy on 2018/1/22.
 */
@Service
public class AroundServiceImpl implements AroundService {

	@Override
	public String getWaterAround(ComDefine.fireWaterSourceCode syfl, double longitude, double latitude, double radius, int size) {
		List<AroundResource> resourcesList = new ArrayList<>();
		switch (syfl) {
			case xhs:
			case xfsc:
			case xfsh:
			case qsmt:
			case trsy:
				resourcesList.addAll(getResourcesAround(syfl.name(),syfl.getValue(),syfl.getIndex(),syfl.getType(),longitude,latitude,radius,size));
				break;
			case all:
				for (ComDefine.fireWaterSourceCode syflTmp : ComDefine.fireWaterSourceCode.getAll()) {
					resourcesList.addAll(getResourcesAround(syflTmp.name(),syflTmp.getValue(),syflTmp.getIndex(),syflTmp.getType(),longitude,latitude,radius,size));
				}
				break;
			default:
				break;
		}

		resourcesList.sort(Comparator.comparingDouble(AroundResource::getDistance));
		return JSON.toJSONString(resourcesList);
	}

	@Override
	public String getBwmbAround(String mbfl, double longitude, double latitude, double radius, int size) {
		List<AroundResource> resultList = new ArrayList<>();
		switch (mbfl) {
			// "00"表示查询全部分类
			case "00":
				resultList.addAll(getResourcesAround("01","重点单位(防火)",ComDefine.fire_dwxx_read,"dwxx",longitude,latitude,radius,size));
				resultList.addAll(getResourcesAround("02","重点单位(灭火)",ComDefine.fire_dwxx_miehuo_read,"dwxx",longitude,latitude,radius,size));
				resultList.addAll(getResourcesAround("03","建筑信息",ComDefine.fire_jzxx_read,"jzxx",longitude,latitude,radius,size));
				//resultList.addAll(getResourcesAround("04","油气管线",ComDefine.fire_yqgx_read,"yqgx",longitude,latitude,radius,size));
				//resultList.addAll(getResourcesAround("05","公路隧道",ComDefine.fire_glsd_read,"glsd",longitude,latitude,radius,size));
				resultList.addAll(getResourcesAround("06","石油化工",ComDefine.fire_shxx_read,"shxx",longitude,latitude,radius,size));
				resultList.addAll(getResourcesAround("07","核电站",ComDefine.fire_hdz_read,"hdz",longitude,latitude,radius,size));
				resultList.addAll(getResourcesAround("08","水库水电站",ComDefine.fire_sdz_read,"sdz",longitude,latitude,radius,size));
				break;
			case "01":
				resultList.addAll(getResourcesAround("01","重点单位(防火)",ComDefine.fire_dwxx_read,"dwxx",longitude,latitude,radius,size));
				break;
			case "02":
				resultList.addAll(getResourcesAround("02","重点单位(灭火)",ComDefine.fire_dwxx_miehuo_read,"dwxx",longitude,latitude,radius,size));
				break;
			case "03":
				resultList.addAll(getResourcesAround("03","建筑信息",ComDefine.fire_jzxx_read,"jzxx",longitude,latitude,radius,size));
				break;
			/*case "04":
				resultList.addAll(getResourcesAround("04","油气管线",ComDefine.fire_yqgx_read,"yqgx",longitude,latitude,radius,size));
				break;
			case "05":
				resultList.addAll(getResourcesAround("05","公路隧道",ComDefine.fire_glsd_read,"glsd",longitude,latitude,radius,size));
				break;*/
			case "06":
				resultList.addAll(getResourcesAround("06","石油化工",ComDefine.fire_shxx_read,"shxx",longitude,latitude,radius,size));
				break;
			case "07":
				resultList.addAll(getResourcesAround("07","核电站",ComDefine.fire_hdz_read,"hdz",longitude,latitude,radius,size));
				break;
			case "08":
				resultList.addAll(getResourcesAround("08","水库水电站",ComDefine.fire_sdz_read,"sdz",longitude,latitude,radius,size));
				break;
			default:
				break;
		}

		resultList.sort(Comparator.comparingDouble(AroundResource::getDistance));
		return JSON.toJSONString(resultList);
	}

	private List<AroundResource> getResourcesAround(String resourceType, String resourceTyneDescription, String indexName, String typeName, double longitude, double latitude, double radius, int size) {
		List<AroundResource> aroundResultList = new ArrayList<>();
		// 检查坐标是否合法，size是否超出
		if(!ComMethod.isValidPoint(longitude, latitude) || size > 10000) {
			return aroundResultList;
		}

		SearchHits searchHits = EsQueryUtils.getAroundResourceHits(indexName,typeName,longitude,latitude,radius,size);
		if(null == searchHits) {
			return aroundResultList;
		}

		for (SearchHit searchHit : searchHits) {
			AroundResource aroundResult = new AroundResource();
			aroundResult.setResourceType(resourceType);
			aroundResult.setResourceTypeDescription(resourceTyneDescription);
			Map<String,Object> source = searchHit.getSource();
			double distance = 0.0;
			if(!indexName.equals(ComDefine.fire_trsy_read)) {
				double jd = source.containsKey("JD") ? ComConvert.toDouble(source.get("JD"), 0.0) : 0.0;
				double wd = source.containsKey("WD") ? ComConvert.toDouble(source.get("WD"), 0.0) : 0.0;
				distance = ComMethod.getSphericalDistance(longitude, latitude, jd, wd);
			} else {
				Map<String, Object> shape = (Map<String, Object>) source.get("SHAPE");
				String shapeWkt = WktUtils.GeoShapeToWkt(shape);
				Coordinate centerP = WktUtils.getCenterPoint(shapeWkt);
				distance = ComMethod.getSphericalDistance(longitude, latitude, centerP.x, centerP.y);
			}
			// 二次校验
			if(distance > radius) {
				continue;
			}
			aroundResult.setContent(JSON.toJSONString(source));
			aroundResult.setDistance(distance);

			aroundResultList.add(aroundResult);
		}

		return aroundResultList;
	}
}
