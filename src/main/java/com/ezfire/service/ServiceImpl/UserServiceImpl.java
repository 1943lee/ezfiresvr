package com.ezfire.service.ServiceImpl;

import com.alibaba.fastjson.JSON;
import com.ezfire.Application;
import com.ezfire.common.ComConvert;
import com.ezfire.common.ComDefine;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
	public String getUserFromWeChatOrg(String orgId, String userId, String[] includes) {
		if(orgId.isEmpty() || userId.isEmpty()) return null;

		String qywxId = orgId + ":" + userId;

		QueryBuilder queryBuilder = QueryBuilders.termQuery("QYWX",qywxId);

		return EsQueryUtils.queryElasticSearch(queryBuilder, ComDefine.fire_ryxx_read, "ryxx",
				EsQueryUtils.getFetchInlcudes(includes, UserWeChat.class), null, 0, 1,
				SortBuilders.scoreSort(), EsQueryUtils::getSingleResult);
	}

	@Override
	public String getUserFromWeChatOrgIds(String orgId, String[] userIds, String[] includes) {
		if(orgId.isEmpty() || userIds == null || userIds.length == 0) return null;

		String[] qywxIds = Arrays.stream(userIds).map(s -> (orgId + ":" + s)).toArray(String[]::new);

		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

		boolQueryBuilder.must().add(QueryBuilders.termsQuery("QYWX", qywxIds));
		boolQueryBuilder.mustNot().add(QueryBuilders.termQuery("JLZT", "0"));

		// 返回字段处理，由于批量查询，需要得到企业微信作为key
		List<String> includeList = Arrays.asList(EsQueryUtils.getFetchInlcudes(includes, UserWeChat.class));
		List<String> tmp = new ArrayList<>(includeList);
		if(!tmp.contains("QYWX")) {
			tmp.add("QYWX");
		}
		includes = tmp.stream().toArray(String[] :: new);

		return EsQueryUtils.queryElasticSearch(boolQueryBuilder, ComDefine.fire_ryxx_read, "ryxx",
				includes, null, 0, qywxIds.length,
				SortBuilders.scoreSort(),
				(searchHits -> EsQueryUtils.getMapResults(searchHits,
						(map)-> ComConvert.toString(map.get("QYWX")).split(":")[1])));
	}

	@Override
	public String getUserByIds(String[] userIds, String[] includes) {
		if(userIds == null || userIds.length == 0) return null;

		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

		boolQueryBuilder.must().add(QueryBuilders.termsQuery("RYBH", userIds));
		boolQueryBuilder.mustNot().add(QueryBuilders.termQuery("JLZT", "0"));

		// 返回字段处理，由于批量查询，需要得到人员编号作为key
		List<String> includeList = Arrays.asList(EsQueryUtils.getFetchInlcudes(includes, UserWeChat.class));
		List<String> tmp = new ArrayList<>(includeList);
		if(!tmp.contains("RYBH")) {
			tmp.add("RYBH");
		}
		includes = tmp.stream().toArray(String[] :: new);

		return EsQueryUtils.queryElasticSearch(boolQueryBuilder, ComDefine.fire_ryxx_read, "ryxx",
				includes, null, 0, userIds.length,
				SortBuilders.scoreSort(),
				(searchHits -> EsQueryUtils.getMapResults(searchHits,
						(map)-> ComConvert.toString(map.get("RYBH")))));
	}
}
