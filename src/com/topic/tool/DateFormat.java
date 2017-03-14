package com.topic.tool;

import java.text.SimpleDateFormat;

public class DateFormat {

	public static String getDateString(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		java.util.Date date=new java.util.Date();  
		String str=sdf.format(date);  
		return str;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(getDateString());
	}

}
