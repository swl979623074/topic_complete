package com.topic.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.topic.service.ProfessionService;
import com.topic.tool.MVC;

@Controller
@RequestMapping("/professionController")
public class ProfessionController {
	private ProfessionService professionService = new ProfessionService();

	@RequestMapping("/getProfession")
	public ModelAndView getProfession(String userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map = professionService.getProfessionByUserId(userId);
		return MVC.toString(map);
	}
}
