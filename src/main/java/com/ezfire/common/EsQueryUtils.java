package com.ezfire.common;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lcy on 2018/1/24.
 */
public class EsQueryUtils {
	private static Logger s_logger = LoggerFactory.getLogger(EsQueryUtils.class);
	private static RestHighLevelClient client = ESClient.getHightClient();

	/**
	 * 根据id查找唯一文档信息
	 * @param index
	 * @param type
	 * @param id
	 * @param idColumn
	 * @return
	 */
	public static String queryAllById(String index, String type, String id, String idColumn) {
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		boolQueryBuilder.must().add(QueryBuilders.termQuery(idColumn, id));
		boolQueryBuilder.mustNot().add(QueryBuilders.termQuery("JLZT", "0"));

		searchSourceBuilder.query(boolQueryBuilder)
				.timeout(ComDefine.elasticTimeOut)
				.size(1);

		SearchRequest searchRequest = new SearchRequest()
				.source(searchSourceBuilder)
				.indices(index)
				.types(type);
		s_logger.info(searchRequest.toString());
		try {
			SearchResponse response = client.search(searchRequest);
			SearchHits searchHits = response.getHits();
			if(searchHits.getHits().length == 1) {
				return JSON.toJSONString(searchHits.getAt(0).getSource());
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}

	/**
	 * 指定queryBuilder查询，返回json数组
	 * @param index
	 * @param type
	 * @param mustQueryBuilder
	 * @return
	 */
	public static String queryListByQueryBuilder(String index, String type, QueryBuilder mustQueryBuilder) {
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		boolQueryBuilder.must().add(mustQueryBuilder);
		boolQueryBuilder.mustNot().add(QueryBuilders.termQuery("JLZT", "0"));

		searchSourceBuilder.query(boolQueryBuilder)
				.timeout(ComDefine.elasticTimeOut)
				.size(ComDefine.elasticMaxSearchSize);

		SearchRequest searchRequest = new SearchRequest()
				.source(searchSourceBuilder)
				.indices(index)
				.types(type);
		s_logger.info(searchRequest.toString());
		return getListResults(searchRequest);
	}

	/**
	 * 获取查询结果集list，并进行json序列化
	 * @param searchRequest
	 * @return
	 */
	public static String getListResults(SearchRequest searchRequest) {
		try {
			SearchResponse response = client.search(searchRequest);
			SearchHits searchHits = response.getHits();
			List<Map<String,Object>> results = new ArrayList<>();
			for (SearchHit searchHit : searchHits) {
				results.add(searchHit.getSource());
			}
			return JSON.toJSONString(results);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
