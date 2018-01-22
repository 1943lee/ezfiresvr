package com.ezfire.web;

import com.ezfire.service.ZqxxService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lcy on 2018/1/21.
 */
@RestController
@RequestMapping(value = "/zqxx")
@Api(description = "灾情信息")
public class ZqxxController {
	private static Logger s_logger = LoggerFactory.getLogger(ZqxxController.class);

	@Autowired
	private ZqxxService zqxxService;

	@ApiOperation(value = "根据灾情编号获取灾情基本信息")
	@RequestMapping(value = "/{zqbh}",method = RequestMethod.GET,produces = "application/json")
	public ResponseEntity<String> getZqxxById(@PathVariable String zqbh) {
		String zqxx = zqxxService.getZqxxByZQBH(zqbh);
		if(null != zqxx && !zqxx.isEmpty()) {
			return new ResponseEntity<>(zqxx, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(zqxx, HttpStatus.NOT_FOUND);
		}
	}
}
