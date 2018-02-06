package com.ezfire.service.ServiceImpl;

import com.alibaba.fastjson.JSON;
import com.ezfire.common.ComDefine;
import com.ezfire.common.ComMethod;
import com.ezfire.common.ESClient;
import com.ezfire.common.EsQueryUtils;
import com.ezfire.domain.Dpxx;
import com.ezfire.service.DpxxService;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by lcy on 2018/1/24.
 */
@Service
public class DpxxServiceImpl implements DpxxService{
	private static Logger s_logger = LoggerFactory.getLogger(ZqxxServiceImpl.class);

	public String getDpxxByZQBH(String zqbh) {
		if(null == zqbh || zqbh.isEmpty()) {
			return  null;
		}

		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		boolQueryBuilder.must().add(QueryBuilders.termQuery("ZQBH", zqbh));
		boolQueryBuilder.mustNot().add(QueryBuilders.termQuery("JLZT", "0"));

		searchSourceBuilder.query(boolQueryBuilder)
				.timeout(ComDefine.elasticTimeOut)
				.size(ComDefine.elasticMaxSearchSize)
				.sort("FSSJ", SortOrder.DESC)
				.fetchSource(ComMethod.getBeanFields(Dpxx.class), null);

		SearchRequest searchRequest = new SearchRequest()
				.source(searchSourceBuilder)
				.indices(ComDefine.fire_dpxx_read)
				.types("dpxx");
		s_logger.info(searchRequest.toString());
		return EsQueryUtils.getListResults(searchRequest);
	}

	@Override
	public String getDpxxZqbhByClbhOrCphm(String key, int type) {
		String colName = "";
		if(type == 0) {
			colName = "DPCL.CLBH";
		}
		else if(type == 1) {
			colName = "DPCL.CPHM";
		}

		if(colName.isEmpty()) {
			return null;
		}

		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		boolQueryBuilder.must().add(QueryBuilders.termQuery(colName, key));
		boolQueryBuilder.mustNot().add(QueryBuilders.termQuery("JLZT", "0"));

		searchSourceBuilder.query(boolQueryBuilder)
				.timeout(ComDefine.elasticTimeOut)
				.size(1)
				.sort("GXSJ", SortOrder.DESC)
				.fetchSource(new String[] { "ZQBH" }, null);

		SearchRequest searchRequest = new SearchRequest()
				.source(searchSourceBuilder)
				.indices(ComDefine.fire_dpxx_read)
				.types("dpxx");
		s_logger.info(searchRequest.toString());

		try {
			SearchResponse searchResponse = ESClient.getHightClient().search(searchRequest);
			if(searchResponse.getHits().getTotalHits() > 0) {
				return JSON.toJSONString(searchResponse.getHits().getAt(0).getSource());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
}
