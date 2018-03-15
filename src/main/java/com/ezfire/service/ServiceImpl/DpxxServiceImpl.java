package com.ezfire.service.serviceImpl;

import com.ezfire.common.ComDefine;
import com.ezfire.common.EsQueryUtils;
import com.ezfire.domain.Dpxx;
import com.ezfire.domain.Zqxx;
import com.ezfire.service.DpxxService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;

/**
 * Created by lcy on 2018/1/24.
 */
@Service
public class DpxxServiceImpl implements DpxxService{

	public String getDpxxByZQBH(String zqbh, String[] includes) {
		if(null == zqbh || zqbh.isEmpty()) {
			return  null;
		}

		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		boolQueryBuilder.must().add(QueryBuilders.termQuery("ZQBH", zqbh));
		boolQueryBuilder.mustNot().add(QueryBuilders.termQuery("JLZT", "0"));

		return EsQueryUtils.queryElasticSearch(boolQueryBuilder, ComDefine.fire_dpxx_read, "dpxx",
				EsQueryUtils.getFetchInlcudes(includes, Dpxx.class), null, 0, ComDefine.elasticMaxSearchSize,
				SortBuilders.fieldSort("FSSJ").order(SortOrder.DESC), EsQueryUtils::getListResults);
	}

	@Override
	public String getDpxxZqbhByClbhOrCphm(String key, int type, String[] includes) {
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

		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		boolQueryBuilder.must().add(QueryBuilders.termQuery(colName, key));
		boolQueryBuilder.mustNot().add(QueryBuilders.termQuery("JLZT", "0"));

		String zqbh = EsQueryUtils.queryElasticSearch(boolQueryBuilder, ComDefine.fire_dpxx_read, "dpxx",
				new String[] { "ZQBH" }, null, 0, 1,
				SortBuilders.fieldSort("GXSJ").order(SortOrder.DESC), (searchHits) -> {
					if(searchHits.getTotalHits() >= 1) {
						return searchHits.getAt(0).getSource().get("ZQBH");
					} else {
						return null;
					}
				});

		if(zqbh != null) {
			return EsQueryUtils.queryById(ComDefine.fire_zqxx_read, "zqxx", zqbh, "ZQBH",
					EsQueryUtils.getFetchInlcudes(includes, Zqxx.class), null);
		} else {
			return null;
		}
	}
}
