package com.ezfire.web;

import com.ezfire.common.ComConvert;
import com.ezfire.service.ShldService;
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
 * Created by lcy on 2018/1/25.
 */
@RestController
@Api(value = "社会联动信息")
@RequestMapping(value = "/shld")
public class ShldController {
	@Autowired
	ShldService shldService;

	@RequestMapping(value = "/lqbzdw",method = RequestMethod.GET,produces = "application/json")
	@ApiImplicitParam(name = "xzqhnbbm",value = "行政区划内部编码",required = true,dataType = "String",paramType = "query")
	@ApiOperation(value = "获取联勤保障单位信息",notes = "参数为行政区划内部编码")
	public ResponseEntity<String> getLqbzdw(@RequestParam String xzqhnbbm,
											@RequestParam(defaultValue = "0") int from,
											@RequestParam(defaultValue = "50") int size) {
		String result = shldService.getLqbzdwByXzqhnbbm(xzqhnbbm, from, size);
		if(null == result) {
			return new ResponseEntity<>("{\"message\":\"未找到联勤保障单位信息\"}", HttpStatus.NOT_FOUND);
		}
		else {
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/yjlddw",method = RequestMethod.GET,produces = "application/json")
	@ApiImplicitParam(name = "xzqhnbbm",value = "行政区划内部编码",required = true,dataType = "String",paramType = "query")
	@ApiOperation(value = "获取应急联动单位信息",notes = "参数为行政区划内部编码")
	public ResponseEntity<String> getYjlddw(@RequestParam String xzqhnbbm,
											@RequestParam(defaultValue = "0") int from,
											@RequestParam(defaultValue = "50") int size) {
		String result = shldService.getYjlddwByXzqhnbbm(xzqhnbbm, from, size);
		if(null == result) {
			return new ResponseEntity<>("{\"message\":\"未找到应急联动信息\"}", HttpStatus.NOT_FOUND);
		}
		else {
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/mhjyzj",method = RequestMethod.GET,produces = "application/json")
	@ApiImplicitParams({@ApiImplicitParam(name = "xzqhnbbm", value = "行政区划内部编码", required = true, dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "jgjb",value = "机构级别，”1“消防局，”2“总队，”3“支队",required = true,dataType = "Integer",paramType = "query")})
	@ApiOperation(value = "获取灭火救援专家信息",notes = "根据行政区域内部编码和机构级别获取专家信息，机构级别支持3种，‘1’表示‘消防局’，‘2’表示总队，‘3’表示支队")
	public ResponseEntity<String> getMhjyzj(@RequestParam String xzqhnbbm,
											@RequestParam String jgjb,
											@RequestParam(defaultValue = "0") int from,
											@RequestParam(defaultValue = "50") int size) {
		String result = null;
		if(jgjb.equals("1") || jgjb.equals("2") || jgjb.equals("3")) {
			result = shldService.getMhjyzjByXzqhnbbmAndXzjb(xzqhnbbm, ComConvert.toInteger(jgjb,0), from, size);
		}

		if(null == result) {
			return new ResponseEntity<>("{\"message\":\"未找到灭火救援专家信息\"}", HttpStatus.NOT_FOUND);
		}
		else {
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
	}
}
