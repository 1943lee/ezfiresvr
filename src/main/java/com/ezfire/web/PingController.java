package com.ezfire.web;

import com.ezfire.common.ESClient;
import org.elasticsearch.action.main.MainResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.cluster.ClusterName;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<String> ping() {
		return new ResponseEntity<String>("ezFireSvr Started", HttpStatus.OK);
	}

	@RequestMapping(value = "/elastic/nodes", method = RequestMethod.GET)
	public ResponseEntity<String> pingElastic() {
		RestHighLevelClient client = ESClient.getHightClient();

		try {
			MainResponse mainResponse = client.info();
			ClusterName clusterName = mainResponse.getClusterName();

			return new ResponseEntity<>(clusterName.value(),HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<>("Server Error Occurred",HttpStatus.OK);
		}
	}
}
