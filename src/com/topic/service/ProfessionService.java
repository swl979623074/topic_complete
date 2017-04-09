package com.topic.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.topic.db.OperateJDBC;

public class ProfessionService {
	private OperateJDBC OJDBC = new OperateJDBC();
	
	public Map<String, Object> getProfessionByUserId(String userId) {
		Map<String, Object> map = new HashMap<String, Object>();

		String sql_get_newstype = "SELECT user_profession FROM user WHERE user.user_id = '"+userId+"'";

		ResultSet rs = OJDBC.executeQuery(sql_get_newstype);
		try {
			rs.next();
			map.put("status", "SUCCESS");
			map.put("list", rs.getString("user_profession").split(","));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			map.put("status", "FALSE");
			map.put("msg", "SQLException");
			e.printStackTrace();
		}
		return map;
	}
}
