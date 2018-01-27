package com.ezfire.web;

import com.ezfire.common.ComConvert;
import com.ezfire.common.ComDefine;
import com.ezfire.domain.AroundResource;
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
@Api(value = "周边资源",description = "查询指定坐标周边资源信息")
public class AroundController {
	@Autowired
	AroundService aroundService;

	@RequestMapping(value = "/xfsy/{syfl}/{longitude:.+}/{latitude:.+}",method = RequestMethod.GET,produces = "application/json")
	@ApiOperation(value = "查询指定坐标周边水源",notes = "根据水源分类获取资源，all表示全部，xhs消火栓，xfsc消防水池，xfsh消防水鹤，qsmt取水码头，trsy天然水源，返回结果根据距离由近及远排序",
			response = AroundResource.class, responseContainer = "List")
	@ApiImplicitParams({@ApiImplicitParam(name = "syfl", paramType = "path", dataType = "String", required = true, value = "水源分类", allowableValues = "all,xhs,xfsc,xfsh,qsmt,trsy"),
			@ApiImplicitParam(name = "radius", value = "半径，单位米", defaultValue = "500", dataType = "Double", paramType = "query"),
			@ApiImplicitParam(name = "size", value = "结果集最大size，不超过10000", defaultValue = "1000", dataType = "int", paramType = "query")})
	public ResponseEntity<String> getXfsyAround(@PathVariable ComDefine.fireWaterSourceCode syfl, @PathVariable String longitude, @PathVariable String latitude,
											   @RequestParam(value = "radius", defaultValue = "500") double radius,
											   @RequestParam(value = "size", defaultValue = "1000") int size) {
		String result = aroundService.getWaterAround(syfl, ComConvert.toDouble(longitude, 0.0), ComConvert.toDouble(latitude, 0.0), radius, size);
		if(null == result) {
			return new ResponseEntity<>("{\"message\":\"输入不合法\"}", HttpStatus.BAD_REQUEST);
		}
		else {
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/bwmb/{mbfl}/{longitude:.+}/{latitude:.+}",method = RequestMethod.GET,produces = "application/json")
	@ApiOperation(value = "查询指定坐标周边保卫目标",notes = "{mbfl}对象类别，00：全部分类，01：重点单位(防火)，02：重点单位(灭火)，03：建筑信息，04：油气管线（废弃），05：公路隧道（废弃），06：石化单位，07：核电站，08：水电站水库，返回结果根据距离由近及远排序",
			response = AroundResource.class, responseContainer = "List")
	@ApiImplicitParams({@ApiImplicitParam(name = "mbfl", paramType = "path", dataType = "String", required = true, value = "目标分类", allowableValues = "00,01,02,03,06,07,08"),
			@ApiImplicitParam(name = "radius", value = "半径，单位米", defaultValue = "500", dataType = "Double", paramType = "query"),
			@ApiImplicitParam(name = "size", value = "结果集最大size，不超过10000", defaultValue = "1000", dataType = "int", paramType = "query")})
	public ResponseEntity<String> getBwmbAround(@PathVariable String mbfl, @PathVariable double longitude, @PathVariable double latitude,
											   @RequestParam(value = "radius", defaultValue = "500") double radius,
											   @RequestParam(value = "size", defaultValue = "1000") int size) {
		String result = aroundService.getBwmbAround(mbfl, ComConvert.toDouble(longitude, 0.0), ComConvert.toDouble(latitude, 0.0), radius, size);
		if(null == result) {
			return new ResponseEntity<>("{\"message\":\"输入不合法\"}", HttpStatus.BAD_REQUEST);
		}
		else {
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
	}
}
