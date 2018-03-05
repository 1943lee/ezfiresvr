package com.ezfire.common;

import com.alibaba.fastjson.JSON;
import com.ezfire.bean.AgsLineString;
import com.ezfire.bean.AgsMultiPoint;
import com.ezfire.bean.AgsPoint;
import com.ezfire.bean.AgsPolygon;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lcy on 2018/3/5.
 */
public class WktUtils {
	public static String writeAgsjson(String wkt) {
		String [] types = wkt.split(" ");
		String result = null;
		String type = types[0];

		if (type == null || type.isEmpty())
		{
			throw new IllegalArgumentException("Not support " + types + "yet.");
		}

		if (type.equals("POINT")) {
			result= getPOINTWktToJson(wkt, 4326);
		} else if (type.equals("MULTIPOINT")) {
			result= getMULTIPOINTWktToJson(wkt, 4326);
		} else if (type.equals("LINESTRING")) {
			result= getLINESTRINGWktToJson(wkt, 4326);
		} else if (type.equals("MULTILINESTRING")) {
			result= getMULTILINESTRINGWktToJson(wkt, 4326);
		} else if (type.equals("POLYGON")) {
			result= getPOLYGONWktToJson(wkt, 4326);
		} else if (type.equals("MULTIPOLYGON")) {
			result= getMULTIPOLYGONWktToJson(wkt,4326);
		} else {
			throw new IllegalArgumentException("Not support " + types + "yet.");
		}
		return result;
	}

	/**
	 * 点 转换 JSON
	 *
	 * @param wkt
	 * @param wkid
	 * @return
	 */
	public static String getPOINTWktToJson(String wkt, int wkid) {

		String[] strHead = wkt.split("\\(");
		String strContent = strHead[1].substring(0, strHead[1].length() - 1);
		String[] strResult = strContent.split(" ");

		AgsPoint pointObject = new AgsPoint();
		pointObject.setX(Double.parseDouble(strResult[0]));
		pointObject.setY(Double.parseDouble(strResult[1]));

		HashMap<String, Integer> spatialReference = new HashMap<>();
		spatialReference.put("wkid", wkid);

		pointObject.setSpatialReference(spatialReference);

		return JSON.toJSONString(pointObject);
	}

	/**
	 * 多点 转换 JSON
	 *
	 * @param wkt
	 * @param wkid
	 * @return
	 */
	public static String getMULTIPOINTWktToJson(String wkt, int wkid) {

		AgsMultiPoint multiPointObject = new AgsMultiPoint();

		String ToTailWkt = wkt.substring(0, wkt.length() - 1);
		String[] strHead = ToTailWkt.split("\\(\\(");
		String strMiddle = strHead[1].substring(0, strHead[1].length() - 1);
		String[] strMiddles = strMiddle.split(",");

		List<Double[]> list = new ArrayList<>();

		for (int i = 0; i < strMiddles.length; i++) {

			if (i == 0) {

				String item = strMiddles[i].substring(0,
						strMiddles[i].length() - 1);
				String[] items = item.split(" ");
				Double[] listResult = new Double[] {
						Double.parseDouble(items[0]),
						Double.parseDouble(items[1]) };

				list.add(listResult);

			} else if (i == strMiddles.length) {

				String item = strMiddles[i]
						.substring(1, strMiddles[i].length());
				String[] items = item.split(" ");
				Double[] listResult = new Double[] {
						Double.parseDouble(items[0]),
						Double.parseDouble(items[1]) };

				list.add(listResult);
			} else {

				String strItem = strMiddles[i].trim();
				String item = strItem.substring(1, strItem.length() - 1);
				String[] items = item.split(" ");
				Double[] listResult = new Double[] {
						Double.parseDouble(items[0]),
						Double.parseDouble(items[1]) };

				list.add(listResult);
			}

		}

		HashMap<String, Integer> spatialReference = new HashMap<>();
		spatialReference.put("wkid", wkid);

		multiPointObject.setPoints(list);
		multiPointObject.setSpatialReference(spatialReference);

		return JSON.toJSONString(multiPointObject);

	}

	/**
	 * 线 转换 JSON
	 *
	 * @param wkt
	 * @param wkid
	 * @return
	 */
	public static String getLINESTRINGWktToJson(String wkt, int wkid) {

		AgsLineString lineStringObject = new AgsLineString();

		List<List<Double[]>> lists = new ArrayList<>();

		String[] strHead = wkt.split("\\(");
		String strContent = strHead[1].substring(0, strHead[1].length() - 1);

		List<Double[]> list = new ArrayList<>();

		getCoordsFromString(strContent, list);

		lists.add(list);

		HashMap<String, Integer> spatialReference = new HashMap<>();
		spatialReference.put("wkid", wkid);

		lineStringObject.setPaths(lists);
		lineStringObject.setSpatialReference(spatialReference);

		return JSON.toJSONString(lineStringObject);
	}

