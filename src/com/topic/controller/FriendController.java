package com.topic.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.topic.service.FriendService;
import com.topic.tool.MVC;

@Controller
@RequestMapping("/friendController")
public class FriendController {
	private FriendService friendService = new FriendService();

	@RequestMapping("/updateOpenWindowAndMissMsg")
	public ModelAndView updateOpenWindowAndMissMsg(String userId,
			String friendId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map = friendService.updateOpenWindowAndMissMsg(userId, friendId);
		return MVC.toString(map);
	}

	@RequestMapping("/closeWindow")
	public ModelAndView closeWindow(String userId, String friendId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map = friendService.closeWindow(userId, friendId);
		return MVC.toString(map);
	}

	@RequestMapping("/updateRemark")
	public ModelAndView updateRemark(String userId, String friendId,
			String remark) {
		Map<String, Object> map = new HashMap<String, Object>();
		map = friendService.updateRemark(userId, friendId, remark);
		return MVC.toString(map);
	}

	@RequestMapping("/deleteFriend")
	public ModelAndView deleteFriend(String userId, String friendId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map = friendService.deleteFriend(userId, friendId);
		return MVC.toString(map);
	}
}
