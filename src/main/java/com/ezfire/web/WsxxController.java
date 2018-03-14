package com.ezfire.web;

import com.ezfire.domain.Wsxx;
import com.ezfire.service.WsxxService;
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
 * Created by lcy on 2018/2/28.
 */
@RestController
@RequestMapping(value = "/wsxx")
@Api(value = "文书信息")
public class WsxxController {
	@Autowired
	WsxxService wsxxService;

	@RequestMapping(value = "/zaiqing", method = RequestMethod.GET, produces = "application/json")
	@ApiOperation(value = "根据灾情编号获取火场文书信息",response = Wsxx.class,responseContainer = "List",
		notes = "必选参数为灾情编号，返回结果按时间降序排列；支持根据时间范围筛选，支持根据反馈机构内部编码查询下级，支持查询指定机构的文书信息")
	@ApiImplicitParams({@ApiImplicitParam(name = "zqbh", value = "灾情编号", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "kssj",value = "开始时间",dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "jssj",value = "结束时间",dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "nbbm",value = "反馈机构内部编码，用于查询指定机构及下属机构发送的文书信息",dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "jgbh",value = "反馈机构编号，用于查询指定机构发送的文书信息",dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "from", value = "from,默认0", defaultValue = "0", paramType = "query", dataType = "int"),
			@ApiImplicitParam(name = "size", value = "size,默认50", defaultValue = "50", paramType = "query", dataType = "int"),
			@ApiImplicitParam(name="includes",value="返回字段，数组形式，逗号隔开",dataType="String",paramType="query")
			})
	public ResponseEntity<String> getWsxxByZqbh(@RequestParam String zqbh,
												@RequestParam(required = false) String kssj,
												@RequestParam(required = false) String jssj,
												@RequestParam(required = false) String nbbm,
												@RequestParam(required = false) String jgbh,
												@RequestParam(defaultValue = "0") int from,
												@RequestParam(defaultValue = "50") int size,
												@RequestParam(required = false) String[] includes) {
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
		if(nbbm != null && !nbbm.isEmpty()) params.put("nbbm",nbbm);
		if(jgbh != null && !jgbh.isEmpty()) params.put("jgbh",jgbh);
		params.put("from", from);
		params.put("size", size);
		params.put("includes", includes);

		res = wsxxService.getWsxxByConditions(params);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
}
