package com.ezfire.dao;

import com.ezfire.domain.Dictionary;
import com.ezfire.domain.FireDanger;
import com.ezfire.domain.FireDangerSimple;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by lcy on 2018/2/2.
 */
@Repository
public interface FireDangerDao {
	List<FireDangerSimple> getAll(Map<String, Object> condition);

	List<Dictionary> getDictionary();

	FireDanger getDetail(String bh);
}
