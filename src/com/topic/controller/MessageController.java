package com.topic.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.topic.service.MessageService;
import com.topic.tool.MVC;

@Controller
@RequestMapping("/messageController")
public class MessageController {
	private MessageService messageService = new MessageService();

	@RequestMapping("/getFriendMsg")
	public ModelAndView getFriendMsg(String userId, String friendId,
			int pageSize, int pageNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		map = messageService.getFriendMsg(userId, friendId, pageSize, pageNum);
		return MVC.toString(map);
	}

	@RequestMapping("/getFriendMissMsg")
	public ModelAndView getFriendMissMsg(String userId, String friendId,
			int misNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		map = messageService.getFriendMissMsg(userId, friendId, misNum);
		return MVC.toString(map);
	}

	@RequestMapping("/getTopicMsg")
	public ModelAndView getTopicMsg(String topicId, int pageSize, int pageNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		map = messageService.getTopicMsg(topicId, pageSize, pageNum);
		return MVC.toString(map);
	}

	@RequestMapping("/getTopicMissMsg")
	public ModelAndView getTopicMissMsg(String topicId, int misNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		map = messageService.getTopicMissMsg(topicId, misNum);
		return MVC.toString(map);
	}

}
