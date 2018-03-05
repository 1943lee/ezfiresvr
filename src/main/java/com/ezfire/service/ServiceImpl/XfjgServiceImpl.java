package com.ezfire.service.ServiceImpl;

import com.alibaba.fastjson.JSON;
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
		boolQueryBuilder.must().add(QueryBuilders.termQuery("DWJB", xzjb - 1 + ""));

		searchSourceBuilder.query(boolQueryBuilder)
				.timeout(ComDefine.elasticTimeOut)
				.size(ComDefine.elasticMaxSearchSize)
				.sort("DWJB", SortOrder.ASC)
				.sort("SZDXZQH.XZQHBH", SortOrder.ASC)
				.fetchSource(ComMethod.getBeanFields(Xfjg.class), null);

		SearchRequest searchRequest = new SearchRequest()
				.source(searchSourceBuilder)
				.indices(ComDefine.fire_xfdw_read)
				.types("xfdw");
		s_logger.info(searchRequest.toString());

		return EsQueryUtils.getListResults(searchRequest);
	}

	@Override
	public String getXfjgById(String dwbh) {
		Xfjg xfjg = JSON.parseObject(EsQueryUtils.queryAllById(ComDefine.fire_xfdw_read,"xfdw",dwbh,"DWBH"),Xfjg.class);

		return JSON.toJSONString(xfjg);
	}
}
