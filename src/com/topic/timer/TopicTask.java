package com.topic.timer;

import com.topic.db.OperateJDBC;

public class TopicTask {
	private OperateJDBC OJDBC = new OperateJDBC();
	
	public int closeTopic(){
		String sql_close_topic = "update topic set topi_closed = 1 where topi_id = any(select topi_id from (select * from topic) as b where now() > topi_stilltime and topi_closed = 0)";
		int num = OJDBC.executeUpdate(sql_close_topic);
		return num;
	}
}
