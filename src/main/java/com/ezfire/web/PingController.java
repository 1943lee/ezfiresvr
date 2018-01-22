package com.ezfire.web;

import com.ezfire.common.ESClient;
import org.elasticsearch.action.main.MainResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.cluster.ClusterName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;

/**
 * Created by lcy on 2018/1/20.
 */
@RestController
@RequestMapping("/ping")
@ApiIgnore
public class PingController {
	private static Logger s_logger = LoggerFactory.getLogger(PingController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String ping() {
		s_logger.info("ezFireSvr Started");
		return "ezFireSvr Started";
	}

	@RequestMapping(value = "/elastic/nodes", method = RequestMethod.GET)
	public String pingElastic() {
		s_logger.info("/elastic/nodes");

		RestHighLevelClient client = ESClient.getHightClient();

		try {
			MainResponse mainResponse = client.info();
			ClusterName clusterName = mainResponse.getClusterName();

			return clusterName.value();
		} catch (IOException e) {
			e.printStackTrace();
			return "Server Error Occurred";
		}
	}
}
