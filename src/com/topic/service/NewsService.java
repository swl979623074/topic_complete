package com.topic.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.types.BasicBSONList;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.topic.db.MongoDB;
import com.topic.tool.ImgOperation;

public class NewsService {

	private DB db = null;

	public NewsService() {
		MongoDB mongoDB = new MongoDB();
		db = mongoDB.getConnect();
	}

	public Map<String, Object> getNewsByTypeName(String typeName, int pageSize,
			int pageNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		DBCollection collection = db.getCollection(typeName);
		DBCursor cur = collection.find(null, null).sort(
				new BasicDBObject("date", -1));
		int allNum = cur.count();
		int from = pageSize * pageNum;
		int end = (from + pageNum) < allNum ? (from + pageNum)
				: (allNum - from);

		if (from > allNum) {
			map.put("status", "FALSE");
			return map;
		}

		List<DBObject> list = cur.toArray();
		List<DBObject> rs = new ArrayList<DBObject>();
		for (int i = from; i < end; i++) {
			DBObject dbObj = list.get(i);
			BasicDBList lbimg_list = (BasicDBList) dbObj.get("lbimg");
			BasicDBObject lbimg_obj = (BasicDBObject) lbimg_list.get(0);
			String lbimg_src = (String) lbimg_obj.get("src");
			String lbimg_type = lbimg_src.split("\\.")[1];
			String lbimg_imgHeader = "data:image/" + lbimg_type + ";base64,";
			String lbimg_imgData = lbimg_imgHeader
					+ ImgOperation.getImageBinary(lbimg_src);
			((BasicDBObject) ((BasicDBList) list.get(i).get("lbimg")).get(0))
					.put("src", lbimg_imgData);

			BasicDBList miniimg_list = (BasicDBList) dbObj.get("miniimg");
			int len = miniimg_list.size();
			for (int j = 0; j < len; j++) {
				BasicDBObject miniimg_obj = (BasicDBObject) miniimg_list.get(j);
				String miniimg_src = (String) miniimg_obj.get("src");
				String miniimg_type = miniimg_src.split("\\.")[1];
				String miniimg_imgHeader = "data:image/" + miniimg_type
						+ ";base64,";
				String miniimg_imgData = miniimg_imgHeader
						+ ImgOperation.getImageBinary(miniimg_src);
				
				((BasicDBObject) ((BasicDBList) list.get(i).get("miniimg"))
						.get(j)).put("src", miniimg_imgData);
			}

			rs.add(list.get(i));
		}
		map.put("status", "SUCCESS");
		map.put("list", rs);
		return map;
	}
}
