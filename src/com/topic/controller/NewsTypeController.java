package com.topic.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.topic.service.NewsTypeService;
import com.topic.tool.MVC;

@Controller
@RequestMapping("/newsTypeController")
public class NewsTypeController {
	private NewsTypeService newsTypeService = new NewsTypeService();

	@RequestMapping("/getNewsType")
	public ModelAndView getNewsType() {
		Map<String, Object> map = new HashMap<String, Object>();
		map = newsTypeService.getNewsType();
		return MVC.toString(map);
	}
}
