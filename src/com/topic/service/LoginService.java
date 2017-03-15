package com.topic.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.topic.db.OperateJDBC;

public class LoginService {
	// 数据库操作
	private OperateJDBC OJDBC = new OperateJDBC();

	/**
	 * 查找用户并修改用户的在线状态
	 * 
	 * @param userName
	 *            用户登录名
	 * @param passwd
	 *            用户密码
	 * @return {userId:"",userOnline:"",status:""}
	 */
	public Map<String, Object> findUserAndUpdateOnline(String userName,
			String passwd) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql_update_online = "update user set user_online = '1' where user_account = '"
				+ userName + "' and user_pwd = '" + passwd + "'";
		String sql_find_user = "select user_id,user_account,user_alias,user_online from user where user_account = '"
				+ userName + "' and user_pwd = '" + passwd + "'";

		// 当语句执行成功时返回1（无论是否更新，这点与数据库不同，数据库是成功更新后返回非0，未更新返回0）,失败时返回0
		int key = OJDBC.executeUpdate(sql_update_online);

		if (key == 1) {
			ResultSet rs = OJDBC.executeQuery(sql_find_user);
			try {
				rs.next();
				map.put("userId", rs.getString("user_id"));
				map.put("userAccount", rs.getString("user_account"));
				map.put("userAlias", rs.getString("user_alias"));
				map.put("userOnline", rs.getString("user_online"));
				map.put("status", "SUCCESS");
				return map;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				map.put("userId", "FALSE");
			}
		} else {
			map.put("status", "FALSE");
		}
		return map;
	}
}
