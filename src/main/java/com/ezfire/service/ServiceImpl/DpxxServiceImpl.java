package com.ezfire.service.ServiceImpl;

import com.ezfire.common.ComDefine;
import com.ezfire.common.ComMethod;
import com.ezfire.common.ESClient;
import com.ezfire.common.EsQueryUtils;
import com.ezfire.domain.Dpxx;
import com.ezfire.service.DpxxService;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by lcy on 2018/1/24.
 */
@Service
public class DpxxServiceImpl implements DpxxService{
	private static Logger s_logger = LoggerFactory.getLogger(ZqxxServiceImpl.class);
	private static RestHighLevelClient client = ESClient.getHightClient();

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
				.sort("FSSJ", SortOrder.ASC)
				.fetchSource(ComMethod.getBeanFields(Dpxx.class), null);

		SearchRequest searchRequest = new SearchRequest()
				.source(searchSourceBuilder)
				.indices(ComDefine.fire_dpxx_read)
				.types("dpxx");
		s_logger.info(searchRequest.toString());
		return EsQueryUtils.getListResults(searchRequest);
	}
}
