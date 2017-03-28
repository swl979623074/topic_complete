package com.topic.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.topic.db.OperateJDBC;

public class NewsTypeService {
	private OperateJDBC OJDBC = new OperateJDBC();
	
	public Map<String, Object> getNewsType(){
		Map<String,Object> map = new HashMap<String,Object>();
		
		String sql_get_newstype = "SELECT type_name,type_alias FROM newstype";
		
		ResultSet rs = OJDBC.executeQuery(sql_get_newstype);
		try {
			ArrayList<Map> list = new ArrayList<Map>();
			while(rs.next()){
				Map<String,String> typeMsg = new HashMap<String,String>();
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
