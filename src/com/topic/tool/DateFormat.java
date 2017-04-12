package com.topic.tool;

import java.text.SimpleDateFormat;

public class DateFormat {

	public static String getCurrentDate_string() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Date date = new java.util.Date();
		String str = sdf.format(date);
		return str;
	}

	public static String getDelayDate_string(String delay) {
		int key = Integer.parseInt(delay);
		int delayTime = key * 60 * 60 * 1000;

		long curren = System.currentTimeMillis();
		curren += delayTime;
		java.util.Date date = new java.util.Date(curren);
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(date);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(getDelayDate_string("24"));
	}

}
