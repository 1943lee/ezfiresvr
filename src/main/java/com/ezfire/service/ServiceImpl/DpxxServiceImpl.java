package com.ezfire.service.serviceImpl;

import com.ezfire.common.ComCache;
import com.ezfire.common.ComDefine;
import com.ezfire.common.EsQueryUtils;
import com.ezfire.domain.Dictionary;
import com.ezfire.domain.Dpxx;
import com.ezfire.domain.Zqxx;
import com.ezfire.service.DpxxService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		// 车辆类型字典项缓存，用于处理调派车辆中的车辆名称，根据车辆类型从缓存获取
		HashMap<String, Dictionary> vehicleTypeDict = ComCache.getInstance().getDictionaryByType(ComDefine.CacheNameDefine.fire_dictionary_vehicle_type);

		return EsQueryUtils.queryElasticSearch(boolQueryBuilder, ComDefine.fire_dpxx_read, "dpxx",
				EsQueryUtils.getFetchInlcudes(includes, Dpxx.class), null, 0, ComDefine.elasticMaxSearchSize,
				SortBuilders.fieldSort("FSSJ").order(SortOrder.DESC),
				(searchHits -> EsQueryUtils.getListResults(searchHits,
						(sourceMap -> {
							if(sourceMap.containsKey("DPCL")) {
								List<Map<String,Object>> dpclList = (List<Map<String, Object>>) sourceMap.get("DPCL");
								if(null != dpclList && null != vehicleTypeDict) {
									dpclList.forEach(dpclMap -> dpclMap.put("CLMC", vehicleTypeDict.get(dpclMap.get("CLLX")).getZdmc()));
								}
							}
						}))));
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
