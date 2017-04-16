package com.topic.db;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.topic.tool.FileOperation;

public class MongoDB {
	private String dbtype = "mongodb";
	private Map<String, String> map = new HashMap<String, String>();

	public MongoDB() {
		try {
			FileOperation fileOperation = new FileOperation();
			map = fileOperation.getConfigData(dbtype);
		} catch (Exception e) {
//			System.out.println("Class MongoDB Initialization ERROR");
		}

	}

	public DB getConnect() {
		DB db = null;
		try {
			String hostname = map.get("host");
			int port = Integer.parseInt(map.get("port"));
			String dbname = map.get("dbname");
			Mongo mongo = new Mongo(hostname, port);
			db = mongo.getDB(dbname);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return db;
	}
	
	public static void main(String[] args){
//		MongoDB mongo = new MongoDB();
//		DB db = mongo.getConnect();
//		DBCollection collection = db.getCollection("ent");
//		DBCursor cur = collection.find(null,null).sort(new BasicDBObject("date",-1)).skip(1390).limit(50);
//		int num = cur.count();
//		DBObject dbObj = cur.next();
//		BasicDBList list = (BasicDBList) dbObj.get("miniimg");
//		BasicDBObject obj = (BasicDBObject) list.get(0);
//		String str = (String) obj.get("src");
//		System.out.println(str);
//		
//		JSONObject json = new JSONObject();
		
	}
}
