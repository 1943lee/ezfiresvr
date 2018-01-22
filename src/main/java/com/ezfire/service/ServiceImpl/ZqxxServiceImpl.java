package com.ezfire.service.ServiceImpl;

import com.alibaba.fastjson.JSON;
import com.ezfire.common.ComDefine;
import com.ezfire.common.ESClient;
import com.ezfire.service.ZqxxService;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by lcy on 2018/1/21.
 */
@Service
public class ZqxxServiceImpl implements ZqxxService {
	private static Logger s_logger = LoggerFactory.getLogger(ZqxxServiceImpl.class);
	private static RestHighLevelClient client = ESClient.getHightClient();

	@Override
	public String getZqxxByZQBH(String zqbh) {
		if(null == zqbh || zqbh.isEmpty()) {
			return  null;
		}

		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		boolQueryBuilder.must().add(QueryBuilders.termQuery("ZQBH", zqbh));
		boolQueryBuilder.mustNot().add(QueryBuilders.termQuery("JLZT", "0"));
		searchSourceBuilder.query(boolQueryBuilder)
				.timeout(ComDefine.elasticTimeOut)
				.size(1);

		SearchRequest searchRequest = new SearchRequest()
				.source(searchSourceBuilder)
				.indices(ComDefine.fire_zqxx_read)
				.types("zqxx");
		s_logger.info(searchRequest.toString());
		try {
			SearchResponse response = client.search(searchRequest);
			SearchHits searchHits = response.getHits();
			if(searchHits.getTotalHits() < 1) {
				return null;
			}
			else {
				return JSON.toJSONString(searchHits.getAt(0).getSource());
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
