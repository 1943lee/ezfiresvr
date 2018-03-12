package com.ezfire.web;

import com.ezfire.domain.Xfjg;
import com.ezfire.service.XfjgService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by lcy on 2018/1/22.
 */
@RestController
@RequestMapping(value = "/xfjg")
@Api(value = "消防机构")
public class XfjgController {
	@Autowired
	XfjgService xfjgService;

	@RequestMapping(value = "/nextLevel/{nbbm:.+}", method = RequestMethod.GET, produces = "application/json")
	@ApiOperation(value = "根据机构内部编码查询其下级机构", notes = "只包含下一级机构", response = Xfjg.class, responseContainer = "List")
	@ApiImplicitParam(name = "nbbm", value = "内部编码", paramType = "path", dataType = "String", required = true)
	public ResponseEntity<String> getXfjgUnderByInternelCode(@PathVariable String nbbm) {
		String result = xfjgService.getXfjgs(nbbm);
		if(null == result) {
			return new ResponseEntity<>("{\"message\":\"未找到对应的下级机构\"}", HttpStatus.NOT_FOUND);
		}
		else {
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/{dwbh}",method = RequestMethod.GET,produces = "application/json")
	@ApiOperation(value = "根据dwbh获取对应消防机构信息",notes = "根据主键id查询",response = Xfjg.class)
	@ApiImplicitParam(name = "dwbh",value = "单位编号",paramType = "path",dataType = "String",required = true)
	public ResponseEntity<String> getXfjgByDwbh(@PathVariable String dwbh) {
		String result = xfjgService.getXfjgById(dwbh);
		if(null == result) {
			return new ResponseEntity<>("{\"message\":\"未找到对应消防机构\"}", HttpStatus.NOT_FOUND);
		}
		else {
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/ids",method = RequestMethod.GET,produces = "application/json")
	@ApiOperation(value = "批量根据dwbh获取对应消防机构信息,参数为数组",notes = "根据主键id查询，参数为数组，返回值为key-value",response = Xfjg.class)
	@ApiImplicitParam(name = "dwbhs",value = "单位编号数组",paramType = "query",dataType = "String",required = true)
	public ResponseEntity<String> getXfjgByDwbhs(@RequestParam String[] dwbhs) {
		String result = xfjgService.getXfjgByIds(dwbhs);
		if(null == result) {
			return new ResponseEntity<>("{\"message\":\"未找到对应消防机构\"}", HttpStatus.NOT_FOUND);
		}
		else {
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
	}
}
