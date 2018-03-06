package com.ezfire.web;

import com.ezfire.domain.Zqzl;
import com.ezfire.service.ZqzlService;
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

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lcy on 2018/3/5.
 */
@RestController
@RequestMapping(value = "/zqzl")
@Api(value = "灾情指令信息")
public class ZqzlController {
	@Autowired
	ZqzlService zqzlService;

	@RequestMapping(value = "/zaiqing", method = RequestMethod.GET, produces = "application/json")
	@ApiOperation(value = "根据灾情编号获取灾情指令信息",response = Zqzl.class,responseContainer = "List",
			notes = "必选参数为灾情编号，返回结果按指令发送时间降序排列；支持根据时间范围筛选，根据指令类型筛选(1.本辖区指挥指令，2.跨辖区调度指令)，根据信息类型筛选(1.文本,2.语音,3.图片,4.视频,5.文档,9.其他)")
	@ApiImplicitParams({@ApiImplicitParam(name = "zqbh", value = "灾情编号", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "kssj",value = "开始时间",dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "jssj",value = "结束时间",dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "xxlx",value = "信息类型，可选参数见notes说明",dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "zllx",value = "指令类型，1.本辖区指挥指令，2.跨辖区调度指令",dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "from", value = "from,默认0", defaultValue = "0", paramType = "query", dataType = "int"),
			@ApiImplicitParam(name = "size", value = "size,默认50", defaultValue = "50", paramType = "query", dataType = "int")
			})
	public ResponseEntity<String> getWsxxByZqbh(@RequestParam String zqbh,
												@RequestParam(required = false) String kssj,
												@RequestParam(required = false) String jssj,
												@RequestParam(required = false) String xxlx,
												@RequestParam(required = false) String zllx,
												@RequestParam(defaultValue = "0") int from,
												@RequestParam(defaultValue = "50") int size) {
		String res = "";
		if(null == zqbh || zqbh.trim().isEmpty()) {
			return new ResponseEntity<>(res, HttpStatus.OK);
		}

		Map<String,Object> params = new HashMap<>();
		//必选参数，灾情编号
		params.put("zqbh",zqbh);
		//可选参数
		if(kssj != null && !kssj.isEmpty()) params.put("kssj",kssj);
		if(jssj != null && !jssj.isEmpty()) params.put("jssj",jssj);
		if(xxlx != null && !xxlx.isEmpty()) params.put("xllx",xxlx);
		if(zllx != null && !zllx.isEmpty()) params.put("zllx",zllx);
		params.put("from", from);
		params.put("size", size);

		res = zqzlService.getZqxxByConditions(params);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
}
