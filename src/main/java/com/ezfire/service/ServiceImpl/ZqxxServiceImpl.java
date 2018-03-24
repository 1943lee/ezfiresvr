package com.ezfire.service.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.ezfire.common.*;
import com.ezfire.domain.AroundResource;
import com.ezfire.domain.Zqxx;
import com.ezfire.domain.comDomains.SZDXFJG;
import com.ezfire.domain.restfulParams.AlarmCondition;
import com.ezfire.service.ZqxxService;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.common.geo.builders.ShapeBuilders;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lcy on 2018/1/21.
 */
@Service
public class ZqxxServiceImpl implements ZqxxService {

	@Override
	public String getZqxxByZQBH(String zqbh, String[] includes) {
		if(null == zqbh || zqbh.isEmpty()) {
			return  null;
		}

		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		boolQueryBuilder.must().add(QueryBuilders.termQuery("ZQBH", zqbh));
		boolQueryBuilder.mustNot().add(QueryBuilders.termQuery("JLZT", "0"));

		// 车辆类型字典项缓存，用于处理调派车辆中的车辆名称，根据车辆类型从缓存获取
		HashMap<String, SZDXFJG> xfjgNameCache = ComCache.getInstance().getmComXfjgNameCache();

		return EsQueryUtils.queryElasticSearch(boolQueryBuilder, ComDefine.fire_zqxx_read, "zqxx",
				EsQueryUtils.getFetchInlcudes(includes, Zqxx.class), null, 0, 1,
				SortBuilders.scoreSort(),
				(searchHits -> {
					if(searchHits.getTotalHits() == 0)
						return "";
					SearchHit searchHit = searchHits.getAt(0);
					Map<String,Object> map = searchHit.getSource();
					if(map.containsKey("SZDXFJG")) {
						Map<String,Object> xfjgMap = (Map<String, Object>) map.get("SZDXFJG");
						if(null != xfjgMap) {
							String dwbh = ComConvert.toString(xfjgMap.get("XFJGBH"));
							if(xfjgNameCache.containsKey(dwbh)) {
								xfjgMap.put("XFJGJC", xfjgNameCache.get(dwbh).getXfjgjc());
							}
						}
					}
					return JSON.toJSONString(map);
				}));
	}

	@Override
	public String getZqxxByCondition(Map<String, Object> condition) {
		if(null == condition || condition.isEmpty()) {
			return  null;
		}

		String xfjgnbbm = ComConvert.toString(condition.get("xfjgnbbm"));
		String xfjgflag = ComConvert.toString(condition.get("xfjgflag"));
		int from = ComConvert.toInteger(condition.get("from"), 0);
		int size = ComConvert.toInteger(condition.get("size"), 50);
		// 是否只查询已结案的灾情
		boolean caseNotClosed = ComConvert.toBoolean(condition.get("notClosed"), true);
		// 是否只查询突出灾情
		boolean onlyStressed = ComConvert.toBoolean(condition.get("onlyStressed"), false);
		int userOrgLevel = ComConvert.toInteger(condition.get("userOrgLevel"), -1);

		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		if(!xfjgnbbm.isEmpty()) {
			if(xfjgflag.equals("0")) {
				boolQueryBuilder.must().add(QueryBuilders.prefixQuery("SZDXFJG.XFJGNBBM", xfjgnbbm));
			} else {
				// 根据xfjgnbbm获取单位级别，1为总队
				int level = xfjgnbbm.split("\\.").length - 2;
				BoolQueryBuilder nestedInnerQuery = QueryBuilders.boolQuery();
				nestedInnerQuery.must().add(QueryBuilders.prefixQuery("KQYZYXX.XFJGNBBM", xfjgnbbm));
				nestedInnerQuery.must().add(QueryBuilders.rangeQuery("KQYZYXX.KQYDJ").lte(level).gte(1));
				NestedQueryBuilder nestedQuery = QueryBuilders.nestedQuery("KQYZYXX",nestedInnerQuery, ScoreMode.None);
				if(xfjgflag.equals("1")) {
					boolQueryBuilder.must().add(nestedQuery);
				} else if(xfjgflag.equals("2")) {
					BoolQueryBuilder subQueryBuilder = QueryBuilders.boolQuery();
					subQueryBuilder.should().add(QueryBuilders.prefixQuery("SZDXFJG.XFJGNBBM", xfjgnbbm));
					subQueryBuilder.should().add(nestedQuery);
					boolQueryBuilder.must().add(subQueryBuilder);
				}
			}
		}
		if(caseNotClosed) {
			boolQueryBuilder.mustNot().add(QueryBuilders.termsQuery("ZQZT.ID", "10","11","12"));
		}
		if(onlyStressed && userOrgLevel != -1) {
			boolQueryBuilder.must().add(QueryBuilders.termQuery("TCZQ.LEVEL_" + userOrgLevel, "1"));
		}
		// 默认只查询真警
		boolQueryBuilder.must().add(QueryBuilders.termQuery("ZQBS", "1"));
		// 过滤无效记录
		boolQueryBuilder.mustNot().add(QueryBuilders.termQuery("JLZT", "0"));

		// 获取返回字段
		String[] includes = condition.containsKey("includes") ? (String[]) condition.get("includes") : null;

		return EsQueryUtils.queryElasticSearch(boolQueryBuilder, ComDefine.fire_zqxx_read, "zqxx",
				EsQueryUtils.getFetchInlcudes(includes, Zqxx.class), null, from, size,
				SortBuilders.fieldSort("LASJ").order(SortOrder.DESC), EsQueryUtils::getListResults);
	}

