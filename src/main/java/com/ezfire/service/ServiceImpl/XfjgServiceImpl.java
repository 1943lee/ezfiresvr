package com.ezfire.service.ServiceImpl;

import com.alibaba.fastjson.JSON;
import com.ezfire.common.ComDefine;
import com.ezfire.common.ComMethod;
import com.ezfire.common.ESClient;
import com.ezfire.domain.Xfjg;
import com.ezfire.service.XfjgService;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
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
public class XfjgServiceImpl implements XfjgService {
	private static Logger s_logger = LoggerFactory.getLogger(XfjgServiceImpl.class);
	private static RestHighLevelClient client = ESClient.getHightClient();

	@Override
	public String getXfjgs(String nbbm) {
		if(null == nbbm || nbbm.isEmpty()) {
			return null;
		}

		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		boolQueryBuilder.must().add(QueryBuilders.prefixQuery("DWNBBM", nbbm));
		boolQueryBuilder.mustNot().add(QueryBuilders.termQuery("JLZT", "0"));

		int xzjb = nbbm.split("\\.").length;
		if(xzjb == 1) {
			boolQueryBuilder.must().add(QueryBuilders.prefixQuery("DWJB", "01"));
		}
		else if(xzjb == 2) {
			boolQueryBuilder.must().add(QueryBuilders.prefixQuery("DWJB", "02"));
		}
		else if(xzjb == 3) {
			boolQueryBuilder.must().add(QueryBuilders.prefixQuery("DWJB", "03"));
		}
		else if(xzjb == 4) {
			boolQueryBuilder.must().add(QueryBuilders.prefixQuery("DWJB", "05"));
		}
		else if(xzjb == 5) {
			boolQueryBuilder.must().add(QueryBuilders.prefixQuery("DWJB", "09"));
		}

		searchSourceBuilder.query(boolQueryBuilder)
				.timeout(ComDefine.elasticTimeOut)
				.size(ComDefine.elasticMaxSearchSize)
				.sort("DWJB", SortOrder.ASC)
				.fetchSource(ComMethod.getBeanFields(Xfjg.class), null);

		SearchRequest searchRequest = new SearchRequest()
				.source(searchSourceBuilder)
				.indices(ComDefine.fire_xfdw_read)
				.types("xfdw");
		s_logger.info(searchRequest.toString());

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
		}

		return null;
	}
}
