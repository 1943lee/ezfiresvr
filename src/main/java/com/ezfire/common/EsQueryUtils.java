package com.ezfire.common;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.geo.builders.ShapeBuilders;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

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
	public static String queryById(String index, String type, String id, String idColumn,
								   String[] fetchIncludes, String[] fetchExcludes) {
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		boolQueryBuilder.must().add(QueryBuilders.termQuery(idColumn, id));
		boolQueryBuilder.mustNot().add(QueryBuilders.termQuery("JLZT", "0"));

		return (String)queryCoreMethod(boolQueryBuilder, index, type, fetchIncludes, fetchExcludes,
				0,1,null,ComDefine.elasticTimeOut,
				EsQueryUtils::getSingleResult);
	}

	/**
	 * 指定queryBuilder查询，返回json数组
	 * @param index
	 * @param type
	 * @param mustQueryBuilder
	 * @return
	 */
	public static String queryListByQueryBuilder(String index, String type, QueryBuilder mustQueryBuilder,
												 int from, int size) {
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		boolQueryBuilder.must().add(mustQueryBuilder);
		boolQueryBuilder.mustNot().add(QueryBuilders.termQuery("JLZT", "0"));

		return (String)queryCoreMethod(boolQueryBuilder, index, type, null, null, from, size,
				null, ComDefine.elasticTimeOut, EsQueryUtils::getListResults);
	}

	/**
	 * 查询指定坐标周边资源，返回SearchHits
	 * @param indexName
	 * @param typeName
	 * @param longitude
	 * @param latitude
	 * @param radius
	 * @param size
	 * @return
	 */
	public static SearchHits getAroundResourceHits(String indexName, String typeName, double longitude, double latitude,
												   double radius, int size) {
		try {
			BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
			boolQueryBuilder.mustNot().add(QueryBuilders.termQuery("JLZT", "0"));
			boolQueryBuilder.must().add(QueryBuilders.geoShapeQuery(ComDefine.esGeoShapeColumn,
					ShapeBuilders.newCircleBuilder().center(longitude, latitude).radius(String.valueOf(radius) + "m")));

			return (SearchHits)queryCoreMethod(boolQueryBuilder,indexName,typeName,null,null,0,size,null,ComDefine.elasticTimeOut,
					(searchHits -> searchHits));
		}
		catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取查询结果集list，并进行json序列化
	 * @param searchHits elasticsearch 查询结果
	 * @return
	 */
	public static String getSingleResult(SearchHits searchHits) {
		if(searchHits.getTotalHits() >= 1) {
			return JSON.toJSONString(searchHits.getAt(0).getSource());
		}
		return null;
	}

	/**
	 * 获取查询结果集list，并进行json序列化
	 * @param searchHits elasticsearch 查询结果
	 * @return
	 */
	public static String getListResults(SearchHits searchHits) {
		List<Map<String,Object>> results = new ArrayList<>();
		for (SearchHit searchHit : searchHits) {
			results.add(searchHit.getSource());
		}
		return JSON.toJSONString(results);
	}

	public static String getMapResults(SearchHits searchHits, Function<Map<String, Object>, String> function) {
		Map<String, Map<String, Object>> results = new HashMap();
		for(SearchHit searchHit : searchHits) {
			Map<String, Object> source = searchHit.getSource();
			results.put(function.apply(source), source);
		}
		return JSON.toJSONString(results);
	}

	/**
	 *
	 * @param queryBuilder 查询elastic 使用的query builder
	 * @param index 索引名
	 * @param type 类型名
	 * @param fetchIncludes 返回字段includes，若为空，表示返回全部
	 * @param fetchExcludes 返回字段excludes，若为空，表示不加限制
	 * @param from from
	 * @param size size
	 * @param sortBuilder 排序
	 * @param callback 查询结果回调，参数为{@link SearchHits}
	 * @return
	 */
	public static String queryElasticSearch(QueryBuilder queryBuilder, String index, String type,
												  String[] fetchIncludes, String[] fetchExcludes,
												  int from, int size, SortBuilder sortBuilder,
												  Function<SearchHits, Object> callback) {

		return (String) queryCoreMethod(queryBuilder, index, type,
				fetchIncludes, fetchExcludes,
				from, size,
				null == sortBuilder ? null : new SortBuilder[] { sortBuilder }, ComDefine.elasticTimeOut,
				callback);
	}

	/**
	 *
	 * @param queryBuilder 查询elastic 使用的query builder
	 * @param index 索引名
	 * @param type 类型名
	 * @param fetchIncludes 返回字段includes，若为空，表示返回全部
	 * @param fetchExcludes 返回字段excludes，若为空，表示不加限制
	 * @param from from
	 * @param size size
	 * @param sortBuilders 排序
	 * @param callback 查询结果回调，参数为{@link SearchHits}
	 * @return
	 */
	public static String queryElasticSearch(QueryBuilder queryBuilder, String index, String type,
											String[] fetchIncludes, String[] fetchExcludes,
											int from, int size, SortBuilder[] sortBuilders,
											Function<SearchHits, Object> callback) {

		return (String) queryCoreMethod(queryBuilder, index, type,
				fetchIncludes, fetchExcludes,
				from, size,
				sortBuilders, ComDefine.elasticTimeOut,
				callback);
	}

	/**
	 * 查询elasticsearch核心方法
	 * @param queryBuilder 查询elasticsearch使用的query builder
	 * @param index 索引名
	 * @param type 类型名
	 * @param fetchIncludes 返回字段includes，数组
	 * @param fetchExcludes 返回字段excludes，数组
	 * @param from from
	 * @param size size
	 * @param sortBuilders 排序
	 * @param timeValue 超时参数
	 * @param callback 查询结果回调，参数为{@link SearchHits}
	 */
	private static Object queryCoreMethod(QueryBuilder queryBuilder, String index, String type,
										String[] fetchIncludes, String[] fetchExcludes,
										int from, int size, SortBuilder[] sortBuilders,TimeValue timeValue,
										Function<SearchHits, Object> callback) {
		if(queryBuilder == null) {
			if(callback != null) {
				callback.apply(null);
			}
			return null;
		}
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.fetchSource(fetchIncludes,fetchExcludes)
				.query(queryBuilder)
				.from(from)
				.size(size)
				.timeout(timeValue);
		if(null != sortBuilders) {
			for(SortBuilder sortBuilder : sortBuilders) {
				searchSourceBuilder.sort(sortBuilder);
			}
		}

		SearchRequest searchRequest = new SearchRequest(index).types(type).source(searchSourceBuilder);
		s_logger.info(searchRequest.toString());

		SearchHits res = null;
		try {
			SearchResponse response = client.search(searchRequest);
			res = response.getHits();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if(null != callback) {
			return callback.apply(res);
		}
		return null;
	}
}
