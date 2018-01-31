package com.ezfire.dao;

import com.ezfire.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lcy on 2018/1/31.
 */
@Repository
public interface UserDao {
	/**
	 * 获取全部用户信息
	 */
	List<User> getAllUsers();

	/**
	 * 通过登录名获取用户信息
	 */
	User getUserByDlm(String dlm);
}
