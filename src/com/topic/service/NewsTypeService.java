package com.topic.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.topic.db.OperateJDBC;

public class NewsTypeService {
	private OperateJDBC OJDBC = new OperateJDBC();

	/**
	 * 描述：从newstype表中获取所有新闻类型
	 * 
	 * @return map
	 */
	public Map<String, Object> getNewsType() {
		Map<String, Object> map = new HashMap<String, Object>();

		String sql_get_newstype = "SELECT type_id,type_name,type_alias FROM newstype";

		ResultSet rs = OJDBC.executeQuery(sql_get_newstype);
		try {
			ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
			while (rs.next()) {
				Map<String, String> typeMsg = new HashMap<String, String>();
				typeMsg.put("typeId", rs.getString("type_id"));
				typeMsg.put("typeName", rs.getString("type_name"));
				typeMsg.put("typeAlias", rs.getString("type_alias"));
				list.add(typeMsg);
			}
			map.put("status", "SUCCESS");
			map.put("list", list);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			map.put("status", "FALSE");
			map.put("msg", "SQLException");
			e.printStackTrace();
		}

		return map;
	}

	/**
	 * 描述：根据用户id，从newstype和interest表中获取新闻类型
	 * 
	 * @param userId
	 *            用户id
	 * @return map
	 */
	public Map<String, Object> getNewsTypeByUserId(String userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql_getnewType_byUserId = "select newstype.type_id,newstype.type_name,newstype.type_alias from interest,newstype where interest.user_id = "
				+ userId + " and interest.news_typeid = newstype.type_id ";
	
		ResultSet rs = OJDBC.executeQuery(sql_getnewType_byUserId);
		try {
			ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
			while (rs.next()) {
				Map<String, String> typeMsg = new HashMap<String, String>();
				typeMsg.put("typeId", rs.getString("type_id"));
				typeMsg.put("typeName", rs.getString("type_name"));
				typeMsg.put("typeAlias", rs.getString("type_alias"));
				list.add(typeMsg);
			}
			map.put("status", "SUCCESS");
			map.put("list", list);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			map.put("status", "FALSE");
			map.put("msg", "SQLException");
			e.printStackTrace();
		}

		return map;
	}
}
