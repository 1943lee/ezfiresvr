package com.ezfire.service.ServiceImpl;

import com.ezfire.common.ComDefine;
import com.ezfire.common.ComMethod;
import com.ezfire.common.EsQueryUtils;
import com.ezfire.domain.Xfjg;
import com.ezfire.service.XfjgService;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by lcy on 2018/1/22.
 */
@Service
public class XfjgServiceImpl implements XfjgService {
	private static Logger s_logger = LoggerFactory.getLogger(XfjgServiceImpl.class);

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

		return EsQueryUtils.getListResults(searchRequest);
	}
}
