package com.topic.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.topic.service.NewsService;
import com.topic.tool.MVC;

@Controller
@RequestMapping("/newsController")
public class NewsController {
	private NewsService newsService = new NewsService();

	@RequestMapping("/getNewsByTypeName")
	public ModelAndView getNewsByTypeName(String typeName, int pageSize,
			int pageNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		map = newsService.getNewsByTypeName(typeName,pageSize,pageNum);
		return MVC.toString(map);
	}
}
