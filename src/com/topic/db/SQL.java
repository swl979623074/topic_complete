package com.topic.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.topic.tool.FileOperation;

public class SQL {

	private String dbtype = "mongodb";
	private Map<String, String> map = new HashMap<String, String>();

	public SQL() {
		try {
			FileOperation fileOperation = new FileOperation();
			map = fileOperation.getData(dbtype);
		} catch (Exception e) {
			System.out.println("Class MongoDB Initialization ERROR");
		}

	}
	
	public Connection getConn(){
		String driver = map.get("driver");
	    String url = map.get("url");
	    String username = map.get("username");
	    String password = map.get("password");
	    Connection conn = null;
	    try {
	        Class.forName(driver); //classLoader,加载对应驱动
	        conn = (Connection) DriverManager.getConnection(url, username, password);
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return conn;
	}

}
