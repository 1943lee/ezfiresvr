package com.ezfire.service.ServiceImpl;

import com.ezfire.common.*;
import com.ezfire.domain.Zqxx;
import com.ezfire.service.ZqxxService;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

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

		return EsQueryUtils.queryAllById(ComDefine.fire_zqxx_read,"zqxx",zqbh,"ZQBH");
	}

	@Override
	public String getZqxxByCondition(Map<String, Object> condition) {
		if(null == condition || condition.isEmpty()) {
			return  null;
		}

		String nbbm = ComConvert.toString(condition.get("xfjgnbbm"));
		int from = ComConvert.toInteger(condition.get("from"), 0);
		int size = ComConvert.toInteger(condition.get("size"), 1000);

		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		if(!nbbm.isEmpty()) {
			boolQueryBuilder.must().add(QueryBuilders.prefixQuery("SZDXFJG.XFJGNBBM", nbbm));
		}
		boolQueryBuilder.mustNot().add(QueryBuilders.termQuery("JLZT", "0"));
		searchSourceBuilder.query(boolQueryBuilder)
				.timeout(ComDefine.elasticTimeOut)
				.from(from)
				.size(size)
				.fetchSource(ComMethod.getBeanFields(Zqxx.class),null);

		SearchRequest searchRequest = new SearchRequest()
				.source(searchSourceBuilder)
				.indices(ComDefine.fire_zqxx_read)
				.types("zqxx");
		s_logger.info(searchRequest.toString());

		return EsQueryUtils.getListResults(searchRequest);
	}
}
