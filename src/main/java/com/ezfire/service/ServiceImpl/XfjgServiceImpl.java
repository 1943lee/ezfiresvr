package com.ezfire.service.serviceImpl;

import com.ezfire.common.ComConvert;
import com.ezfire.common.ComDefine;
import com.ezfire.common.EsQueryUtils;
import com.ezfire.domain.Xfjg;
import com.ezfire.service.XfjgService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by lcy on 2018/1/22.
 */
@Service
public class XfjgServiceImpl implements XfjgService {

	@Override
	public String getXfjgs(String nbbm, String[] includes) {
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
				EsQueryUtils.getFetchInlcudes(includes, Xfjg.class), null, 0, ComDefine.elasticMaxSearchSize,
				sortBuilders, EsQueryUtils::getListResults);
	}

	@Override
	public String getXfjgById(String dwbh, String[] includes) {
		return EsQueryUtils.queryById(ComDefine.fire_xfdw_read, "xfdw", dwbh, "DWBH",
				EsQueryUtils.getFetchInlcudes(includes, Xfjg.class), null);
	}

	@Override
	public String getXfjgByIds(String[] dwbhs, String[] includes) {
		if(dwbhs == null || dwbhs.length == 0) return null;

		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

		boolQueryBuilder.must().add(QueryBuilders.termsQuery("DWBH", dwbhs));
		boolQueryBuilder.mustNot().add(QueryBuilders.termQuery("JLZT", "0"));

		// 返回字段处理，由于批量查询，需要得到单位编号作为key
		List<String> includeList = Arrays.asList(EsQueryUtils.getFetchInlcudes(includes, Xfjg.class));
		List<String> tmp = new ArrayList<>(includeList);
		if(!tmp.contains("DWBH")) {
			tmp.add("DWBH");
		}
		includes = tmp.stream().toArray(String[] :: new);

		return EsQueryUtils.queryElasticSearch(boolQueryBuilder, ComDefine.fire_xfdw_read, "xfdw",
				includes, null, 0, dwbhs.length,
				SortBuilders.scoreSort(),
				(searchHits -> EsQueryUtils.getMapResults(searchHits,
						(map)-> ComConvert.toString(map.get("DWBH")))));
	}
}
