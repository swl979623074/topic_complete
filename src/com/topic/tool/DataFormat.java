package com.topic.tool;

import net.sf.json.JSONObject;

public class DataFormat {
	
	public static JSONObject toJSON(String str){
		JSONObject obj = JSONObject.fromObject(str);
		return obj;
	}
	
	public static String toString(JSONObject obj){
		return obj.toString();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

}
