package com.ezfire.web;

import com.ezfire.common.ComMethod;
import com.ezfire.domain.Dpxx;
import com.ezfire.domain.Zqxx;
import com.ezfire.service.DpxxService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
	@ApiImplicitParams({@ApiImplicitParam(name = "zqbh",value = "灾情编号",required = true,dataType = "String",paramType = "query"),
			@ApiImplicitParam(name="includes",value="返回字段，数组形式，逗号隔开",dataType="String",paramType="query")})
	@ApiOperation(value = "根据灾情编号获取调派信息",notes = "需要灾情编号作为参数",response = Dpxx.class,responseContainer = "List")
	public ResponseEntity<String> getDpxx(@RequestParam(value = "zqbh") String zqbh,
										  @RequestParam(required = false) String[] includes) {
		String result = dpxxService.getDpxxByZQBH(zqbh, includes);
		if(null == result) {
			return new ResponseEntity<>("{\"message\":\"未找到灾情对应的调派信息\"}", HttpStatus.NOT_FOUND);
		}
		else {
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/relatedZqxx",method = RequestMethod.GET,produces = "application/json")
	@ApiImplicitParams({@ApiImplicitParam(name = "key", value = "key，车辆编号或者车牌号码", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "type", value = "类型，0为车辆编号，1为车牌号码", defaultValue = "0", paramType = "query", dataType = "int"),
			@ApiImplicitParam(name="includes",value="返回字段，数组形式，逗号隔开",dataType="String",paramType="query")})
	@ApiOperation(value = "根据车辆编号或车牌号码获取灾情基本信息",response = Zqxx.class,
			notes = "默认返回更新时间最近的灾情，没有则为空；需传入编号和类型，类型0为车辆编号，1为车牌号码")
	public ResponseEntity<String> getDpxxZqbhByClbhOrCphm(@RequestParam(value = "key") String key,
														  @RequestParam(defaultValue = "0") int type,
														  @RequestParam(required = false) String[] includes) {
		String result = dpxxService.getDpxxZqbhByClbhOrCphm(ComMethod.encodeStr(key), type, includes);
		if(null == result) {
			return new ResponseEntity<>("{\"message\":\"未找到对应的灾情信息\"}", HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
	}
}
