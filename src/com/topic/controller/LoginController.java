package com.topic.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.topic.service.LoginService;
import com.topic.tool.MVC;

@Controller
@RequestMapping("/loginController")
public class LoginController {
	private LoginService loginService = new LoginService();

	@RequestMapping("/login")
	public ModelAndView login(String userName,String passwd) {
		Map<String, Object> map = new HashMap<String, Object>();
		map = loginService.findUserAndUpdateOnline(userName, passwd);
		return MVC.toString(map);
	}

	@RequestMapping("/exit")
	public ModelAndView exit() {
		Map<String, Object> map = new HashMap<String, Object>();

		return MVC.toString(map);
	}
}
