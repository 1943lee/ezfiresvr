package com.ezfire.common;

import com.ezfire.Application;
import com.ezfire.dao.DictionaryDao;
import com.ezfire.domain.Dictionary;
import com.ezfire.domain.comDomains.SZDXFJG;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortBuilders;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by lcy on 2018/3/16.
 */
@Component
@Order(1)
public class ComCache implements CommandLineRunner{
	/**
	 * 字典项缓存
	 */
	private HashMap<String, HashMap<String, Dictionary>> mComDictionaryCache;
	/**
	 * 消防机构名称、简称缓存
	 */
	private HashMap<String, SZDXFJG> mComXfjgNameCache;
	/**
	 * 单例对象
	 */
	private static ComCache comCache;

	@Override
	public void run(String... strings) throws Exception {
		comCache = new ComCache();
		comCache.initDictionaryCache();
		comCache.initXfjgNameCache();
	}

	private void initDictionaryCache() {
		if(null == mComDictionaryCache) {
			mComDictionaryCache = new HashMap<>();
		}
		DictionaryDao dictionaryDao = Application.myContext.getBean("dictionaryDao", DictionaryDao.class);
		// 装备类型字典项
		List<String> typeCodeList = Arrays.asList(ComDefine.CacheNameDefine.fire_dictionary_vehicle_type.getValue(),
				ComDefine.CacheNameDefine.fire_dictionary_fireDanger_type.getValue());
		List<Dictionary> dictionaryList = dictionaryDao.getDictionary(typeCodeList);

		HashMap<String, Dictionary> vehicleTypeMap = (HashMap<String, Dictionary>)dictionaryList.stream()
				.filter(dictionary ->
						dictionary.getZdlx().equals(ComDefine.CacheNameDefine.fire_dictionary_vehicle_type.getValue()))
				.collect(Collectors.toMap(Dictionary::getZdbh, Function.identity()));
		HashMap<String, Dictionary> fireDangerTypeMap = (HashMap<String, Dictionary>) dictionaryList.stream()
				.filter(dictionary ->
						dictionary.getZdlx().equals(ComDefine.CacheNameDefine.fire_dictionary_fireDanger_type.getValue()))
				.collect(Collectors.toMap(Dictionary::getZdbh, Function.identity()));

		mComDictionaryCache.put(ComDefine.CacheNameDefine.fire_dictionary_vehicle_type.getValue(), vehicleTypeMap);
		mComDictionaryCache.put(ComDefine.CacheNameDefine.fire_dictionary_fireDanger_type.getValue(), fireDangerTypeMap);
	}

	private void initXfjgNameCache() {
		if(null == mComXfjgNameCache) {
			mComXfjgNameCache = new HashMap<>();
		}

		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		boolQueryBuilder.mustNot().add(QueryBuilders.termQuery("JLZT", "0"));
		EsQueryUtils.queryElasticSearch(boolQueryBuilder, ComDefine.fire_xfdw_read, "xfdw",
				new String[] {"DWBH", "DWMC", "DWNBBM", "DWSX"}, null, 0, ComDefine.elasticMaxSearchSize,
				SortBuilders.scoreSort(), (this::parseXfjgName));
	}

	private String parseXfjgName(SearchHits searchHits) {
		for (SearchHit searchHit : searchHits) {
			Map<String, Object> map = searchHit.getSource();
			String dwbh = ComConvert.toString(map.get("DWBH"));

			SZDXFJG szdxfjg = new SZDXFJG();
			szdxfjg.setXfjgbh(dwbh);
			szdxfjg.setXfjgmc(ComConvert.toString(map.get("DWMC")));
			szdxfjg.setXfjgnbbm(ComConvert.toString(map.get("DWNBBM")));
			szdxfjg.setXfjgjc(ComConvert.toString(map.get("DWSX")));

			mComXfjgNameCache.put(dwbh, szdxfjg);
		}
		return "";
	}

	/**
	 * 单例模式
	 * @return
	 */
	public static ComCache getInstance() {
		return comCache;
	}

	/**
	 * 根据字典大类获取字典
	 * @param type
	 * @return
	 */
	public HashMap<String, Dictionary> getDictionaryByType(ComDefine.CacheNameDefine type) {
		return null == mComDictionaryCache ? null : mComDictionaryCache.get(type.getValue());
	}

	/**
	 * 获取消防机构名称缓存，用于获取简称
	 * @return
	 */
	public HashMap<String, SZDXFJG> getmComXfjgNameCache() {
		return mComXfjgNameCache;
	}
}
