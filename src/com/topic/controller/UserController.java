package com.topic.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.topic.service.UserService;
import com.topic.tool.MVC;

@Controller
@RequestMapping("/userController")
public class UserController {
	private UserService userService = new UserService();
	
	@RequestMapping("/getUserMsg")
	public ModelAndView getUserMsg(String userId){
		Map<String, Object> map = new HashMap<String, Object>();
		map = userService.getUserMsg(userId);
		return MVC.toString(map);
	}
	
	@RequestMapping("/getUserMsgByAccount")
	public ModelAndView getUserMsgByAccount(String userAccount){
		Map<String, Object> map = new HashMap<String, Object>();
		map = userService.getUserMsgByAccount(userAccount);
		return MVC.toString(map);
	}
	
	
	@RequestMapping("/updateUserPwd")
	public ModelAndView updateUserPwd(String userId,String oldPwd,String newPwd){
		Map<String, Object> map = new HashMap<String, Object>();
		map = userService.updateUserPwd(userId, oldPwd, newPwd);
		return MVC.toString(map);
	}
	
	@RequestMapping("/getFriend")
	public ModelAndView getFriend(String userId){
		Map<String, Object> map = new HashMap<String, Object>();
		map = userService.getFriendByUserId(userId);
		return MVC.toString(map);
	}
	
	@RequestMapping("/addFriend")
	public ModelAndView addFriend(String userId, String firendAccount){
		Map<String, Object> map = new HashMap<String, Object>();
		map = userService.addFriend(userId, firendAccount);
		return MVC.toString(map);
	}
	
	@RequestMapping("/updateFriendAlias")
	public ModelAndView updateFriendAlias(String userId, String firendId , String aliasName){
		Map<String, Object> map = new HashMap<String, Object>();
		map = userService.updateFriendAlias(userId, firendId, aliasName);
		return MVC.toString(map);
	}
	
	@RequestMapping("/deleteFriend")
	public ModelAndView deleteFriend(String userId, String firendId){
		Map<String, Object> map = new HashMap<String, Object>();
		map = userService.deleteFriend(userId, firendId);
		return MVC.toString(map);
	}
}
