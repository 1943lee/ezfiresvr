package com.ezfire.service.ServiceImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ezfire.common.ComConvert;
import com.ezfire.common.ComDefine;
import com.ezfire.common.ComMethod;
import com.ezfire.common.EsQueryUtils;
import com.ezfire.domain.UserWeChat;
import com.ezfire.service.VehicleService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

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
	public String getVehicleBasic(String key, int type) {
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

		return EsQueryUtils.queryById(ComDefine.fire_clxx_read, "clxx", key, colName, null, null);
	}

	@Override
	public String getVehicleBasics(String[] keys, int type) {
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

		final String colNameLambda = colName;
		return EsQueryUtils.queryElasticSearch(boolQueryBuilder, ComDefine.fire_clxx_read, "clxx",
				ComMethod.getBeanFields(UserWeChat.class), null, 0, keys.length,
				SortBuilders.scoreSort(),
				(searchHits -> EsQueryUtils.getMapResults(searchHits,
						(map) -> ComConvert.toString(map.get(colNameLambda)))));
	}

	@Override
	public String getVehicleStatus(String[] keys) {
		List<String> outRedis = stringRedisTemplate.opsForValue()
				.multiGet(Arrays.stream(keys).
						map(key-> ComDefine.redisVehicleStatusPrefix + key)
						.collect(Collectors.toList()));
		JSONObject res = new JSONObject();
		for(int i = 0; i < keys.length; i++) {
			res.put(keys[i], JSON.parseObject(outRedis.get(i)));
		}

		return res.toJSONString();
	}
}
