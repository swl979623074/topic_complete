package com.topic.service;

import java.util.Date;
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
		String currentTime = DateFormat.getCurrentDate_string();
		String delayDate = DateFormat.getDelayDate_string(stillTime);

		Long time = new Date().getTime();
		String topicId = "topic_" + userId + "_" + time;
		String sql_insert_topic = "insert into topic (topi_id,topi_createid , topi_title , topi_description , topi_stilltime , topi_url , topi_degree , topi_type , topi_createtime) values ('"
				+ topicId
				+ "','"
				+ userId
				+ "','"
				+ title
				+ "','"
				+ description
				+ "','"
				+ delayDate
				+ "','"
				+ url
				+ "','"
				+ degree + "','" + typeId + "','" + currentTime + "')";
		String sql_insert_group = "insert into groups (group_userid,group_topicid) values ('"
				+ userId + "','" + topicId + "')";
		int len = OJDBC.executeUpdate(sql_insert_topic);
		if (len == 1) {
			OJDBC.executeUpdate(sql_insert_group);
			map.put("status", "SUCCESS");
		} else {
			map.put("status", "FALSE");
		}
		return map;

	}
}
