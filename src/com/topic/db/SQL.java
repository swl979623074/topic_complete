package com.topic.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import com.topic.tool.FileOperation;

public class SQL {

	private String dbtype = "mysql";
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

	public static void main(String[] args){
		SQL sql = new SQL();
		Connection conn = sql.getConn();
		String str = "select * from user";
		Statement sta;
		try {
			sta = conn.createStatement();
			ResultSet rs = sta.executeQuery(str);
			while(rs.next()){
				System.out.println(rs.getString("user_account"));
			}
			rs.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
