package com.ezfire.web;

import com.ezfire.common.ComConvert;
import com.ezfire.service.XhsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by lcy on 2018/1/22.
 */
@RestController
@RequestMapping(value = "/around")
@Api(description = "查询指定目标周边资源信息")
public class AroundController {
	@Autowired
	XhsService xhsService;

	@RequestMapping(value = "/xhs/{longitude:.+}/{latitude:.+}",method = RequestMethod.GET,produces = "application/json")
	@ApiOperation(value = "查询指定坐标周边消火栓")
	@ApiImplicitParams({@ApiImplicitParam(name = "radius", value = "半径，单位米", defaultValue = "500", dataType = "Double", paramType = "query"),
			@ApiImplicitParam(name = "size", value = "结果集最大size，不超过10000", defaultValue = "1000", dataType = "Interger", paramType = "query")})
	public ResponseEntity<String> getXhsAround(@PathVariable String longitude, @PathVariable String latitude,
											   @RequestParam(value = "radius", defaultValue = "500") double radius,
											   @RequestParam(value = "size", defaultValue = "1000") int size) {
		String result = xhsService.getXhsAround(ComConvert.toDouble(longitude, 0.0), ComConvert.toDouble(latitude, 0.0), radius, size);
		if(null == result) {
			return new ResponseEntity<String>("{\"message\":\"输入不合法\"}", HttpStatus.BAD_REQUEST);
		}
		else {
			return new ResponseEntity<String>(result, HttpStatus.OK);
		}
	}
}
