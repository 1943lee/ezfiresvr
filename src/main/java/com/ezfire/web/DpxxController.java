package com.ezfire.web;

import com.ezfire.domain.Dpxx;
import com.ezfire.service.DpxxService;
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
 * Created by lcy on 2018/1/24.
 */
@RestController
@RequestMapping(value = "/dpxx")
@Api(value = "调派信息")
public class DpxxController {
	@Autowired
	DpxxService dpxxService;

	@RequestMapping(value = "",method = RequestMethod.GET,produces = "application/json")
	@ApiImplicitParam(name = "zqbh",value = "灾情编号",required = true,dataType = "String",paramType = "query")
	@ApiOperation(value = "根据灾情编号获取调派信息",notes = "需要灾情编号作为参数",response = Dpxx.class,responseContainer = "List")
	public ResponseEntity<String> getDpxx(@RequestParam(value = "zqbh") String zqbh) {
		String result = dpxxService.getDpxxByZQBH(zqbh);
		if(null == result) {
			return new ResponseEntity<>("{\"message\":\"未找到灾情对应的调派信息\"}", HttpStatus.NOT_FOUND);
		}
		else {
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
	}
}
