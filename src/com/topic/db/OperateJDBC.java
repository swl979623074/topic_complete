package com.topic.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OperateJDBC {

	private JDBC jdbc = new JDBC();
	
	private Connection conn = jdbc.getConn();
	
	private Statement statement;
	/**
	 * @param args
	 */
	
	@SuppressWarnings("finally")
	public ResultSet executeQuery(String sql){
		ResultSet rs = null;
		try {
			statement = conn.createStatement();
			rs = statement.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			return rs;
		}
	}
	
	@SuppressWarnings("finally")
	public int executeUpdate(String sql){
		int key = 0;
		try {
			statement = conn.createStatement();
			key = statement.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("EXEC ERROR: " + sql);
			e.printStackTrace();
		}finally{
			return key;
		}
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
