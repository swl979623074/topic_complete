package com.topic.service;

import java.util.HashMap;
import java.util.Map;

import com.topic.db.OperateJDBC;

public class FriendService {
	private OperateJDBC OJDBC = new OperateJDBC();

	public Map<String, Object> updateOpenWindowAndMissMsg(String userId,
			String friendId) {
		Map<String, Object> map = new HashMap<String, Object>();
		String update_friend = "update friend set frie_openwindow = 0,frie_missmsg=0 where user_id = '"
				+ userId + "' and frie_id = '" + friendId + "'";
		int len = OJDBC.executeUpdate(update_friend);
		if (len == 1) {
			map.put("status", "SUCCESS");
		} else {
			map.put("status", "FALSE");
		}
		return map;
	}

	public Map<String, Object> closeWindow(String userId, String friendId) {
		Map<String, Object> map = new HashMap<String, Object>();
		String update_friend = "update friend set frie_openwindow = 0 where user_id = '"
				+ userId + "' and frie_id = '" + friendId + "'";
		int len = OJDBC.executeUpdate(update_friend);
		if (len == 1) {
			map.put("status", "SUCCESS");
		} else {
			map.put("status", "FALSE");
		}
		return map;
	}

	public Map<String, Object> updateRemark(String userId, String friendId,
			String remark) {
		Map<String, Object> map = new HashMap<String, Object>();
		String update_remark = "update friend set frie_remark = '" + remark
				+ "' where user_id = '" + userId + "' and frie_id = '"
				+ friendId + "'";
		int len = OJDBC.executeUpdate(update_remark);
		if (len == 1) {
			map.put("status", "SUCCESS");
		} else {
			map.put("status", "FALSE");
		}
		return map;
	}

	public Map<String, Object> deleteFriend(String userId, String friendId) {
		Map<String, Object> map = new HashMap<String, Object>();
		String delete_friend = "delete from friend where user_id = '"
				+ userId + "' and frie_id = '" + friendId + "'";
		int len = OJDBC.executeUpdate(delete_friend);
		if (len == 1) {
			map.put("status", "SUCCESS");
		} else {
			map.put("status", "FALSE");
		}
		return map;
	}

}
