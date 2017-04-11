package com.topic.service;

import java.util.HashMap;
import java.util.Map;

import com.topic.db.OperateJDBC;
import com.topic.tool.DateFormat;

public class TopicService {
	private OperateJDBC OJDBC = new OperateJDBC();

	public Map<String, Object> insertOneTopic(String userId, String title,
			String description, String stillTime, String url, String degree,
			String typeId) {
		Map<String, Object> map = new HashMap<String, Object>();
		String date = DateFormat.getDateString();
		String sql_insert_topic = "insert into topic (topi_createid , topi_title , topi_description , topi_stilltime , topi_url , topi_degree , topi_type , topi_createtime) values ('"
				+ userId
				+ "','"
				+ title
				+ "','"
				+ description
				+ "','"
				+ stillTime
				+ "','"
				+ url
				+ "','"
				+ degree
				+ "','"
				+ typeId
				+ "','" + date + "')";
		int len = OJDBC.executeUpdate(sql_insert_topic);
		if(len == 1){
			map.put("status", "SUCCESS");
		}else{
			map.put("status", "FALSE");
		}
		return map;

	}
}
