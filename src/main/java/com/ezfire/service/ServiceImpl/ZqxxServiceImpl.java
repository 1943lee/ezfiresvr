package com.ezfire.service.ServiceImpl;

import com.alibaba.fastjson.JSON;
import com.ezfire.common.*;
import com.ezfire.domain.AroundResource;
import com.ezfire.domain.RestfulParams.AlarmCondition;
import com.ezfire.domain.Zqxx;
import com.ezfire.service.ZqxxService;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.geo.builders.ShapeBuilders;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lcy on 2018/1/21.
 */
@Service
public class ZqxxServiceImpl implements ZqxxService {
	private static Logger s_logger = LoggerFactory.getLogger(ZqxxServiceImpl.class);

	@Override
	public String getZqxxByZQBH(String zqbh) {
		if(null == zqbh || zqbh.isEmpty()) {
			return  null;
		}

		return EsQueryUtils.queryAllById(ComDefine.fire_zqxx_read,"zqxx",zqbh,"ZQBH");
	}

	@Override
	public String getZqxxByCondition(Map<String, Object> condition) {
		if(null == condition || condition.isEmpty()) {
			return  null;
		}

		String nbbm = ComConvert.toString(condition.get("xfjgnbbm"));
		int from = ComConvert.toInteger(condition.get("from"), 0);
		int size = ComConvert.toInteger(condition.get("size"), 50);
		// 是否只查询已结案的灾情
		boolean caseNotClosed = ComConvert.toBoolean(condition.get("notClosed"), true);

		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		if(!nbbm.isEmpty()) {
			boolQueryBuilder.must().add(QueryBuilders.prefixQuery("SZDXFJG.XFJGNBBM", nbbm));
		}
		if(caseNotClosed) {
			boolQueryBuilder.mustNot().add(QueryBuilders.termsQuery("ZQZT.ID", new String[] {"10","11","12"}));
		}
		boolQueryBuilder.mustNot().add(QueryBuilders.termQuery("JLZT", "0"));
		searchSourceBuilder.query(boolQueryBuilder)
				.timeout(ComDefine.elasticTimeOut)
				.from(from)
				.size(size)
				.fetchSource(ComMethod.getBeanFields(Zqxx.class),null)
				.sort("LASJ", SortOrder.DESC);

		SearchRequest searchRequest = new SearchRequest()
				.source(searchSourceBuilder)
				.indices(ComDefine.fire_zqxx_read)
				.types("zqxx");
		s_logger.info(searchRequest.toString());

		return EsQueryUtils.getListResults(searchRequest);
	}

	@Override
	public String getNearestZqxx(double longitude, double latitude, double radius, int dateRange) {
		// 检查坐标是否合法
		if(!ComMethod.isValidPoint(longitude, latitude)) {
			return null;
		}

		try {
			SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
			BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
			boolQueryBuilder.mustNot().add(QueryBuilders.termQuery("JLZT", "0"));
			// 周边条件
			boolQueryBuilder.must().add(QueryBuilders.geoShapeQuery(ComDefine.esGeoShapeColumn, ShapeBuilders.newCircleBuilder().center(longitude, latitude).radius(String.valueOf(radius) + "m")));
			// 时间条件
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String nowDate = sdf.format(new Date());
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MINUTE, - dateRange);
			String rangeStart = sdf.format(calendar.getTime());
			boolQueryBuilder.must().add(QueryBuilders.rangeQuery("LASJ").gte(rangeStart).lte(nowDate));

			searchSourceBuilder.query(boolQueryBuilder)
					.timeout(ComDefine.elasticTimeOut)
					.size(ComDefine.elasticMaxSearchSize)
					.fetchSource(ComMethod.getBeanFields(Zqxx.class),null);

			SearchRequest searchRequest = new SearchRequest().source(searchSourceBuilder).indices(ComDefine.fire_zqxx_read).types("zqxx");
			s_logger.info(searchRequest.toString());

			List<AroundResource> aroundResultList = new ArrayList<>();
			RestHighLevelClient client = ESClient.getHightClient();
			SearchResponse response = client.search(searchRequest);
			for (SearchHit searchHit : response.getHits()) {
				AroundResource aroundResult = new AroundResource();
				Map<String,Object> source = searchHit.getSource();
				double jd = source.containsKey("JD") ? ComConvert.toDouble(source.get("JD"), 0.0) : 0.0;
				double wd = source.containsKey("WD") ? ComConvert.toDouble(source.get("WD"), 0.0) : 0.0;
				double distance = ComMethod.getSphericalDistance(longitude,latitude,jd,wd);
				// 二次校验
				if(distance > radius) {
					continue;
				}
				aroundResult.setContent(JSON.toJSONString(source));
				aroundResult.setDistance(distance);

				aroundResultList.add(aroundResult);
			}

			if(aroundResultList.size() == 0) {
				return "";
			}

			// 排序，取最近的灾情信息
			aroundResultList.sort(Comparator.comparingDouble(AroundResource::getDistance));

			return aroundResultList.get(0).getContent();
		}
		catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String getZqxxSearch(AlarmCondition conditions) {
		//1. 灾情编号，可能为数组
		List<String> zqbhs = new ArrayList<>();
		if(conditions.getZqbh() == null) {

		}
		else if(conditions.getZqbh().getClass().equals(ArrayList.class)) {
			zqbhs.addAll((Collection<? extends String>) conditions.getZqbh());
		}
		else {
			zqbhs.add(conditions.getZqbh().toString());
		}
		//3.includes
		String[] includes = conditions.getIncludes();

		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		if(!zqbhs.isEmpty()) {
			boolQueryBuilder.must().add(QueryBuilders.termsQuery("ZQBH", zqbhs));
		}
		boolQueryBuilder.mustNot().add(QueryBuilders.termQuery("JLZT", "0"));
		searchSourceBuilder.query(boolQueryBuilder)
				.timeout(ComDefine.elasticTimeOut)
				.size(ComDefine.elasticMaxSearchSize)
				.sort("LASJ", SortOrder.DESC);
		if(null != includes && includes.length > 0) {
			searchSourceBuilder.fetchSource(includes,null);
		}

		SearchRequest searchRequest = new SearchRequest()
				.source(searchSourceBuilder)
				.indices(ComDefine.fire_zqxx_read)
				.types("zqxx");
		s_logger.info(searchRequest.toString());

		try {
			SearchResponse searchResponse = ESClient.getHightClient().search(searchRequest);
			Map<String,Object> result = new HashMap<>();
			for(SearchHit searchHit : searchResponse.getHits()) {
				result.put(searchHit.getId(),searchHit.getSource());
			}
			return JSON.toJSONString(result);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
