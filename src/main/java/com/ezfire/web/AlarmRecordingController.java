package com.ezfire.web;

import com.ezfire.domain.AlarmRecording;
import com.ezfire.service.AlarmRecordingService;
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
 * Created by lcy on 2018/3/15.
 */
@RestController
@RequestMapping(value = "/zqly")
@Api(value = "灾情录音信息")
public class AlarmRecordingController {
	@Autowired
	AlarmRecordingService alarmRecordingService;

	@RequestMapping(value = "/zaiqing", method = RequestMethod.GET, produces = "application/json")
	@ApiOperation(value = "根据灾情编号获取灾情录音信息", response = AlarmRecording.class, responseContainer = "List",
			notes = "必选参数为灾情编号，返回结果按报警开始时间降序排列；支持根据（报警开始）时间范围筛选")
	@ApiImplicitParams({@ApiImplicitParam(name = "zqbh", value = "灾情编号", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "kssj",value = "开始时间,yyyy-MM-dd HH:mm:ss",dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "jssj",value = "结束时间,yyyy-MM-dd HH:mm:ss",dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "from", value = "from,默认0", defaultValue = "0", paramType = "query", dataType = "int"),
			@ApiImplicitParam(name = "size", value = "size,默认50", defaultValue = "50", paramType = "query", dataType = "int"),
			@ApiImplicitParam(name="includes",value="返回字段，数组形式，逗号隔开",dataType="String",paramType="query")
	})
	public ResponseEntity<String> getRecordingByZqbh(@RequestParam String zqbh,
												@RequestParam(required = false) String kssj,
												@RequestParam(required = false) String jssj,
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
		params.put("from", from);
		params.put("size", size);
		params.put("includes", includes);

		res = alarmRecordingService.getRecordingByConditions(params);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
}
