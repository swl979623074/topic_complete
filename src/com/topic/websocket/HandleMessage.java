package com.topic.websocket;

import java.util.Map;

import javax.websocket.Session;
/**
 * 对websocket消息的处理
 * @author SWL
 *
 */
public interface HandleMessage {
	public int handleMessage(Map<String, Session> map);
}
