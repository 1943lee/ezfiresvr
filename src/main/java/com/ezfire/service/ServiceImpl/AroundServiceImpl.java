package com.ezfire.service.ServiceImpl;

import com.alibaba.fastjson.JSON;
import com.ezfire.common.ComDefine;
import com.ezfire.common.ComMethod;
import com.ezfire.common.ESClient;
import com.ezfire.domain.AroundResources;
import com.ezfire.service.AroundService;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.geo.builders.ShapeBuilders;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
	public String getAround(ComDefine.fireWaterSourceCode syfl, double longitude, double latitude, double radius, int size) {
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

	private AroundResources getResourcesAround(ComDefine.fireWaterSourceCode syfl, String indexName, String typeName, double longitude, double latitude, double radius, int size) {
		AroundResources aroundResult = new AroundResources();
		aroundResult.setResourceType(syfl);
		aroundResult.setResourceTypeDescription(syfl.getValue());
		// 检查坐标是否合法，size是否超出
		if(!ComMethod.isValidPoint(longitude, latitude) || size > 10000) {
			return aroundResult;
		}

		try {
			SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
			BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
			boolQueryBuilder.mustNot().add(QueryBuilders.termQuery("JLZT", "0"));
			boolQueryBuilder.must().add(QueryBuilders.geoShapeQuery(ComDefine.esGeoShapeColumn,
					ShapeBuilders.newCircleBuilder().center(longitude, latitude).radius(String.valueOf(radius) + "m")));

			searchSourceBuilder.query(boolQueryBuilder)
					.timeout(ComDefine.elasticTimeOut)
					.size(size);

			SearchRequest searchRequest = new SearchRequest()
					.source(searchSourceBuilder)
					.indices(indexName)
					.types(typeName);
			s_logger.info(searchRequest.toString());

			SearchResponse response = client.search(searchRequest);
			SearchHits searchHits = response.getHits();
			List<Map<String,Object>> results = new ArrayList<>();
			for (SearchHit searchHit : searchHits) {
				results.add(searchHit.getSource());
			}
			aroundResult.setContent(JSON.toJSONString(results));
			aroundResult.setTotalCount(searchHits.getTotalHits());
			return aroundResult;
		} catch (IOException e) {
			e.printStackTrace();
			return aroundResult;
		}
	}
}
