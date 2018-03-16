package com.ezfire.common;

import com.ezfire.Application;
import com.ezfire.dao.DictionaryDao;
import com.ezfire.domain.Dictionary;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
 * Created by lcy on 2018/3/16.
 */
@Component
@Order(1)
public class ComCache implements CommandLineRunner{
	private HashMap<String, HashMap<String, Dictionary>> mComCache;
	private static ComCache comCache;

	@Override
	public void run(String... strings) throws Exception {
		comCache = new ComCache();
		comCache.initCache();
	}

	private void initCache() {
		if(null == mComCache) {
			mComCache = new HashMap<>();
		}
		DictionaryDao dictionaryDao = Application.myContext.getBean("dictionaryDao", DictionaryDao.class);
		// 装备类型字典项
		List<Dictionary> vehicleType = dictionaryDao
				.getDictionary(ComDefine.CacheNameDefine.fire_dictionary_vehicle_type.getValue());

		HashMap<String, Dictionary> vehicleTypeMap = new HashMap<>();
		for (Dictionary dictionary : vehicleType) {
			vehicleTypeMap.put(dictionary.getZdbh(), dictionary);
		}
		mComCache.put(ComDefine.CacheNameDefine.fire_dictionary_vehicle_type.getValue(), vehicleTypeMap);
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
		return null == mComCache ? null : mComCache.get(type.getValue());
	}
}
