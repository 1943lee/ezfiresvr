package com.ezfire.web;

import com.ezfire.domain.Zqxx;
import com.ezfire.service.ZqxxService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lcy on 2018/1/21.
 */
@RestController
@RequestMapping(value = "/zqxx")
@Api(value = "灾情信息")
public class ZqxxController {
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

	@RequestMapping(value = "/search",method = RequestMethod.GET,produces = "application/json")
	@ApiOperation(value = "查询灾情信息", notes = "按照灾情立案时间降序排列", response = Zqxx.class, responseContainer = "List")
	@ApiImplicitParams({@ApiImplicitParam(name = "xfjgnbbm", value = "内部编码,默认为空", paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "from", value = "from,默认0", defaultValue = "0", paramType = "query", dataType = "int"),
			@ApiImplicitParam(name = "size", value = "size,默认50", defaultValue = "50", paramType = "query", dataType = "int")})
	public ResponseEntity<String> getZqxxByCondition(@RequestParam(defaultValue = "") String xfjgnbbm,
													 @RequestParam(defaultValue = "0") int from,
													 @RequestParam(defaultValue = "50") int size) {
		Map<String, Object> condition = new HashMap<>();
		condition.put("xfjgnbbm", xfjgnbbm);
		condition.put("from", from);
		condition.put("size", size);

		String zqxx = zqxxService.getZqxxByCondition(condition);
		if(null != zqxx && !zqxx.isEmpty()) {
			return new ResponseEntity<>(zqxx, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(zqxx, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/near",method = RequestMethod.GET,produces = "application/json")
	@ApiOperation(value = "查询最近的灾情信息", notes = "提供经纬度（gps坐标）、半径（米）、时间范围（分钟），返回符合条件的最近的灾情信息", response = Zqxx.class)
	@ApiImplicitParams({@ApiImplicitParam(name = "longitude", value = "经度", paramType = "query", dataType = "double"),
			@ApiImplicitParam(name = "latitude", value = "纬度", paramType = "query", dataType = "double"),
			@ApiImplicitParam(name = "radius", value = "半径，米为单位", paramType = "query", dataType = "double"),
			@ApiImplicitParam(name = "dateRange", value = "时间范围，分钟为单位", paramType = "query", dataType = "int")})
	public ResponseEntity<String> getNearestZqxx(@RequestParam double longitude,
												 @RequestParam double latitude,
												 @RequestParam double radius,
												 @RequestParam int dateRange) {
		String zqxx = zqxxService.getNearestZqxx(longitude, latitude, radius, dateRange);
		if(null != zqxx) {
			return new ResponseEntity<>(zqxx, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>("{\"message\":\"输入不合法\"}", HttpStatus.BAD_REQUEST);
		}
	}
}
