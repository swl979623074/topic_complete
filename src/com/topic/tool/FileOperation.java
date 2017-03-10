package com.topic.tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

public class FileOperation {
	private String filepath = "./src/dbconfig";

	@SuppressWarnings("finally")
	public Map<String, String> getData(String dbtype) {
		Map<String, String> map = new HashMap<String, String>();
		File file = new File(filepath);
		try {
			@SuppressWarnings("resource")
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String tempString = new String();
			while ((tempString = reader.readLine()) != null) {
				int flay = tempString.indexOf(dbtype);
				if (flay > 0) {
					while ((tempString = reader.readLine()) != null) {
						if (tempString.equals("[end]")) {
							return map;
						}
						int point = tempString.indexOf(":");
						String key = tempString.substring(0, point);
						String value = tempString.substring(point + 1,
								tempString.length());
						map.put(key, value);
					}
				}
			}
			return null;
		} catch (Exception e) {
			System.out.println("File operation error");
			map = null;
		} finally {
			return map;
		}
	}

	public static void main(String[] args) {
		Map<String, String> map = new HashMap<String, String>();
		FileOperation fileOp = new FileOperation();
		map = fileOp.getData("mysql");
		System.out.println(map);
		
		String message = "{'name':'swl'}";
		JSONObject obj = JSONObject.fromObject(message);
		System.out.println(obj.get("name"));
	}
}
