package com.topic.db;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.topic.tool.FileOperation;

public class MongoDB {
	private String dbtype = "mongodb";
	private Map<String, String> map = new HashMap<String, String>();

	public MongoDB() {
		try {
			FileOperation fileOperation = new FileOperation();
			map = fileOperation.getData(dbtype);
		} catch (Exception e) {
			System.out.println("Class MongoDB Initialization ERROR");
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
		MongoDB mongo = new MongoDB();
		DB db = mongo.getConnect();
		DBCollection collection = db.getCollection("world");
		DBCursor cur = collection.find(null,null);
		int num = collection.find(null,null).count();
		System.out.println(cur.toArray());
		System.out.println(num);
	}
}
