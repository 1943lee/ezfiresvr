package com.ezfire.web;

import com.ezfire.service.XfjgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lcy on 2018/1/22.
 */
@RestController
@RequestMapping(value = "/xfjg")
@Api(description = "消防机构信息")
public class XfjgController {
	@Autowired
	XfjgService xfjgService;

	@RequestMapping(value = "/listXfjg/{nbbm:.+}", method = RequestMethod.GET, produces = "application/json")
	@ApiOperation(value = "根据机构内部编码查询所有下级机构")
	@ApiImplicitParam(name = "nbbm", value = "内部编码", paramType = "path", dataType = "String")
	public ResponseEntity<String> getXfjgUnderByInternelCode(@PathVariable String nbbm) {
		String result = xfjgService.getXfjgs(nbbm);
		if(null == result) {
			return new ResponseEntity<>("{\"message\":\"输入不合法\"}", HttpStatus.BAD_REQUEST);
		}
		else {
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
	}
}
