package com.ezfire.web;

import com.ezfire.common.ComMethod;
import com.ezfire.service.VehicleService;
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
 * Created by lcy on 2018/3/9.
 */
@RestController
@RequestMapping(value = "/vehicle")
@Api(value = "车辆信息")
public class VehicleController {
	@Autowired
	VehicleService vehicleService;

	@RequestMapping(value = "/info",method = RequestMethod.GET,produces = "application/json")
	@ApiImplicitParams({@ApiImplicitParam(name = "key", value = "key，车辆编号或者车牌号码", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "type", value = "类型，0为车辆编号，1为车牌号码，默认0", defaultValue = "0", paramType = "query", dataType = "int")})
	@ApiOperation(value = "根据车辆编号或车牌号码获取车辆基本信息",
			notes = "类型参数不传入时，默认为0，即按车辆编号查询")
	public ResponseEntity<String> getBasicVehicleInfo(@RequestParam(value = "key") String key,
													  @RequestParam(defaultValue = "0") int type) {
		String result = vehicleService.getVehicleBasic(ComMethod.encodeStr(key), type);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@RequestMapping(value = "/infos",method = RequestMethod.GET,produces = "application/json")
	@ApiImplicitParams({@ApiImplicitParam(name = "key", value = "key，车辆编号或者车牌号码,为数组，逗号隔开", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "type", value = "类型，0为车辆编号，1为车牌号码，默认0", defaultValue = "0", paramType = "query", dataType = "int")})
	@ApiOperation(value = "批量根据车辆编号或车牌号码获取车辆基本信息",
			notes = "类型参数不传入时，默认为0，即按车辆编号查询")
	public ResponseEntity<String> getBasicVehicleInfos(@RequestParam(value = "key") String[] keys,
													  @RequestParam(defaultValue = "0") int type) {
		String result = vehicleService.getVehicleBasics(ComMethod.encodeStrs(keys), type);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@RequestMapping(value = "/status",method = RequestMethod.GET,produces = "application/json")
	@ApiImplicitParam(name = "keys", value = "keys，车辆编号,为数组，逗号隔开", required = true,
			paramType = "query", dataType = "String")
	@ApiOperation(value = "批量根据车辆编号获取车辆状态",
			notes = "返回值为list，内部为key-value结构，key为车辆编号")
	public ResponseEntity<String> getBasicVehicleStatus(@RequestParam String[] keys) {
		String result = vehicleService.getVehicleStatus(keys);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
