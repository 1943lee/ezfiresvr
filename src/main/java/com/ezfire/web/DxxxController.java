package com.ezfire.web;

import com.ezfire.service.DxxxService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lcy on 2018/1/24.
 */
@Api(value = "对象信息")
@RestController
@RequestMapping(value = "/dxxx")
public class DxxxController {
	@Autowired
	DxxxService dxxxService;

	@RequestMapping(value="/{dxlx}/{dxbh:.+}",method = RequestMethod.GET,produces = "application/json")
	@ApiOperation(value = "对象信息基础信息",notes = "{dxlx}对象类别，01：重点单位(防火)，02：重点单位(灭火)，03：建筑信息，04：油气管线，05：公路隧道，06：石化单位，07：核电站，08：水电站水库")
	@ApiImplicitParams({@ApiImplicitParam(name = "dxlx",value = "对象类别",paramType = "path",dataType = "String",required = true,allowableValues = "01,02,03,04,05,06,07,08"),
			@ApiImplicitParam(name = "dxbh",value = "对象ID",paramType = "path",dataType = "String",required = true)})
	public ResponseEntity<String> getDxxx(@PathVariable String dxlx,
										  @PathVariable String dxbh) {
		String result = dxxxService.getBasicInfo(dxlx, dxbh);
		if(null == result) {
			return new ResponseEntity<>("{\"message\":\"未找到对应id\"}", HttpStatus.NOT_FOUND);
		}
		else {
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
	}
}
