package com.ezfire.bean;

import java.util.HashMap;
import java.util.List;

/**
 * Created by lcy on 2018/3/5.
 */
public class AgsLineString {
	private List<List<Double[]>> paths;
	private HashMap<String, Integer> spatialReference;

	public List<List<Double[]>> getPaths() {
		return paths;
	}

	public void setPaths(List<List<Double[]>> paths) {
		this.paths = paths;
	}

	public HashMap<String, Integer> getSpatialReference() {
		return spatialReference;
	}

	public void setSpatialReference(HashMap<String, Integer> spatialReference) {
		this.spatialReference = spatialReference;
	}
}
