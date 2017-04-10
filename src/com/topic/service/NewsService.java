package com.topic.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.topic.db.OperateJDBC;

public class NewsService {
	private OperateJDBC OJDBC = new OperateJDBC();
	
	public  Map<String, Object> getNewsById(String typeId){
		Map<String, Object> map = new HashMap<String, Object>();
		
		return map;
	}
}
