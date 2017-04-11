package com.topic.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.topic.db.MongoDB;

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
		int end = (from + pageNum) < allNum ? (from + pageNum) : (allNum - from);
		
		if (from > allNum) {
			map.put("status", "FALSE");
			return map;
		}
		
		List<DBObject> list = cur.toArray();
		List<DBObject> rs = new ArrayList<DBObject>();
		for (int i = from; i < end; i++) {
			rs.add(list.get(i));
		}
		map.put("status", "SUCCESS");
		map.put("list", rs);
		return map;
	}
}
