package com.topic.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

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

	public Map<String, Object> getTopicListByUserId(String userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql_select_topic = "select t.*,g.group_missmsg from groups g,topic t where g.group_userid = '"
				+ userId
				+ "' and g.group_topicid = t.topi_id and t.topi_closed = 0";

		ResultSet rs = OJDBC.executeQuery(sql_select_topic);
		try {
			ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
			while (rs.next()) {
				Map<String, String> msg = new HashMap<String, String>();
				msg.put("topicId", rs.getString("topi_id"));
				msg.put("topicTitle", rs.getString("topi_title"));
				msg.put("topicDescriptione", rs.getString("topi_description"));
				msg.put("topicCreateTime", rs.getString("topi_createtime"));
				msg.put("topicEndTime", rs.getString("topi_stilltime"));
				msg.put("topicUrl", rs.getString("topi_url"));
				msg.put("topicDegree", rs.getString("topi_degree"));
				msg.put("topicMissNum", rs.getString("group_missmsg"));

				list.add(msg);
			}
			map.put("status", "SUCCESS");
			map.put("list", list);
		} catch (SQLException e) {
			map.put("status", "FALSE");
			map.put("msg", "SQLException");
			e.printStackTrace();
		}
		return map;
	}

	public Map<String, Object> updateMeetWindowAndMissMsg(String userId,
			String topicId) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql_update_groups = "update groups set group_openwindow = 1,group_missmsg = 0 where group_userid = '"
				+ userId + "' and group_topicid = '" + topicId + "'";
		int len = OJDBC.executeUpdate(sql_update_groups);
		if (len > 0) {
			map.put("status", "SUCCESS");
		} else {
			map.put("status", "FALSE");
		}
		return map;
	}

	public Map<String, Object> updateWindowStatus(String userId,
			String topicId, String status) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql_update_groups = "update groups set group_openwindow = '"
				+ status + "' where group_userid = '" + userId
				+ "' and group_topicid = '" + topicId + "'";
		int len = OJDBC.executeUpdate(sql_update_groups);
		if (len > 0) {
			map.put("status", "SUCCESS");
		} else {
			map.put("status", "FALSE");
		}
		return map;
	}

	public Map<String, Object> deleteTopicAboutUser(String userId,
			String topicId) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql_delete_groups = "delete from groups where group_userid = '"
				+ userId + "' and group_topicid = '" + topicId + "'";
		int len = OJDBC.executeUpdate(sql_delete_groups);
		if (len > 0) {
			map.put("status", "SUCCESS");
		} else {
			map.put("status", "FALSE");
		}
		return map;
	}
	
	public Map<String, Object> getTopicMsg(String topicId){
		Map<String, Object> map = new HashMap<String, Object>();
		String sql_topic = "select * from topic where topi_id = '"+topicId+"'";
		ResultSet rs = OJDBC.executeQuery(sql_topic);
		try {
			rs.next();
			map.put("status", "SUCCESS");
			map.put("topicId", rs.getString("topi_id"));
			map.put("topicTitle", rs.getString("topi_title"));
			map.put("topicDescriptione", rs.getString("topi_description"));
			map.put("topicCreateTime", rs.getString("topi_createtime"));
			map.put("topicEndTime", rs.getString("topi_stilltime"));
			map.put("topicUrl", rs.getString("topi_url"));
			map.put("topicDegree", rs.getString("topi_degree"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			map.put("status", "FALSE");
			e.printStackTrace();
		}
		return map;
	}
	
	public Map<String, Object> initAllWindowStatus(String userId){
		Map<String, Object> map = new HashMap<String, Object>();
		String sql_update = "update groups set group_openwindow = 0 where group_userid = '"+userId+"'";
		int num = OJDBC.executeUpdate(sql_update);
		map.put("status", "SUCCESS");
		return map;
	}
	
	public Map<String, Object>  addTopicForUser(String userId,String topicId){
		Map<String, Object> map = new HashMap<String, Object>();
		String sql_find = "select * from groups where group_userid = '"+userId+"' and group_topicid ='"+topicId+"'";
		String sql_add = "insert into groups (group_userid,group_topicid) values ('"+userId+"','"+topicId+"')";
		
		ResultSet rs = OJDBC.executeQuery(sql_find);
		try {
			rs.last();
			int len = rs.getRow();
			if(len == 0){
				OJDBC.executeUpdate(sql_add);
				map.put("status", "SUCCESS");
			}else{
				map.put("status", "FALSE");
				map.put("msg", "exist");
			}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			map.put("status", "FALSE");
			e.printStackTrace();
		}
		return map;
	}
}
