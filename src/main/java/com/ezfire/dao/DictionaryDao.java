package com.ezfire.dao;

import com.ezfire.domain.Dictionary;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lcy on 2018/3/16.
 */
@Repository
public interface DictionaryDao {
	/**
	 * 根据字典项获取字典项
	 * @param zdlx 字典类型
	 * @return
	 */
	List<Dictionary> getDictionary(String zdlx);
}
