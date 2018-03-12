package com.ezfire.service.ServiceImpl;

import com.alibaba.fastjson.JSON;
import com.ezfire.Application;
import com.ezfire.common.ComConvert;
import com.ezfire.common.ComDefine;
import com.ezfire.common.ComMethod;
import com.ezfire.common.EsQueryUtils;
import com.ezfire.dao.UserDao;
import com.ezfire.domain.User;
import com.ezfire.domain.UserWeChat;
import com.ezfire.service.UserService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.springframework.stereotype.Service;

import java.util.Arrays;

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

	@Override
	public String getUserFromWeChatOrgIds(String orgId, String[] userIds) {
		if(orgId.isEmpty() || userIds == null || userIds.length == 0) return null;

		String[] qywxIds = Arrays.stream(userIds).map(s -> (orgId + ":" + s)).toArray(String[]::new);

		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

		boolQueryBuilder.must().add(QueryBuilders.termsQuery("QYWX", qywxIds));
		boolQueryBuilder.mustNot().add(QueryBuilders.termQuery("JLZT", "0"));

		return EsQueryUtils.queryElasticSearch(boolQueryBuilder, ComDefine.fire_ryxx_read, "ryxx",
				ComMethod.getBeanFields(UserWeChat.class), null, 0, qywxIds.length,
				SortBuilders.scoreSort(),
				(searchHits -> EsQueryUtils.getMapResults(searchHits,
						(map)-> ComConvert.toString(map.get("QYWX")).split(":")[1])));
	}

	@Override
	public String getUserByIds(String[] userIds) {
		if(userIds == null || userIds.length == 0) return null;

		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

		boolQueryBuilder.must().add(QueryBuilders.termsQuery("RYBH", userIds));
		boolQueryBuilder.mustNot().add(QueryBuilders.termQuery("JLZT", "0"));

		return EsQueryUtils.queryElasticSearch(boolQueryBuilder, ComDefine.fire_ryxx_read, "ryxx",
				ComMethod.getBeanFields(UserWeChat.class), null, 0, userIds.length,
				SortBuilders.scoreSort(),
				(searchHits -> EsQueryUtils.getMapResults(searchHits,
						(map)-> ComConvert.toString(map.get("RYBH")))));
	}
}
