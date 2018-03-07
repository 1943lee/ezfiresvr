package com.ezfire.service.ServiceImpl;

import com.alibaba.fastjson.JSON;
import com.ezfire.Application;
import com.ezfire.common.ComDefine;
import com.ezfire.common.ComMethod;
import com.ezfire.common.EsQueryUtils;
import com.ezfire.dao.UserDao;
import com.ezfire.domain.User;
import com.ezfire.domain.UserWeChat;
import com.ezfire.service.UserService;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.springframework.stereotype.Service;

/**
 * Created by lcy on 2018/1/31.
 */
@Service
public class UserServiceImpl implements UserService{
	private static UserDao userDao = Application.myContext.getBean("userDao", UserDao.class);

	@Override
	public String getAllUsers() {
		return JSON.toJSONString(userDao.getAllUsers());
	}

	@Override
	public String getUserByDlm(String dlm) {
		User user = userDao.getUserByDlm(dlm);
		if(null == user)
			return null;
		else
			return JSON.toJSONString(user);
	}

	@Override
	public String getUserFromWeChatOrg(String orgId, String userId) {
		if(orgId.isEmpty() || userId.isEmpty()) return null;

		String qywxId = orgId + ":" + userId;

		QueryBuilder queryBuilder = QueryBuilders.termQuery("QYWX",qywxId);

		return EsQueryUtils.queryElasticSearch(queryBuilder, ComDefine.fire_ryxx_read, "ryxx",
				ComMethod.getBeanFields(UserWeChat.class), null, 0, 1,
				SortBuilders.scoreSort(), EsQueryUtils::getSingleResult);
	}
}
