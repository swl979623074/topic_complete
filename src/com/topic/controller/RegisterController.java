package com.topic.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.topic.service.RegisterService;
import com.topic.tool.MVC;

@Controller
@RequestMapping("/registerController")
public class RegisterController {
	private RegisterService registeService = new RegisterService();

	@RequestMapping("/registeUser")
	public ModelAndView registerUser(String userAlias, String userAccount,
			String userPasswd, String userSex, String userEmial,
			String userProfession, String userInterest) {
		Map<String, Object> map = new HashMap<String, Object>();
		map = registeService.insertUser(userAlias, userAccount, userPasswd,
				userSex, userEmial, userProfession, userInterest);
		return MVC.toString(map);
	}

	@RequestMapping("/updateuserMsg")
	public ModelAndView updateuserMsg(String userId, String userAlias,
			String userSex, String userEmial, String userProfession,
			String userInterest) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			map = registeService.updateUser(userId,userAlias,
					userSex, userEmial, userProfession, userInterest);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return MVC.toString(map);
	}
}
