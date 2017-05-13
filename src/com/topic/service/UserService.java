package com.topic.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.topic.db.OperateJDBC;
import com.topic.tool.ImgOperation;

public class UserService {
	private OperateJDBC OJDBC = new OperateJDBC();

	private String getImgBinaryData(String imgPath){
		if(imgPath == null)
			return null;
		String type = imgPath.split("\\.")[1];
		String imgHeader = "data:image/" + type
				+ ";base64,";
		String imgData = imgHeader
				+ ImgOperation.getImageBinary(imgPath);
		return imgData;
	}
	
	public Map<String, Object> getUserMsg(String userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql_getUser = "select * from user where user_id = '" + userId
				+ "'";
		ResultSet rs = OJDBC.executeQuery(sql_getUser);
		try {
			rs.next();
			Map<String, String> userMap = new HashMap<String, String>();

			userMap.put("userAccount", rs.getString("user_account"));
			userMap.put("userAlias", rs.getString("user_alias"));
			userMap.put("userEmail", rs.getString("user_email"));
			userMap.put("userSex", rs.getString("user_sex"));
			userMap.put("userProfession", rs.getString("user_profession"));
			userMap.put("userCreatetime", rs.getString("user_createtime"));
			userMap.put("userDegree", rs.getString("user_degree"));
			userMap.put("userphoto", getImgBinaryData(rs.getString("user_photo")));

			map.put("status", "SUCCESS");
			map.put("userMsg", userMap);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("status", "FALSE");
		}

		return map;
	}

	public Map<String, Object> getUserMsgByAccount(String account) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql_getUser = "select * from user where user_account = '"
				+ account + "'";
		ResultSet rs = OJDBC.executeQuery(sql_getUser);
		try {
			rs.last();
			int len = rs.getRow();
			if(len == 0){
				map.put("status", "FALSE");
			}else{
				rs.first();
				Map<String, String> userMap = new HashMap<String, String>();

				userMap.put("userId", rs.getString("user_id"));
				userMap.put("userAccount", rs.getString("user_account"));
				userMap.put("userAlias", rs.getString("user_alias"));
				userMap.put("userEmail", rs.getString("user_email"));
				userMap.put("userSex", rs.getString("user_sex"));
				userMap.put("userProfession", rs.getString("user_profession"));
				userMap.put("userCreatetime", rs.getString("user_createtime"));
				userMap.put("userDegree", rs.getString("user_degree"));
				userMap.put("userphoto", getImgBinaryData(rs.getString("user_photo")));

				map.put("status", "SUCCESS");
				map.put("userMsg", userMap);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("status", "FALSE");
		}

		return map;
	}

	public Map<String, Object> updateUserPwd(String userId, String oldPwd,
			String newPwd) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql_update_userPwd = "update user set user_pwd = '" + newPwd
				+ "' where user_id = '" + userId + "' and user_pwd = '"
				+ oldPwd + "'";
		int len = OJDBC.executeUpdate(sql_update_userPwd);
		if (len == 1) {
			map.put("status", "SUCCESS");
		} else {
			map.put("status", "FALSE");
		}
		return map;
	}

	public Map<String, Object> getFriendByUserId(String userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql_select_friend = "select fri.frie_remark,fri.frie_missmsg,u.* from friend fri,user u where fri.user_id = "
				+ userId + " and fri.frie_id = u.user_id";
		ResultSet rs = OJDBC.executeQuery(sql_select_friend);
		try {
			ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
			while(rs.next()){
				Map<String, String> userMap = new HashMap<String, String>();

				userMap.put("userId", rs.getString("user_id"));
				userMap.put("firendRemark",rs.getString("frie_remark"));
				userMap.put("friendMissMsg",rs.getString("frie_missmsg"));
				userMap.put("userAlias", rs.getString("user_alias"));
				userMap.put("userSex", rs.getString("user_sex"));
				userMap.put("userDegree", rs.getString("user_degree"));
				userMap.put("userphoto", getImgBinaryData(rs.getString("user_photo")));
				
				list.add(userMap);
			}
			map.put("status", "SUCCESS");
			map.put("list", list);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("status", "FALSE");
		}
		return map;
	}

	public Map<String, Object> addFriend(String userId, String firendAccount) {
		Map<String, Object> map = new HashMap<String, Object>();
		String insert_friend_one = "insert into friend (user_id,frie_id) values('"
				+ userId + "',(select user_id from user where user_account='"
				+ firendAccount + "'))";
		String insert_friend_two = "insert into friend (frie_id,user_id) values('"
				+ userId + "',(select user_id from user where user_account='"
				+  firendAccount  + "'))";
		int key_one = OJDBC.executeUpdate(insert_friend_one);
		int key_two = OJDBC.executeUpdate(insert_friend_two);
		Boolean key = false;
		if(key_one == 1 && key_two == 1)
			key = true;;
		if (key == true) {
			map.put("status", "SUCCESS");
		} else {
			map.put("status", "FALSE");
		}
		return map;
	}

	public Map<String, Object> updateFriendAlias(String userId,
			String firendId, String aliasName) {
		Map<String, Object> map = new HashMap<String, Object>();
		String update_friend = "update friend set frie_remark  = '" + aliasName
				+ "' where user_id = '" + userId + "' and frie_id = '"
				+ firendId + "'";
		OJDBC.executeUpdate(update_friend);
		map.put("status", "SUCCESS");
		return map;
	}

	public Map<String, Object> deleteFriend(String userId, String firendId) {
		Map<String, Object> map = new HashMap<String, Object>();
		String update_friend_one = "delete from friend where user_id = '" + userId
				+ "' and frie_id = '" + firendId + "'";
		String update_friend_two = "delete from friend where user_id = '" + firendId
				+ "' and frie_id = '" + userId + "'";
		
		OJDBC.executeUpdate(update_friend_one);
		OJDBC.executeUpdate(update_friend_two);
		map.put("status", "SUCCESS");
		return map;
	}
}
