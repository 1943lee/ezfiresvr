package com.ezfire.web;

import com.ezfire.common.ComMethod;
import com.ezfire.domain.Dictionary;
import com.ezfire.domain.FireDanger;
import com.ezfire.domain.FireDangerSimple;
import com.ezfire.service.FireDangerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lcy on 2018/2/2.
 */
@RestController
@RequestMapping(value = "/fireDanger")
@Api(value = "危化品信息")
public class FireDangerController {
	@Autowired
	FireDangerService fireDangerService;

	@RequestMapping(value = "/dictionary",produces = "application/json",method = RequestMethod.GET)
	@ApiOperation(value = "获取危化品类别字典项信息",response = Dictionary.class,responseContainer = "List")
	public  ResponseEntity<String> getFireDangerDictionary() {
		return new ResponseEntity<>(fireDangerService.getDictionary(), HttpStatus.OK);
	}

	@RequestMapping(value = "/simple",produces = "application/json",method = RequestMethod.GET)
	@ApiOperation(value = "获取危化品简易信息",notes = "支持分页，支持分类检索，支持中文名、英文名、分子式模糊查询",response = FireDangerSimple.class,responseContainer = "List")
	public ResponseEntity<String> getAll(@RequestParam(defaultValue = "0") int from,
										 @RequestParam(defaultValue = "10") int size,
										 @RequestParam(defaultValue = "1") String nbbm,
										 @RequestParam(defaultValue = "",required = false) String zwm,
										 @RequestParam(defaultValue = "",required = false) String ywm,
										 @RequestParam(defaultValue = "",required = false ) String fzs) {
		return new ResponseEntity<>(fireDangerService.getAll(from, size,nbbm, ComMethod.encodeStr(zwm),ywm,fzs), HttpStatus.OK);
	}

	@RequestMapping(value = "/detail",produces = "application/json",method = RequestMethod.GET)
	@ApiOperation(value = "获取危化品详细信息",response = FireDanger.class)
	public ResponseEntity<String> getDetail(@RequestParam String bh) {
		return new ResponseEntity<>(fireDangerService.getDetail(bh), HttpStatus.OK);
	}
}
