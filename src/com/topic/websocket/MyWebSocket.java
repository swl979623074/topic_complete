package com.topic.websocket;

import java.util.HashMap;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import net.sf.json.JSONObject;

import com.topic.tool.DataFormat;

@ServerEndpoint("/mywebsocket")
public class MyWebSocket {
	private static int onlineCount = 0;

	private static Map<String, Session> websocketMap = new HashMap<String, Session>();

	private Session session;

	/**
	 * 连接建立成功调用的方法
	 * 
	 * @param session
	 *            可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
	 */
	@OnOpen
	public void onOpen(Session session) {
		this.session = session;
		addOnlineCount(); // 在线数加1
		System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
	}

	/**
	 * 连接关闭调用的方法
	 */
	@OnClose
	public void onClose() {
		subOnlineCount(); // 在线数减1
		System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
	}

	/**
	 * 收到客户端消息后调用的方法
	 * 
	 * @param message
	 *            客户端发送过来的消息
	 * @param session
	 *            可选的参数
	 */
	@OnMessage
	public void onMessage(String message, Session session) {
		System.out.println("来自客户端的消息:" + message);
		this.handleMessage(message);
	}

	private void handleMessage(String message) {
		JSONObject obj =new JSONObject();
		obj =  DataFormat.toJSON(message);
		String msgType = obj.optString("type");
		switch (msgType) {
		case "onOpen":
			websocketMap.put(obj.optString("userid"), this.session);
			break;
		case "onClose":
			websocketMap.remove(obj.optString("userid"));
			;
			break;
		default:
			break;
		}
		HandleMessage handleMessage;
		handleMessage = (HandleMessage) new HandleMessageToDatabase(message);
		handleMessage.handleMessage(websocketMap);
		
		handleMessage = (HandleMessage) new HandleMessageToClient(message);
		handleMessage.handleMessage(websocketMap);
	}

	/**
	 * 发生错误时调用
	 * 
	 * @param session
	 * @param error
	 */
	@OnError
	public void onError(Session session, Throwable error) {
		System.out.println("发生错误");
		error.printStackTrace();
	}

	public static synchronized int getOnlineCount() {
		return onlineCount;
	}

	public static synchronized void addOnlineCount() {
		MyWebSocket.onlineCount++;
	}

	public static synchronized void subOnlineCount() {
		MyWebSocket.onlineCount--;
	}
}