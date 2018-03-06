package com.ezfire.service.ServiceImpl;

import com.ezfire.common.ComConvert;
import com.ezfire.common.ComDefine;
import com.ezfire.common.ComMethod;
import com.ezfire.common.EsQueryUtils;
import com.ezfire.domain.Zqzl;
import com.ezfire.service.ZqzlService;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * Created by lcy on 2018/3/5.
 */
@Service
public class ZqzlServiceImpl implements ZqzlService {
	private static Logger s_logger = LoggerFactory.getLogger(ZqzlService.class);

	@Override
	public String getZqxxByConditions(Map<String, Object> conditidons) {
		if(conditidons == null) return "";

		int from = ComConvert.toInteger(conditidons.get("from"), 0);
		int size = ComConvert.toInteger(conditidons.get("size"), 50);

		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//1.zqbh
		String zqbh = conditidons.containsKey("zqbh") ? conditidons.get("zqbh").toString() : "";
		if(!zqbh.isEmpty()) boolQueryBuilder.must().add(QueryBuilders.termQuery("ZQBH",zqbh));
		//2.时间范围，时间以更新时间FSSJ为准
		String kssj = conditidons.containsKey("kssj") ?
				(ComMethod.isValidDate(conditidons.get("kssj").toString(),dateFormat) ? conditidons.get("kssj").toString() : "") : "";
		String jssj = conditidons.containsKey("jssj") ?
				(ComMethod.isValidDate(conditidons.get("jssj").toString(),dateFormat) ? conditidons.get("jssj").toString() : "") : "";
		if(!kssj.isEmpty() || !jssj.isEmpty()) {
			RangeQueryBuilder rangeQueryBuilder = new RangeQueryBuilder("FSSJ");
			if(!kssj.isEmpty()) rangeQueryBuilder.gte(kssj);
			if(!jssj.isEmpty()) rangeQueryBuilder.lte(jssj);

			boolQueryBuilder.must().add(rangeQueryBuilder);
		}

		//3.信息类型
		String xxlx = conditidons.containsKey("xxlx") ? conditidons.get("xxlx").toString() : "";
		if(!xxlx.isEmpty()) boolQueryBuilder.must().add(QueryBuilders.termQuery("XXLX",xxlx));

		//4.指令类型
		String zllx = conditidons.containsKey("zllx") ? conditidons.get("zllx").toString() : "";
		if(!zllx.isEmpty()) boolQueryBuilder.must().add(QueryBuilders.termQuery("ZLLX",zllx));

		boolQueryBuilder.mustNot().add(QueryBuilders.termQuery("JLZT","0"));

		searchSourceBuilder.query(boolQueryBuilder)
				.timeout(ComDefine.elasticTimeOut)
				.from(from)
				.size(size)
				.sort("FSSJ", SortOrder.DESC)
				.fetchSource(ComMethod.getBeanFields(Zqzl.class), null);

		SearchRequest searchRequest = new SearchRequest()
				.source(searchSourceBuilder)
				.indices(ComDefine.fire_zqzl_read)
				.types("zqzl");
		s_logger.info(searchRequest.toString());

		return EsQueryUtils.getListResults(searchRequest);
	}
}
