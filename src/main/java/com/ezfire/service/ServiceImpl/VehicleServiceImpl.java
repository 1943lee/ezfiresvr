package com.ezfire.service.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ezfire.common.ComConvert;
import com.ezfire.common.ComDefine;
import com.ezfire.common.EsQueryUtils;
import com.ezfire.service.VehicleService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by lcy on 2018/3/9.
 */
@Service
public class VehicleServiceImpl implements VehicleService {
	@Autowired
	StringRedisTemplate stringRedisTemplate;

	@Override
	public String getVehicleBasic(String key, int type, String[] includes) {
		String colName = "";
		if(type == 0) {
			colName = "CLBH";
		}
		else if(type == 1) {
			colName = "CPHM";
		}

		if(colName.isEmpty() || key.isEmpty()) {
			return null;
		}

		return EsQueryUtils.queryById(ComDefine.fire_clxx_read, "clxx", key, colName,
				EsQueryUtils.getFetchInlcudes(includes, null), null);
	}

	@Override
	public String getVehicleBasics(String[] keys, int type, String[] includes) {
		String colName = "";
		if(type == 0) {
			colName = "CLBH";
		}
		else if(type == 1) {
			colName = "CPHM";
		}

		if(colName.isEmpty() || null == keys || keys.length == 0) {
			return null;
		}

		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

		boolQueryBuilder.must().add(QueryBuilders.termsQuery(colName, keys));
		boolQueryBuilder.mustNot().add(QueryBuilders.termQuery("JLZT", "0"));

		// 返回字段处理，由于批量查询，需要得到单位编号作为key
		includes = EsQueryUtils.getFetchInlcudes(includes, null);
		if(includes != null) {
			List<String> includeList = Arrays.asList(includes);
			List<String> tmp = new ArrayList<>(includeList);
			if (!tmp.contains(colName)) {
				tmp.add(colName);
			}
			includes = tmp.stream().toArray(String[]::new);
		}

		final String colNameLambda = colName;
		return EsQueryUtils.queryElasticSearch(boolQueryBuilder, ComDefine.fire_clxx_read, "clxx",
				EsQueryUtils.getFetchInlcudes(includes, null), null, 0, keys.length,
				SortBuilders.scoreSort(),
				(searchHits -> EsQueryUtils.getMapResults(searchHits,
						(map) -> ComConvert.toString(map.get(colNameLambda)))));
	}

	@Override
	public String getVehicleStatus(String[] keys) {
		List<String> outRedis = stringRedisTemplate.opsForValue()
				.multiGet(Arrays.stream(keys)
						.map(key-> ComDefine.redisVehicleStatusPrefix + key)
						.collect(Collectors.toList()));
		JSONObject res = new JSONObject();
		for(int i = 0; i < keys.length; i++) {
			if(null == outRedis.get(i)) continue;
			res.put(keys[i], JSON.parseObject(outRedis.get(i).replace("^", " ")));
		}

		return res.toJSONString();
	}

	@Override
	public String getVehicleLocation(String[] keys) {
		List<String> outRedis = stringRedisTemplate.opsForValue()
				.multiGet(Arrays.stream(keys)
						.map(key-> ComDefine.redisVehicleLocationPrefix + key)
						.collect(Collectors.toList()));
		JSONObject res = new JSONObject();
		for(int i = 0; i < keys.length; i++) {
			if(null == outRedis.get(i)) continue;
			res.put(keys[i], JSON.parseObject(outRedis.get(i).replace("^", " ")));
		}

		return res.toJSONString();
	}
}
