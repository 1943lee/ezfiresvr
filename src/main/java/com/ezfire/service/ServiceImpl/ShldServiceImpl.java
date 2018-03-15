package com.ezfire.service.serviceImpl;

import com.ezfire.common.ComDefine;
import com.ezfire.common.EsQueryUtils;
import com.ezfire.service.ShldService;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.stereotype.Service;

/**
 * Created by lcy on 2018/1/25.
 */
@Service
public class ShldServiceImpl implements ShldService {
	@Override
	public String getLqbzdwByXzqhnbbm(String nbbm, int from, int size) {
		QueryBuilder queryBuilder = QueryBuilders.prefixQuery("SZDXZQH.XZQHNBBM", nbbm);
		return EsQueryUtils.queryListByQueryBuilder(ComDefine.fire_lqbzdw_read, "lqbzdw", queryBuilder, from, size);
	}

	@Override
	public String getYjlddwByXzqhnbbm(String nbbm, int from, int size) {
		QueryBuilder queryBuilder = QueryBuilders.prefixQuery("SZDXZQH.XZQHNBBM", nbbm);
		return EsQueryUtils.queryListByQueryBuilder(ComDefine.fire_yjlddw_read, "yjlddw", queryBuilder, from, size);
	}

	@Override
	public String getMhjyzjByXzqhnbbmAndXzjb(String nbbm, int xzjb, int from, int size) {
		if(0 == xzjb)
			return null;
		String[] ids = nbbm.split("\\.");
		QueryBuilder queryBuilder = null;
		switch (xzjb) {
			// 消防局
			case 1:
				queryBuilder = QueryBuilders.termQuery("SZDXFJG.XFJGBH", "eb09df352cda4902b24c54dd2b2ce656");
				return EsQueryUtils.queryListByQueryBuilder(ComDefine.fire_mhjyzj_read, "mhjyzj", queryBuilder, from, size);
			// 总队，只查询总队一级，即省
			case 2:
				if(ids.length >= 2) {
					String xzqhbh = ids[1];
					queryBuilder = QueryBuilders.termQuery("SZDXZQH.XZQHBH", xzqhbh);
					return EsQueryUtils.queryListByQueryBuilder(ComDefine.fire_mhjyzj_read, "mhjyzj", queryBuilder, from, size);
				}
				break;
			// 支队，查询支队及以下
			case 3:
				if(ids.length >= 3) {
					String xzqhnbbm = ids[0] + "." + ids[1] + "." + ids[2];
					queryBuilder = QueryBuilders.prefixQuery("SZDXZQH.XZQHNBBM", xzqhnbbm);
					return EsQueryUtils.queryListByQueryBuilder(ComDefine.fire_mhjyzj_read, "mhjyzj", queryBuilder, from, size);
				}
				break;
		}
		return null;
	}
}
