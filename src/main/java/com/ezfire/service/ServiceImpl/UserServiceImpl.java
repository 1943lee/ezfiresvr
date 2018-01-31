package com.ezfire.service.ServiceImpl;

import com.alibaba.fastjson.JSON;
import com.ezfire.Application;
import com.ezfire.dao.UserDao;
import com.ezfire.domain.User;
import com.ezfire.service.UserService;
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
}
