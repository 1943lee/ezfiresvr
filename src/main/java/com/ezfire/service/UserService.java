package com.ezfire.service;

/**
 * Created by lcy on 2018/1/31.
 */
public interface UserService {
	String getAllUsers();

	String getUserByDlm(String dlm);

	String getUserFromWeChatOrg(String orgId, String userId, String[] includes);

	String getUserFromWeChatOrgIds(String orgId, String[] userIds, String[] includes);

	String getUserByIds(String[] userIds, String[] includes);
}
