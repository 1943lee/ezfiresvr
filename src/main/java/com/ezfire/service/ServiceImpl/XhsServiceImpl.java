package com.ezfire.service.ServiceImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ezfire.common.ComDefine;
import com.ezfire.common.ComMethod;
import com.ezfire.common.ESClient;
import com.ezfire.service.XhsService;
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
import java.util.List;
import java.util.Map;

/**
 * Created by lcy on 2018/1/22.
 */
@Service
public class XhsServiceImpl implements XhsService{
	private static Logger s_logger = LoggerFactory.getLogger(ZqxxServiceImpl.class);
	private static RestHighLevelClient client = ESClient.getHightClient();

	@Override
	public String getXhsAround(double longitude, double latitude, double radius, int size) {
		// 检查坐标是否合法，size是否超出
		if(!ComMethod.isValidPoint(longitude, latitude) || size > 10000) {
			return null;
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
					.indices(ComDefine.fire_xhs_read)
					.types("xhs");
			s_logger.info(searchRequest.toString());

			SearchResponse response = client.search(searchRequest);
			SearchHits searchHits = response.getHits();
			List<Map<String,Object>> results = new ArrayList<>();
			for (SearchHit searchHit : searchHits) {
				results.add(searchHit.getSource());
			}
			return JSON.toJSONString(results, SerializerFeature.BeanToArray);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
