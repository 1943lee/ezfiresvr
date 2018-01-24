package com.ezfire.web;

import com.ezfire.domain.Zqxx;
import com.ezfire.service.ZqxxService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	@RequestMapping(value = "/search",method = RequestMethod.GET,produces = "application/json")
	@ApiOperation(value = "查询灾情信息", response = Zqxx.class, responseContainer = "List")
	@ApiImplicitParams({@ApiImplicitParam(name = "xfjgnbbm", value = "内部编码,默认为空", paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "from", value = "from,默认0", defaultValue = "0", paramType = "query", dataType = "int"),
			@ApiImplicitParam(name = "size", value = "size,默认1000", defaultValue = "1000", paramType = "query", dataType = "int")})
	public ResponseEntity<String> getZqxxByCondition(@RequestParam(defaultValue = "") String xfjgnbbm,
													 @RequestParam(defaultValue = "0") int from,
													 @RequestParam(defaultValue = "1000") int size) {
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
}
