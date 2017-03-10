package com.topic.websocket;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import net.sf.json.JSONObject;

public class HandleMessage {

	private JSONObject JSONObj;

	/**
	 * 将客户端消息解析为JSON数据
	 * 
	 * @param msg
	 * 
	 * 客户端发送的信息，有三种格式 { type:"onMessage", senderId:"1",
	 * recipientId:"2", recipienttype:'persion/group',
	 * msg:"hello world" } ,{ "type":"onOpen", "userid":"1" }, {
	 * "type":"onClose", "userid":"1" }
	 */
	public HandleMessage(String msg) {
		this.JSONObj = JSONObject.fromObject(msg);
	}

	private String getMsgType() {
		return this.JSONObj.getString("type");
	}

	private int handleOpenMessage(Statement statement) {
		int key = 0;
		try {
			String sql = "update user set user_online = '1' where user_id ='"
					+ this.JSONObj.getString("userid") + "'";
			key = statement.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return key;
	}

	private int handleCloseMessage(Statement statement) {
		int key = 0;
		try {
			String sql = "update user set user_online = '0' where user_id ='"
					+ this.JSONObj.getString("userid") + "'";
			key = statement.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return key;
	}

	private int handleTalkMessage(Statement statement) {
		int key = 0;
		String talkType = this.JSONObj.getString("recipienttype");
		String sender = this.JSONObj.getString("senderId");
		String recipient = this.JSONObj.getString("recipientId");
		String msg = this.JSONObj.getString("recipientId");
		Date date = new Date();
		try {
			String sql = null;
			switch (talkType) {
			case "persion":
				sql = "insert into conversation ('user_come','user_to','conv_content','conv_time') values ("
						+ sender
						+ ","
						+ recipient
						+ ","
						+ msg
						+ ","
						+ date
						+ ",)";
				break;
			case "group":
				sql = "insert into meeting ('meet_userid','meet_groupid','meet_content','conv_time') values ("
						+ sender
						+ ","
						+ recipient
						+ ","
						+ msg
						+ ","
						+ date
						+ ",)";
				break;
			default:
				break;
			}

			key = statement.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return key;
	}

	/**
	 * 返回为0则操作失败，非0为成功
	 * 
	 * @param statement
	 * @return
	 */
	public int handle(Statement statement) {
		String msgType = this.getMsgType();
		int key = 0;
		switch (msgType) {
		case "onOpen":
			key = this.handleOpenMessage(statement);
			break;
		case "onClose":
			key = this.handleCloseMessage(statement);
			break;
		case "onMessage":
			key = this.handleTalkMessage(statement);
			break;
		default:
			break;
		}
		return key;
	}
}
