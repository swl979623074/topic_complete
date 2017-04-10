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
	
	@RequestMapping("/updateUserPwd")
	public ModelAndView updateUserPwd(String userId,String oldPwd,String newPwd){
		Map<String, Object> map = new HashMap<String, Object>();
		map = userService.updateUserPwd(userId, oldPwd, newPwd);
		return MVC.toString(map);
	}
}
