package com.ezfire.web;

import com.ezfire.domain.RestfulParams.AlarmCondition;
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

import java.util.*;

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
			@ApiImplicitParam(name = "size", value = "size,默认50", defaultValue = "50", paramType = "query", dataType = "int"),
			@ApiImplicitParam(name = "notClosed", value = "notClosed,默认true,表示只查未结案的", defaultValue = "true", paramType = "query", dataType = "boolean"),
			@ApiImplicitParam(name = "onlyStressed", value = "onlyStressed,为true时表示只查突出灾情，默认为false,表示查询全部", defaultValue = "false", paramType = "query", dataType = "boolean"),
			@ApiImplicitParam(name = "userOrgLevel", value = "userOrgLevel,用户所在单位级别，部局为0依次递增,用于查询突出灾情，根据用户不同级别进行查询", paramType = "query", dataType = "int")})
	public ResponseEntity<String> getZqxxByCondition(@RequestParam(defaultValue = "") String xfjgnbbm,
													 @RequestParam(defaultValue = "0") int from,
													 @RequestParam(defaultValue = "50") int size,
													 @RequestParam(defaultValue = "true") boolean notClosed,
													 @RequestParam(defaultValue = "false") boolean onlyStressed,
													 @RequestParam(defaultValue = "-1") int userOrgLevel) {
		Map<String, Object> condition = new HashMap<>();
		condition.put("xfjgnbbm", xfjgnbbm);
		condition.put("from", from);
		condition.put("size", size);
		condition.put("notClosed", notClosed);
		condition.put("onlyStressed", onlyStressed);
		condition.put("userOrgLevel", userOrgLevel);

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

	@RequestMapping(value = "/idsSearch",method = RequestMethod.POST,produces = "application/json",consumes = "application/json")
	@ApiOperation(value = "根据传入条件获取灾情信息",notes = "支持多个灾情编号,支持指定返回字段")
	@ApiImplicitParam(name = "conditions",paramType = "body",dataType = "AlarmCondition")
	public ResponseEntity<String> getZqxxStates(@RequestBody AlarmCondition conditions) {
		String zqxxs = zqxxService.getZqxxSearch(conditions);
		if(null != zqxxs) {
			return new ResponseEntity<>(zqxxs, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>("{\"message\":\"未找到符合条件的灾情信息\"}", HttpStatus.NOT_FOUND);
		}
	}
}
