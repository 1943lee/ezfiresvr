package com.ezfire.service.serviceImpl;

import com.ezfire.common.ComConvert;
import com.ezfire.common.ComDefine;
import com.ezfire.common.ComMethod;
import com.ezfire.common.EsQueryUtils;
import com.ezfire.domain.Wsxx;
import com.ezfire.service.WsxxService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * Created by lcy on 2018/2/28.
 */
@Service
public class WsxxServiceImpl implements WsxxService {

	@Override
	public String getWsxxByConditions(Map<String,Object> conditions) {
		if(conditions == null) return "";

		int from = ComConvert.toInteger(conditions.get("from"), 0);
		int size = ComConvert.toInteger(conditions.get("size"), 50);

		BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//1.zqbh
		String zqbh = conditions.containsKey("zqbh") ? conditions.get("zqbh").toString() : "";
		if(!zqbh.isEmpty()) boolQueryBuilder.must().add(QueryBuilders.termQuery("ZQBH",zqbh));
		//2.时间范围，时间以更新时间GXSJ为准
		String kssj = conditions.containsKey("kssj") ?
				(ComMethod.isValidDate(conditions.get("kssj").toString(),dateFormat) ? conditions.get("kssj").toString() : "") : "";
		String jssj = conditions.containsKey("jssj") ?
				(ComMethod.isValidDate(conditions.get("jssj").toString(),dateFormat) ? conditions.get("jssj").toString() : "") : "";
		if(!kssj.isEmpty() || !jssj.isEmpty()) {
			RangeQueryBuilder rangeQueryBuilder = new RangeQueryBuilder("GXSJ");
			if(!kssj.isEmpty()) rangeQueryBuilder.gte(kssj);
			if(!jssj.isEmpty()) rangeQueryBuilder.lte(jssj);

			boolQueryBuilder.must().add(rangeQueryBuilder);
		}

		//3.反馈机构内部编码，指定查看某个单位及以下的文书信息
		String fkjgnbbm = conditions.containsKey("nbbm") ? conditions.get("nbbm").toString() : "";
		if(!fkjgnbbm.isEmpty()) boolQueryBuilder.must().add(QueryBuilders.prefixQuery("FKJG.XFJGNBBM",fkjgnbbm));
		//4.反馈机构编号，指定查看某个单位的文书信息
		String fkjgjgbh = conditions.containsKey("jgbh") ? conditions.get("jgbh").toString() : "";
		if(!fkjgjgbh.isEmpty()) boolQueryBuilder.must().add(QueryBuilders.termQuery("FKJG.XFJGBH",fkjgnbbm));

		boolQueryBuilder.mustNot().add(QueryBuilders.termQuery("JLZT","0"));

		//5.返回字段
		String[] includes = conditions.containsKey("includes") ? (String[]) conditions.get("includes") : null;

		return EsQueryUtils.queryElasticSearch(boolQueryBuilder, ComDefine.fire_wsxx_read, "wsxx",
				EsQueryUtils.getFetchInlcudes(includes, Wsxx.class), null, from, size,
				SortBuilders.fieldSort("GXSJ").order(SortOrder.DESC), EsQueryUtils::getListResults);
	}
}
