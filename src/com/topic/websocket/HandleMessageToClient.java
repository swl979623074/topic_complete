package com.topic.websocket;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import javax.websocket.Session;

import net.sf.json.JSONObject;

import com.topic.db.OperateJDBC;
import com.topic.tool.DataFormat;
/**
 * 将处理后的消息发送到对应的客户端
 * @author SWL
 *
 */
public class HandleMessageToClient implements HandleMessage {

	private String message;
	
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
	public HandleMessageToClient(String msg) {
		this.JSONObj = DataFormat.toJSON(msg);
		this.message = msg;
	}


	/**
	 * 从服务端向客户端发送信息 当用户在线且客户端窗口是打开时，从socket发送信息 其他情况下，在firend表中记录有新消息
	 * 
	 * @param session
	 *            需要接受信息的客户端的session
	 * @param msg
	 *            要发送的信息
	 * 
	 */
	private void sendMsgToPersion(Session session) {
		Boolean online = this.isOnLine(this.JSONObj.optString("recipientid"));
		Boolean onwindow = this.isOpenWindow(
				this.JSONObj.optString("senderid"),
				this.JSONObj.optString("recipientid"),
				this.JSONObj.optString("recipienttype"));

		if (true == online && true == onwindow) {
			try {
				session.getBasicRemote().sendText(this.message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			String sql = "update firend set fire_missmsg = '1' where user_id = '"
					+ this.JSONObj.optString("senderId")
					+ "' and fire_id = '"
					+ this.JSONObj.optString("recipientId") + "'";
			OJDBC.executeUpdate(sql);
		}
	}

	/**
	 * 向话题群组发送信息，当被接收人在线并且窗口打开时，通过socket发送细信息；否则，在group表中记录有新消息
	 * 
	 * @param session
	 */
	private void sendMsgToGroup(Map<String,Session> map) {
		String groupTopicId = this.JSONObj.optString("recipientId");
		String sql_group = "select group_userid , group_topicid ,user_online , group_openwindow from group , user where user_id = group_userid and group_topicid = '"
				+ groupTopicId + "'";

		try {
			String online = "0", onwindow = "0";
			ResultSet rs = this.OJDBC.executeQuery(sql_group);
			while (rs.next()) {
				String recipientUserId = rs.getString("group_userid");
				online = rs.getString("user_online");
				onwindow = rs.getString("group_openwindow");

				if ("1" == online && "1" == onwindow) {
					try {
						(map.get(this.JSONObj.optString(recipientUserId))).getBasicRemote().sendText(this.message);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					String sql = "update group set group_missmsg = '1' where group_userid = '"
							+ recipientUserId
							+ "' and group_topicid = '"
							+ groupTopicId + "'";
					OJDBC.executeUpdate(sql);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 查询某个用户是否在线
	 * 
	 * @param userId
	 *            被查询用户id
	 * @return true false
	 */
	private Boolean isOnLine(String userId) {
		Boolean online = false;
		try {
			String sql_online = "select user_online from user where user_id = '"
					+ userId + "'";
			ResultSet rs_online = OJDBC.executeQuery(sql_online);
			if (1 == rs_online.getRow() && rs_online.next()) {
				if (1 == Integer.parseInt(rs_online.getString("user_online"))) {
					online = true;
				}
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return online;
	}

	private Boolean isOpenWindow(String senderId, String recipientId,
			String recipientType) {
		Boolean onwindow = false;
		String sql = null;
		if (recipientType.equals("persion")) {
			sql = "select fire_openwindow from firend where user_id = '"
					+ senderId + "' and fire_id = '" + recipientId + "'";
		} else if (recipientType.equals("group")) {
			sql = "select group_openwindow from group where group_userid = '"
					+ senderId + "' and group_topicid = '" + recipientId + "'";
		} else {
			System.out.println("recipientType ERROR: " + recipientType);
		}

		try {
			ResultSet rs_onwindow = OJDBC.executeQuery(sql);
			if (1 == rs_onwindow.getRow() && rs_onwindow.next()) {
				if (1 == Integer.parseInt(rs_onwindow.getString("user_online"))) {
					onwindow = true;
				}
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return onwindow;
	}

	@Override
	public int handleMessage(Map<String,Session> map) {
		String talkType = this.JSONObj.optString("recipienttype");
		switch (talkType) {
		case "persion":
			this.sendMsgToPersion(map.get(this.JSONObj.optString("recipientid")));
			;
			break;
		case "group":
			this.sendMsgToGroup(map);
			;
			break;
		default:
			break;
		}
		return 1;
	}
}
