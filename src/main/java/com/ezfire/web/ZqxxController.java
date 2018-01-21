package com.ezfire.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lcy on 2018/1/21.
 */
@RestController
@RequestMapping(value = "/zqxx")
public class ZqxxController {
	private static Logger s_logger = LoggerFactory.getLogger(ZqxxController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String getBasicInfo() {
		return "";
	}
}
