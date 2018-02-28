package com.ezfire.service.ServiceImpl;

import com.ezfire.common.ComDefine;
import com.ezfire.common.ComMethod;
import com.ezfire.common.EsQueryUtils;
import com.ezfire.domain.Wsxx;
import com.ezfire.service.WsxxService;
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
 * Created by lcy on 2018/2/28.
 */
@Service
public class WsxxServiceImpl implements WsxxService {
	private static Logger s_logger = LoggerFactory.getLogger(WsxxServiceImpl.class);

	@Override
	public String getWsxxByConditions(Map<String,Object> conditidons) {
		if(conditidons == null) return "";

		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//1.zqbh
		String zqbh = conditidons.containsKey("zqbh") ? conditidons.get("zqbh").toString() : "";
		if(!zqbh.isEmpty()) boolQueryBuilder.must().add(QueryBuilders.termQuery("ZQBH",zqbh));
		//2.时间范围，时间以更新时间GXSJ为准
		String kssj = conditidons.containsKey("kssj") ?
				(ComMethod.isValidDate(conditidons.get("kssj").toString(),dateFormat) ? conditidons.get("kssj").toString() : "") : "";
		String jssj = conditidons.containsKey("jssj") ?
				(ComMethod.isValidDate(conditidons.get("jssj").toString(),dateFormat) ? conditidons.get("jssj").toString() : "") : "";
		if(!kssj.isEmpty() || !jssj.isEmpty()) {
			RangeQueryBuilder rangeQueryBuilder = new RangeQueryBuilder("GXSJ");
			if(!kssj.isEmpty()) rangeQueryBuilder.gte(kssj);
			if(!jssj.isEmpty()) rangeQueryBuilder.lte(jssj);

			boolQueryBuilder.must().add(rangeQueryBuilder);
		}

		//3.反馈机构内部编码，指定查看某个单位及以下的文书信息
		String fkjgnbbm = conditidons.containsKey("nbbm") ? conditidons.get("nbbm").toString() : "";
		if(!fkjgnbbm.isEmpty()) boolQueryBuilder.must().add(QueryBuilders.prefixQuery("FKJG.XFJGNBBM",fkjgnbbm));
		//4.反馈机构编号，指定查看某个单位的文书信息
		String fkjgjgbh = conditidons.containsKey("jgbh") ? conditidons.get("jgbh").toString() : "";
		if(!fkjgjgbh.isEmpty()) boolQueryBuilder.must().add(QueryBuilders.termQuery("FKJG.XFJGBH",fkjgnbbm));

		boolQueryBuilder.mustNot().add(QueryBuilders.termQuery("JLZT","0"));

		searchSourceBuilder.query(boolQueryBuilder)
				.timeout(ComDefine.elasticTimeOut)
				.size(ComDefine.elasticMaxSearchSize)
				.sort("GXSJ", SortOrder.DESC)
				.fetchSource(ComMethod.getBeanFields(Wsxx.class), null);

		SearchRequest searchRequest = new SearchRequest()
				.source(searchSourceBuilder)
				.indices(ComDefine.fire_wsxx_read)
				.types("wsxx");
		s_logger.info(searchRequest.toString());

		return EsQueryUtils.getListResults(searchRequest);
	}
}
