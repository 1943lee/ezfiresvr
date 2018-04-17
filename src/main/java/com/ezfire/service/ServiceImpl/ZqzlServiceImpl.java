package com.ezfire.service.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.ezfire.common.ComConvert;
import com.ezfire.common.ComDefine;
import com.ezfire.common.ComMethod;
import com.ezfire.common.EsQueryUtils;
import com.ezfire.domain.Zqzl;
import com.ezfire.service.ZqzlService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lcy on 2018/3/5.
 */
@Service
public class ZqzlServiceImpl implements ZqzlService {
	@Override
	public String getZqxxByConditions(Map<String, Object> conditions) {
		if(conditions == null) return "";

		int from = ComConvert.toInteger(conditions.get("from"), 0);
		int size = ComConvert.toInteger(conditions.get("size"), 50);

		BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//1.zqbh
		String zqbh = conditions.containsKey("zqbh") ? conditions.get("zqbh").toString() : "";
		if(!zqbh.isEmpty()) boolQueryBuilder.must().add(QueryBuilders.termQuery("ZQBH",zqbh));
		//2.时间范围，时间以更新时间FSSJ为准
		String kssj = conditions.containsKey("kssj") ?
				(ComMethod.isValidDate(conditions.get("kssj").toString(),dateFormat) ? conditions.get("kssj").toString() : "") : "";
		String jssj = conditions.containsKey("jssj") ?
				(ComMethod.isValidDate(conditions.get("jssj").toString(),dateFormat) ? conditions.get("jssj").toString() : "") : "";
		if(!kssj.isEmpty() || !jssj.isEmpty()) {
			RangeQueryBuilder rangeQueryBuilder = new RangeQueryBuilder("FSSJ");
			if(!kssj.isEmpty()) rangeQueryBuilder.gte(kssj);
			if(!jssj.isEmpty()) rangeQueryBuilder.lte(jssj);

			boolQueryBuilder.must().add(rangeQueryBuilder);
		}

		//3.信息类型
		String xxlx = conditions.containsKey("xxlx") ? conditions.get("xxlx").toString() : "";
		if(!xxlx.isEmpty()) boolQueryBuilder.must().add(QueryBuilders.termQuery("XXLX",xxlx));

		//4.指令类型
		String zllx = conditions.containsKey("zllx") ? conditions.get("zllx").toString() : "";
		if(!zllx.isEmpty()) boolQueryBuilder.must().add(QueryBuilders.termQuery("ZLLX",zllx));

		boolQueryBuilder.mustNot().add(QueryBuilders.termQuery("JLZT","0"));

		//5.返回字段
		String[] includes = conditions.containsKey("includes") ? (String[]) conditions.get("includes") : null;

		boolQueryBuilder.must().add(QueryBuilders.termQuery("SFZD","1"));
		SearchHits topHits = (SearchHits) EsQueryUtils.queryCoreMethod(boolQueryBuilder, ComDefine.fire_zqzl_read, "zqzl",
				EsQueryUtils.getFetchInlcudes(includes, Zqzl.class), null, from, size,
				new SortBuilder[] {
					SortBuilders.fieldSort("FSSJ").order(SortOrder.DESC),
				}, ComDefine.elasticTimeOut,(searchHits -> searchHits));

		List<Map<String, Object>> resultList = new ArrayList<>(size);
		for(SearchHit searchHit : topHits) {
			resultList.add(searchHit.getSource());
		}

		if(topHits.getTotalHits() < size) {
			boolQueryBuilder.must().remove(boolQueryBuilder.must().size() - 1);
			boolQueryBuilder.mustNot().add(QueryBuilders.termQuery("SFZD", "1"));
			SearchHits normalHits = (SearchHits) EsQueryUtils.queryCoreMethod(boolQueryBuilder,
					ComDefine.fire_zqzl_read, "zqzl", EsQueryUtils.getFetchInlcudes(includes, Zqzl.class),
					null, from, size, new SortBuilder[]{SortBuilders.fieldSort("FSSJ").order(SortOrder.DESC),},
					ComDefine.elasticTimeOut, (searchHits -> searchHits));

			for(SearchHit searchHit : normalHits) {
				Map<String,Object> map = searchHit.getSource();
				if(!map.containsKey("SFZD")) {
					map.put("SFZD", "0");
				}
				resultList.add(map);
				if(resultList.size() >= size)
					break;
			}
		}

		return JSON.toJSONString(resultList);
	}
}
