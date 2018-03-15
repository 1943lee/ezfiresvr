package com.ezfire.service.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.ezfire.Application;
import com.ezfire.dao.FireDangerDao;
import com.ezfire.domain.FireDangerSimple;
import com.ezfire.service.FireDangerService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lcy on 2018/2/2.
 */
@Service
public class FireDangerServiceImpl implements FireDangerService {
	private static FireDangerDao fireDangerDao = Application.myContext.getBean("fireDangerDao", FireDangerDao.class);

	@Override
	public String getAll(int from,int size,String nbbm,String zwm,String ywm,String fzs) {
		Map<String,Object> condition = new HashMap<>();
		condition.put("nbbm",nbbm);
		if(zwm != null && !zwm.isEmpty()) {
			condition.put("zwm", zwm.trim());
		}
		if(ywm != null && !ywm.isEmpty()) {
			condition.put("ywm", ywm.trim());
		}
		if(fzs != null && !fzs.isEmpty()) {
			condition.put("fzs", fzs.trim());
		}

		condition.put("from", from);
		condition.put("to", from + size);

		List<FireDangerSimple> fireDangers = fireDangerDao.getAll(condition);
		return JSON.toJSONString(fireDangers);
	}

	@Override
	public String getDictionary() {
		return JSON.toJSONString(fireDangerDao.getDictionary());
	}

	@Override
	public String getDetail(String bh) {
		return JSON.toJSONString(fireDangerDao.getDetail(bh));
	}
}