	/**
	 * 多线 转换 JSON
	 *
	 * @param wkt
	 * @param wkid
	 * @return
	 */
	public static String getMULTILINESTRINGWktToJson(String wkt, int wkid) {
		AgsLineString lineStringObject = new AgsLineString();

		List<List<Double[]>> lists = new ArrayList<>();

		String ToTailWkt = wkt.substring(0, wkt.length() - 1);
		String[] strHead = ToTailWkt.split("\\(", 2);

		String[] strList = strHead[1].split("\\),\\(");

		if(strList.length == 1) {
			String item = strList[0].trim();
			item = item.substring(1, item.length() - 1);

			List<Double[]> list = new ArrayList<>();

			getCoordsFromString(item, list);

			lists.add(list);
		}
		else {
			strList[0] = strList[0].substring(1);
			strList[strList.length - 1] = strList[strList.length - 1].substring(0, strList[strList.length - 1].length() - 1);
			for (int i = 0; i < strList.length; i++) {
				String item = strList[i].trim();

				List<Double[]> list = new ArrayList<>();

				getCoordsFromString(item, list);

				lists.add(list);
			}
		}
		HashMap<String, Integer> spatialReference = new HashMap<>();
		spatialReference.put("wkid", wkid);

		lineStringObject.setPaths(lists);
		lineStringObject.setSpatialReference(spatialReference);

		return JSON.toJSONString(lineStringObject);
	}

	public static String getPOLYGONWktToJson(String wkt, int wkid) {

		AgsPolygon polygonObject = new AgsPolygon();

		List<List<Double[]>> lists = new ArrayList<>();

		String ToTailWkt = wkt.substring(0, wkt.length() - 1);
		String[] strHead = ToTailWkt.split("\\(", 2);

		String[] strList = strHead[1].split("\\),\\(");

		for (int i = 0; i < strList.length; i++) {

			String item = strList[i].trim();
			item = item.substring(1, item.length() - 1);

			List<Double[]> list = new ArrayList<>();

			getCoordsFromString(item, list);

			lists.add(list);
		}

		HashMap<String, Integer> spatialReference = new HashMap<>();
		spatialReference.put("wkid", wkid);

		polygonObject.setRings(lists);
		polygonObject.setSpatialReference(spatialReference);

		return JSON.toJSONString(polygonObject);
	}

	public static String getMULTIPOLYGONWktToJson(String wkt, int wkid) {

		AgsPolygon polygonObject = new AgsPolygon();

		List<List<Double[]>> lists = new ArrayList<>();

		String ToTailWkt = "";
		ToTailWkt = wkt.substring(0, wkt.length());
		String[] strHead = ToTailWkt.split(" ", 2);
		ToTailWkt = strHead[1].substring(1, strHead[1].length() - 1);

		String[] strList = ToTailWkt.split("\\),\\(");

		if (strList.length == 1) {

			for (int i = 0; i < strList.length; i++) {
				String item = strList[i].trim();
				item = item.substring(2, item.length() - 2);

				List<Double[]> list = new ArrayList<>();

				getCoordsFromString(item, list);

				lists.add(list);
			}

		} else {
			strList[0]=strList[0].substring(1);
			strList[strList.length - 1]=strList[strList.length - 1].substring(0,strList[strList.length - 1].length() - 1);

			for (int i = 0; i < strList.length; i++) {

				if(strList[i].startsWith("\\("))
					strList[i] = strList[i].substring(1, strList.length - 1);
				String item = strList[i].trim();
				item = item.substring(1, item.length() - 1);
				String[] items = item.split(",");

				List<Double[]> list = new ArrayList<>();

				for (int j = 1; j < items.length; j++) {
					String jItem = items[j].trim();
					String[] jItems = jItem.split(" ");

					Double[] listResult = new Double[] {
							Double.parseDouble(jItems[0]),
							Double.parseDouble(jItems[1]) };

					list.add(listResult);

				}

				lists.add(list);

			}

		}

		HashMap<String, Integer> spatialReference = new HashMap<>();
		spatialReference.put("wkid", wkid);

		polygonObject.setRings(lists);
		polygonObject.setSpatialReference(spatialReference);

		return JSON.toJSONString(polygonObject);
	}

	private static void getCoordsFromString(String item, List<Double[]> list) {
		String[] items = item.split(",");
		for (int j = 0; j < items.length; j++) {
			String jItem = items[j].trim();
			String[] jItems = jItem.split(" ");

			Double[] listResult = new Double[] {
					Double.parseDouble(jItems[0]),
					Double.parseDouble(jItems[1]) };

			list.add(listResult);
		}
	}

	public static String GeoShapeToWkt(Map<String,Object> map) {
		if (map == null)
			return "";

		// 获取Geometry类型
		String type = map.get("type").toString();
		String wkt = "";
		if (type.equals("point")) {
			wkt = GetWktFromPoint(map);
		} else if (type.equals("linestring")) {
			wkt = GetWktFromLine(map);
		} else if (type.equals("multilinestring")) {
			wkt = GetWktFromMultiLine(map);
		} else if (type.equals("polygon")) {
			wkt = GetWktFromPolygon(map);
		} else if (type.equals("multipolygon")) {
			wkt = GetWktFromMultiPolygon(map);
		}
		return wkt;
	}

