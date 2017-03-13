package com.topic.websocket;

import java.util.Date;
import java.util.Map;

import javax.websocket.Session;

import net.sf.json.JSONObject;

import com.topic.db.OperateJDBC;
import com.topic.tool.DataFormat;
/**
 * 将处理后的消息存储到数据库
 * @author SWL
 *
 */
public class HandleMessageToDatabase implements HandleMessage {

	private JSONObject JSONObj;

	private OperateJDBC OJDBC = new OperateJDBC();

	/**
	 * 将客户端消息解析为JSON数据
	 * 
	 * @param msg
	 * 
	 *            客户端发送的信息，有三种格式 { type:"onMessage", senderId:"1",
	 *            recipientId:"2", recipienttype:'persion/group',
	 *            msg:"hello world" } ,{ "type":"onOpen", "userid":"1" }, {
	 *            "type":"onClose", "userid":"1" }
	 */
	public HandleMessageToDatabase(String msg) {
		this.JSONObj = DataFormat.toJSON(msg);
	}

	private int handleOpenMessage() {
		int key = 0;
		String sql = "update user set user_online = '1' where user_id ='"
				+ this.JSONObj.getString("userid") + "'";
		key = OJDBC.executeUpdate(sql);
		return key;
	}

	private int handleCloseMessage() {
		int key = 0;
		String sql = "update user set user_online = '0' where user_id ='"
				+ this.JSONObj.getString("userid") + "'";
		key = OJDBC.executeUpdate(sql);
		return key;
	}

	private int handleTalkMessageToDB() {
		int key = 0;
		String talkType = this.JSONObj.getString("recipienttype");
		String sender = this.JSONObj.getString("senderId");
		String recipient = this.JSONObj.getString("recipientId");
		String msg = this.JSONObj.getString("msg");
		Date date = new Date();
		String sql = null;
		switch (talkType) {
		case "persion":
			sql = "insert into conversation ('user_come','user_to','conv_content','conv_time') values ("
					+ sender + "," + recipient + "," + msg + "," + date + ",)";
			break;
		case "group":
			sql = "insert into meeting ('meet_userid','meet_groupid','meet_content','conv_time') values ("
					+ sender + "," + recipient + "," + msg + "," + date + ",)";
			break;
		default:
			break;
		}
		key = OJDBC.executeUpdate(sql);
		return key;
	}

	/**
	 * 对外部开放的信息处理接口 信息类型为onOpen、onClose、onMessage 返回为0则操作失败，非0为成功
	 * 
	 * @param statement
	 * @return
	 */
	@Override
	public int handleMessage(Map<String,Session> map) {
		String msgType = this.JSONObj.getString("type");
		int key = 0;
		switch (msgType) {
		case "onOpen":
			key = this.handleOpenMessage();
			break;
		case "onClose":
			key = this.handleCloseMessage();
			break;
		case "onMessage":
			key = this.handleTalkMessageToDB();
			break;
		default:
			break;
		}
		return key;

	}

}
