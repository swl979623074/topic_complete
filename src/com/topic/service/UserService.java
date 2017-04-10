package com.topic.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.topic.db.OperateJDBC;

public class UserService {
	private OperateJDBC OJDBC = new OperateJDBC();

	public Map<String, Object> getUserMsg(String userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql_getUser = "select * from user where user_id = '"+userId+"'";
		ResultSet rs = OJDBC.executeQuery(sql_getUser);
		try {
			rs.next();
			Map<String, String> userMap = new HashMap<String, String>();
			
			userMap.put("userAccount", rs.getString("user_account"));
			userMap.put("userAlias", rs.getString("user_alias"));
			userMap.put("userEmail", rs.getString("user_email"));
			userMap.put("userSex", rs.getString("user_sex"));
			userMap.put("userCreatetime", rs.getString("user_createtime"));
			userMap.put("userDegree", rs.getString("user_degree"));
			userMap.put("userphoto", rs.getString("user_photo"));
			
			map.put("status","SUCCESS");
			map.put("userMsg", userMap);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("status", "FALSE");
		}
		
		return map;
	}
	
	public Map<String, Object> updateUserPwd(String userId,String oldPwd,String newPwd){
		Map<String, Object> map = new HashMap<String, Object>();
		String sql_update_userPwd = "update user set user_pwd = '"+newPwd+"' where user_id = '"+userId+"' and user_pwd = '"+oldPwd+"'";
		int len = OJDBC.executeUpdate(sql_update_userPwd);
		if(len == 1){
			map.put("status","SUCCESS");
		}else{
			map.put("status","FALSE");
		}
		return map;
	}
}
