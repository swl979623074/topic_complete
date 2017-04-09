package com.topic.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.topic.db.OperateJDBC;
import com.topic.tool.DateFormat;

public class RegisterService {
	// 数据库操作
	private OperateJDBC OJDBC = new OperateJDBC();

	public Map<String, Object> insertUser(String userAlias, String userAccount,
			String userPasswd, String userSex, String userEmial,
			String userProfession, String userInterest) {
		Map<String, Object> map = new HashMap<String, Object>();
		String userCreateTime = DateFormat.getDateString();

		String sql_find_user = "select user_id from user where user_account = '"
				+ userAccount + "'";
		String sql_inset_user = "insert into user (user_alias,user_account,user_pwd,user_email,user_sex,user_profession,user_createtime) values ('"
				+ userAlias
				+ "','"
				+ userAccount
				+ "','"
				+ userPasswd
				+ "','"
				+ userEmial
				+ "','"
				+ userSex
				+ "','"
				+ userProfession
				+ "','"
				+ userCreateTime + "')";

		ResultSet rs = OJDBC.executeQuery(sql_find_user);
		try {
			rs.last();
			int len = rs.getRow();
			if (len != 0) {
				map.put("status", "FALSE");
				map.put("msg", "user account exist already");
			} else {
				map.put("status", "SUCCESS");
				OJDBC.executeUpdate(sql_inset_user);
				rs = OJDBC.executeQuery(sql_find_user);
				rs.next();
				String user_id = rs.getString("user_id");
				String userInterests[] = userInterest.split(",");
				int len_interest = userInterests.length;
				for (int i = 0; i < len_interest; i++) {
					String sql_inert_interest = "insert into interest (user_id,news_typeid) values ('"
							+ user_id
							+ "',(SELECT type_id from newstype where type_name = '"
							+ userInterests[i] + "'))";
					OJDBC.executeUpdate(sql_inert_interest);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			map.put("status", "FALSE");
			map.put("msg", "SQLException");
			e.printStackTrace();
		}
		return map;
	}

	public Map<String, Object> updateUser(String userId, String userAlias,
			String userSex, String userEmial, String userProfession,
			String userInterest) throws SQLException {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql_update_user = "update user set user_alias = '" + userAlias
				+ "',user_sex = '" + userSex + "',user_emial = '" + userEmial
				+ "',user_profession = '" + userProfession
				+ "' where user_id = '" + userId + "'";
		String sql_delete_interest = "delete from interset where user_id = '"
				+ userId + "'";

		try{
			int key = OJDBC.executeUpdate(sql_update_user);
			if (key > 0) {
				int amount = OJDBC.executeUpdate(sql_delete_interest);
				if (amount > 0) {
					String userInterests[] = userInterest.split(",");
					int len_interest = userInterests.length;
					for (int i = 0; i < len_interest; i++) {
						String sql_inert_interest = "insert into interest (user_id,news_typeid) values ('"
								+ userId + "','" + userInterests[i] + "')";
						OJDBC.executeUpdate(sql_inert_interest);
					}
					
				}
			}
			map.put("status", "SUCCESS");
		}catch(Exception e){
			map.put("status", "FALSE");
		}
		return map;
	}
}
