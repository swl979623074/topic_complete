package com.topic.websocket;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import javax.websocket.Session;

import net.sf.json.JSONObject;

import com.topic.db.OperateJDBC;
import com.topic.tool.DataFormat;
import com.topic.tool.DateFormat;
import com.topic.tool.ImgOperation;

/**
 * 将处理后的消息发送到对应的客户端
 * 
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
	 *            客户端发送的信息，有三种格式 { type:"onMessage", senderid:"1",
	 *            recipientid:"2", recipienttype:'persion/group',
	 *            msg:"hello world" } ,{ "type":"onOpen", "userid":"1" }, {
	 *            "type":"onClose", "userid":"1" }
	 */
	public HandleMessageToClient(String msg) {
		this.JSONObj = DataFormat.toJSON(msg);
		this.message = "";
	}

	/**
	 * 从服务端向客户端发送信息 当用户在线且客户端窗口是打开时，从socket发送信息 其他情况下，在friend表中记录有新消息
	 * 
	 * @param session
	 *            需要接受信息的客户端的session
	 * @param msg
	 *            要发送的信息
	 * 
	 */
	private void sendMsgToPersion(Session senderSession,
			Session recipientSession) {
		Boolean onwindow = this.isOpenWindow(
				this.JSONObj.optString("senderid"),
				this.JSONObj.optString("recipientid"),
				this.JSONObj.optString("recipienttype"));
		
		String userId = this.JSONObj.optString("senderid");
		this.JSONObj.put("userPhoto", getImgBinaryData(userId));
		this.JSONObj.put("time", DateFormat.getCurrentDate_string());
		this.message = this.JSONObj.toString();
		
		try {
			if(senderSession != recipientSession)
				senderSession.getBasicRemote().sendText(this.message);
			if (false == onwindow) {
				String sql = "update friend set frie_missmsg = '1' where user_id = '"
						+ this.JSONObj.optString("recipientid")
						+ "' and frie_id = '"
						+ this.JSONObj.optString("senderid") + "'";
				OJDBC.executeUpdate(sql);
				this.JSONObj.put("userPhoto","");
				this.JSONObj.put("time","");
			}
			if(recipientSession == null) return;
			recipientSession.getBasicRemote().sendText(this.message);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	/**
	 * 向话题群组发送信息，当被接收人在线并且窗口打开时，通过socket发送细信息；否则，在group表中记录有新消息
	 * 
	 * @param session
	 */
	private void sendMsgToGroup(Map<String, Session> map) {
		String groupTopicId = this.JSONObj.optString("recipientid");
		String sql_group = "select group_userid , group_topicid ,user_online , group_openwindow from groups , user where user_id = group_userid and group_topicid = '"
				+ groupTopicId + "'";

		System.out.println(sql_group);
		try {
			String onwindow = "0";
			ResultSet rs = this.OJDBC.executeQuery(sql_group);
			while (rs.next()) {
				String recipientUserId = rs.getString("group_userid");
				onwindow = rs.getString("group_openwindow");

				String userId = this.JSONObj.optString("senderid");
				this.JSONObj.put("userPhoto", getImgBinaryData(userId));
				this.JSONObj.put("time", DateFormat.getCurrentDate_string());
				this.message = this.JSONObj.toString();

				if ("1" != onwindow) {
					String sql = "update groups set group_missmsg = '1' where group_userid = '"
							+ recipientUserId
							+ "' and group_topicid = '"
							+ groupTopicId + "'";
					OJDBC.executeUpdate(sql);
					this.JSONObj.remove("userPhoto");
					this.JSONObj.remove("time");
				}

				try {
					(map.get(recipientUserId)).getBasicRemote().sendText(this.message);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private Boolean isOpenWindow(String senderId, String recipientId,
			String recipientType) {
		Boolean onwindow = false;
		String sql = null;
		if (recipientType.equals("persion")) {
			sql = "select frie_openwindow from friend where user_id = '"
					+ recipientId + "' and frie_id = '" + senderId + "'";
		} else {
			System.out.println("recipientType ERROR: " + recipientType);
		}
		System.out.println(sql);
		try {
			ResultSet rs_onwindow = OJDBC.executeQuery(sql);
			rs_onwindow.next();
			if (1 == Integer.parseInt(rs_onwindow.getString("frie_openwindow"))) {
				onwindow = true;
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

	private String getImgBinaryData(String userId) {
		String imgData = null;
		String sql_select_photo = "select user_photo from user where user_id = '"
				+ userId + "'";
		ResultSet rs = OJDBC.executeQuery(sql_select_photo);
		try {
			rs.next();
			String imgPath = rs.getString("user_photo");
			if (imgPath == null)
				return null;
			String type = imgPath.split("\\.")[1];
			String imgHeader = "data:image/" + type + ";base64,";
			imgData = imgHeader + ImgOperation.getImageBinary(imgPath);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			imgData = null;
			e.printStackTrace();
		}
		return imgData;
	}

	@Override
	public int handleMessage(Map<String, Session> map) {
		String talkType = this.JSONObj.optString("recipienttype");
		switch (talkType) {
		case "persion":
			this.sendMsgToPersion(map.get(this.JSONObj.optString("senderid")),
					map.get(this.JSONObj.optString("recipientid")));
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