	// 根据point坐标得到wkt
	private static String GetWktFromPoint(Map<String,Object> map) {
		ArrayList<Object> pointList = (ArrayList<Object>)map.get("coordinates");
		if (pointList == null || pointList.size() != 2)
			return "";

		StringBuffer sb = new StringBuffer("POINT (");
		sb.append(pointList.get(0).toString() + " " + pointList.get(1).toString());
		sb.append(")");

		return sb.toString();
	}

	// 根据linestring坐标得到wkt
	private static String GetWktFromLine(Map<String,Object> map) {
		ArrayList<ArrayList<Object>> lineList = (ArrayList<ArrayList<Object>>)map.get("coordinates");
		if (lineList == null || lineList.size() <= 1)
			return "";

		StringBuffer sb = new StringBuffer("LINESTRING (");

		getStringFromPointList(lineList, sb);

		sb.append(")");
		return sb.toString();
	}

	// 根据multilinestring坐标得到wkt
	private static String GetWktFromMultiLine(Map<String,Object> map) {
		ArrayList<ArrayList<ArrayList<Object>>> multilineList = (ArrayList<ArrayList<ArrayList<Object>>>)map.get("coordinates");
		if (multilineList == null)
			return "";

		// 如果只有一条线，当作linestring处理
		if(multilineList.size() == 1) {
			ArrayList<ArrayList<Object>> lineList = multilineList.get(0);
			if (lineList == null || lineList.size() <= 1)
				return "";

			StringBuffer sb = new StringBuffer("LINESTRING (");
			getStringFromPointList(lineList, sb);

			sb.append(")");
			return sb.toString();
		}
		else {
			StringBuffer sb = new StringBuffer("MULTILINESTRING (");
			getStringFromPolygonList(multilineList, sb);
			if (sb.charAt(sb.length() - 1) == ',')
				sb.deleteCharAt(sb.length() - 1);
			sb.append(")");

			return sb.toString();
		}
	}

	// 根据polygon坐标得到wkt
	private static String GetWktFromPolygon(Map<String,Object> map) {
		ArrayList<ArrayList<ArrayList<Object>>> polygonList = (ArrayList<ArrayList<ArrayList<Object>>>)map.get("coordinates");
		if (polygonList == null)
			return "";
		StringBuffer stringBuffer = new StringBuffer("POLYGON (");

		getStringFromPolygonList(polygonList, stringBuffer);
		if (stringBuffer.charAt(stringBuffer.length() - 1) == ',')
			stringBuffer.deleteCharAt(stringBuffer.length() - 1);
		stringBuffer.append(")");
		return stringBuffer.toString();
	}

	// 根据multipolygon坐标得到wkt
	private static String GetWktFromMultiPolygon(Map<String,Object> map) {
		ArrayList<ArrayList<ArrayList<ArrayList<Object>>>> multiPolygonList = (ArrayList<ArrayList<ArrayList<ArrayList<Object>>>>)map.get("coordinates");
		if (multiPolygonList == null)
			return "";
		StringBuffer stringBuffer = new StringBuffer("MULTIPOLYGON (");

		for (ArrayList<ArrayList<ArrayList<Object>>> polygonList : multiPolygonList) {
			if(polygonList == null)
				continue;
			stringBuffer.append("(");
			getStringFromPolygonList(polygonList, stringBuffer);
			if (stringBuffer.charAt(stringBuffer.length() - 1) == ',')
				stringBuffer.deleteCharAt(stringBuffer.length() - 1);
			stringBuffer.append("),");
		}
		if (stringBuffer.charAt(stringBuffer.length() - 1) == ',')
			stringBuffer.deleteCharAt(stringBuffer.length() - 1);
		stringBuffer.append(")");
		return stringBuffer.toString();
	}

	private static void getStringFromPointList(ArrayList<ArrayList<Object>> lineList, StringBuffer sb) {
		int i = 0;
		for(ArrayList<Object> pointList : lineList) {
			if (pointList == null || pointList.size() != 2)
				continue;

			sb.append(pointList.get(0).toString() + " " + pointList.get(1).toString());
			if (i < lineList.size() - 1)
				sb.append(",");
			i++;
		}
	}

	private static void getStringFromPolygonList(ArrayList<ArrayList<ArrayList<Object>>> polygonList, StringBuffer sb) {
		for (ArrayList<ArrayList<Object>> lineList : polygonList) {
			if(lineList == null)
				continue;
			sb.append("(");
			getStringFromPointList(lineList,sb);
			sb.append("),");
		}
	}

	/**
	 * 根据wkt获取中心点
	 * @param wktString
	 * @return
	 */
	public static Coordinate getCenterPoint(String wktString) {
		if (wktString == null || wktString.isEmpty())
			return null;

		try {
			WKTReader reader = new WKTReader(new GeometryFactory());
			Geometry geometry = reader.read(wktString);
			return geometry.getCentroid().getCoordinate();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}
