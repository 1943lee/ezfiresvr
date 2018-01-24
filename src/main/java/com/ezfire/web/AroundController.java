package com.ezfire.web;

import com.ezfire.common.ComConvert;
import com.ezfire.common.ComDefine;
import com.ezfire.domain.AroundResources;
import com.ezfire.service.AroundService;
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
	AroundService aroundService;

	@RequestMapping(value = "/xfsy/{syfl}/{longitude:.+}/{latitude:.+}",method = RequestMethod.GET,produces = "application/json")
	@ApiOperation(value = "查询指定坐标周边水源",notes = "根据水源分类获取资源，all表示全部，xhs消火栓，xfsc消防水池，xfsh消防水鹤，qsmt取水码头，trsy天然水源\n结果集为以水源分类为key的map",
			response = AroundResources.class)
	@ApiImplicitParams({@ApiImplicitParam(name = "syfl", paramType = "path", dataType = "String", required = true, value = "水源分类", allowableValues = "all,xhs,xfsc,xfsh,qsmt,trsy"),
			@ApiImplicitParam(name = "radius", value = "半径，单位米", defaultValue = "500", dataType = "Double", paramType = "query"),
			@ApiImplicitParam(name = "size", value = "结果集最大size，不超过10000", defaultValue = "1000", dataType = "int", paramType = "query")})
	public ResponseEntity<String> getXhsAround(@PathVariable ComDefine.fireWaterSourceCode syfl, @PathVariable String longitude, @PathVariable String latitude,
											   @RequestParam(value = "radius", defaultValue = "500") double radius,
											   @RequestParam(value = "size", defaultValue = "1000") int size) {
		String result = aroundService.getAround(syfl, ComConvert.toDouble(longitude, 0.0), ComConvert.toDouble(latitude, 0.0), radius, size);
		if(null == result) {
			return new ResponseEntity<>("{\"message\":\"输入不合法\"}", HttpStatus.BAD_REQUEST);
		}
		else {
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
	}
}
