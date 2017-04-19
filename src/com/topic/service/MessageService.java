package com.topic.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.topic.db.OperateJDBC;

public class MessageService {
	private OperateJDBC OJDBC = new OperateJDBC();

	public Map<String, Object> getFriendMsg(String userId, String friendId,
			int pageSize, int pageNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		int begin = pageSize * pageNum;
		String sql_select_friend = "select conversation.*,user_photo from conversation,user where user_come = '"
				+ userId
				+ "' and user_to = '"
				+ friendId
				+ "' and user_to = user_id ORDER BY conv_time desc LIMIT "
				+ begin + "," + pageNum + "";
		ResultSet rs = OJDBC.executeQuery(sql_select_friend);
		try {
			ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
			while (rs.next()) {
				Map<String, String> msg = new HashMap<String, String>();
				msg.put("convContent", rs.getString("conv_content"));
				msg.put("convTime", rs.getString("conv_time"));
				msg.put("userPhoto", rs.getString("user_photo"));
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

	public Map<String, Object> getFriendMissMsg(String userId, String friendId,
			int misNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		int begin = 0;
		String sql_select_friend = "select conversation.*,user_photo from conversation,user where user_come = '"
				+ userId
				+ "' and user_to = '"
				+ friendId
				+ "' and user_to = user_id ORDER BY conv_time desc LIMIT "
				+ begin + "," + misNum + "";
		ResultSet rs = OJDBC.executeQuery(sql_select_friend);
		try {
			ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
			while (rs.next()) {
				Map<String, String> msg = new HashMap<String, String>();
				msg.put("convContent", rs.getString("conv_content"));
				msg.put("convTime", rs.getString("conv_time"));
				msg.put("userPhoto", rs.getString("user_photo"));
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

	public Map<String, Object> getTopicMsg(String topicId, int pageSize,
			int pageNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		int begin = pageSize * pageNum;
		String sql_select_meetimg = "select meeting.*,user_photo from meeting,user where meet_topicid = '"
				+ topicId
				+ "' and meet_userid = user_id ORDER BY meet_time desc LIMIT "
				+ begin + "," + pageNum + "";
		ResultSet rs = OJDBC.executeQuery(sql_select_meetimg);
		try {
			ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
			while (rs.next()) {
				Map<String, String> msg = new HashMap<String, String>();
				msg.put("meetContent", rs.getString("meet_content"));
				msg.put("meetTime", rs.getString("meet_time"));
				msg.put("userPhoto", rs.getString("user_photo"));
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

	public Map<String, Object> getTopicMissMsg(String topicId, int misNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		int begin = 0;
		String sql_select_meetimg = "select meeting.*,user_photo from meeting,user where meet_topicid = '"
				+ topicId
				+ "' and meet_userid = user_id ORDER BY meet_time desc LIMIT "
				+ begin + "," + misNum + "";
		ResultSet rs = OJDBC.executeQuery(sql_select_meetimg);
		try {
			ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
			while (rs.next()) {
				Map<String, String> msg = new HashMap<String, String>();
				msg.put("meetContent", rs.getString("meet_content"));
				msg.put("meetTime", rs.getString("meet_time"));
				msg.put("userPhoto", rs.getString("user_photo"));
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
}
