package com.topic.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.topic.service.TopicService;
import com.topic.tool.MVC;

@Controller
@RequestMapping("/topicController")
public class TopicController {
	private TopicService topicService = new TopicService();

	@RequestMapping("/insertOneTopic")
	public ModelAndView insertOneTopic(String userId, String title,
			String description, String stillTime, String url, String degree,
			String typeId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map = topicService.insertOneTopic(userId, title, description,
				stillTime, url, degree, typeId);
		return MVC.toString(map);

	}
}
