package com.ezfire.service.ServiceImpl;

import com.ezfire.common.ComConvert;
import com.ezfire.common.ComDefine;
import com.ezfire.common.ComMethod;
import com.ezfire.common.EsQueryUtils;
import com.ezfire.domain.Xfjg;
import com.ezfire.service.XfjgService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;

/**
 * Created by lcy on 2018/1/22.
 */
@Service
public class XfjgServiceImpl implements XfjgService {

	@Override
	public String getXfjgs(String nbbm) {
		if(null == nbbm || nbbm.isEmpty()) {
			return null;
		}

		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		boolQueryBuilder.must().add(QueryBuilders.prefixQuery("DWNBBM", nbbm));
		boolQueryBuilder.mustNot().add(QueryBuilders.termQuery("JLZT", "0"));

		int xzjb = nbbm.split("\\.").length;
		boolQueryBuilder.must().add(QueryBuilders.termQuery("DWJB", xzjb - 1 + ""));

		SortBuilder[] sortBuilders = new SortBuilder[] {
				SortBuilders.fieldSort("DWJB").order(SortOrder.ASC),
				SortBuilders.fieldSort("SZDXZQH.XZQHBH").order(SortOrder.ASC),
		};
		return EsQueryUtils.queryElasticSearch(boolQueryBuilder, ComDefine.fire_xfdw_read, "xfdw",
				ComMethod.getBeanFields(Xfjg.class), null, 0, ComDefine.elasticMaxSearchSize,
				sortBuilders, EsQueryUtils::getListResults);
	}

	@Override
	public String getXfjgById(String dwbh) {
		return EsQueryUtils.queryById(ComDefine.fire_xfdw_read, "xfdw", dwbh, "DWBH",
				ComMethod.getBeanFields(Xfjg.class), null);
	}

	@Override
	public String getXfjgByIds(String[] dwbhs) {
		if(dwbhs == null || dwbhs.length == 0) return null;

		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

		boolQueryBuilder.must().add(QueryBuilders.termsQuery("DWBH", dwbhs));
		boolQueryBuilder.mustNot().add(QueryBuilders.termQuery("JLZT", "0"));

		return EsQueryUtils.queryElasticSearch(boolQueryBuilder, ComDefine.fire_xfdw_read, "xfdw",
				ComMethod.getBeanFields(Xfjg.class), null, 0, dwbhs.length,
				SortBuilders.scoreSort(),
				(searchHits -> EsQueryUtils.getMapResults(searchHits,
						(map)-> ComConvert.toString(map.get("DWBH")))));
	}
}