	@Override
	public String getNearestZqxx(double longitude, double latitude, double radius, int dateRange, String[] includes) {
		// 检查坐标是否合法
		if (!ComMethod.isValidPoint(longitude, latitude)) {
			return null;
		}

		try {
			BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
			boolQueryBuilder.mustNot().add(QueryBuilders.termQuery("JLZT", "0"));
			// 默认只查询真警
			boolQueryBuilder.must().add(QueryBuilders.termQuery("ZQBS", "1"));
			// 默认只查询未结案的警情
			boolQueryBuilder.mustNot().add(QueryBuilders.termsQuery("ZQZT.ID", "10","11","12"));
			// 周边条件
			boolQueryBuilder.must().add(QueryBuilders.geoShapeQuery(ComDefine.esGeoShapeColumn, ShapeBuilders.newCircleBuilder().center(longitude, latitude).radius(String.valueOf(radius) + "m")));
			// 时间条件
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String nowDate = sdf.format(new Date());
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MINUTE, -dateRange);
			String rangeStart = sdf.format(calendar.getTime());
			boolQueryBuilder.must().add(QueryBuilders.rangeQuery("LASJ").gte(rangeStart).lte(nowDate));

			// 返回字段处理，由于要计算距离并排序，需默认加上经纬度
			List<String> includeList = Arrays.asList(EsQueryUtils.getFetchInlcudes(includes, Zqxx.class));
			List<String> tmp = new ArrayList<>(includeList);
			if(!tmp.containsAll(Arrays.asList("JD","WD"))) {
				tmp.addAll(Arrays.asList("JD","WD"));
			}
			includes = tmp.stream().toArray(String[] :: new);

			return EsQueryUtils.queryElasticSearch(boolQueryBuilder, ComDefine.fire_zqxx_read, "zqxx",
					includes, null, 0, ComDefine.elasticMaxSearchSize,
					SortBuilders.scoreSort(), (searchHits) -> {
						List<AroundResource> aroundResultList = new ArrayList<>();
						for (SearchHit searchHit : searchHits) {
							AroundResource aroundResult = new AroundResource();
							Map<String, Object> source = searchHit.getSource();
							double jd = source.containsKey("JD") ? ComConvert.toDouble(source.get("JD"), 0.0) : 0.0;
							double wd = source.containsKey("WD") ? ComConvert.toDouble(source.get("WD"), 0.0) : 0.0;
							double distance = ComMethod.getSphericalDistance(longitude, latitude, jd, wd);
							// 二次校验
							if (distance > radius) {
								continue;
							}
							aroundResult.setContent(JSON.toJSONString(source));
							aroundResult.setDistance(distance);

							aroundResultList.add(aroundResult);
						}

						if (aroundResultList.size() == 0) {
							return "";
						}

						// 排序，取最近的灾情信息
						aroundResultList.sort(Comparator.comparingDouble(AroundResource::getDistance));

						return aroundResultList.get(0).getContent();
					});
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String getZqxxSearch(AlarmCondition conditions) {
		//1. 灾情编号，可能为数组
		List<String> zqbhs = new ArrayList<>();
		if(conditions.getZqbh() == null) {

		}
		else if(conditions.getZqbh().getClass().equals(ArrayList.class)) {
			zqbhs.addAll((Collection<? extends String>) conditions.getZqbh());
		}
		else {
			zqbhs.add(conditions.getZqbh().toString());
		}
		//3.includes
		String[] includes = conditions.getIncludes();

		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		if(!zqbhs.isEmpty()) {
			boolQueryBuilder.must().add(QueryBuilders.termsQuery("ZQBH", zqbhs));
		}
		boolQueryBuilder.mustNot().add(QueryBuilders.termQuery("JLZT", "0"));

		return EsQueryUtils.queryElasticSearch(boolQueryBuilder, ComDefine.fire_zqxx_read, "zqxx",
				includes, null, 0, ComDefine.elasticMaxSearchSize,
				SortBuilders.fieldSort("LASJ").order(SortOrder.DESC),
				(searchHits) -> {
					Map<String,Object> result = new HashMap<>();
					for(SearchHit searchHit : searchHits) {
						result.put(searchHit.getId(),searchHit.getSource());
					}
					return JSON.toJSONString(result);
				});
	}
}
