package com.ezfire.web;

import com.ezfire.domain.User;
import com.ezfire.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lcy on 2018/1/31.
 */
@RestController
@RequestMapping(value = "/user")
@Api(value = "用户信息")
public class UserController {
	@Autowired
	UserService userService;

	@RequestMapping(value = "/all",produces = "application/json",method = RequestMethod.GET)
	@ApiOperation(value = "获取全部人员信息",response = User.class,responseContainer = "List")
	public ResponseEntity<String> getAllUsers() {
		return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
	}

	@RequestMapping(value = "",produces = "application/json",method = RequestMethod.GET)
	@ApiOperation(value = "根据登陆名获取用户信息",response = User.class)
	@ApiImplicitParam(name = "dlm",value = "登录名，即用户id，主键",dataType = "String",paramType = "query")
	public ResponseEntity<String> getUserByDlm(@RequestParam String dlm) {
		String result = userService.getUserByDlm(dlm);
		if(null == result) {
			return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
		}
		else {
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
	}
}
