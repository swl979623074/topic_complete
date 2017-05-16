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

	@RequestMapping("/getTopicListByUserId")
	public ModelAndView getTopicListByUserId(String userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map = topicService.getTopicListByUserId(userId);
		return MVC.toString(map);
	}

	@RequestMapping("/updateMeetWindowAndMissMsg")
	public ModelAndView updateMeetWindowAndMissMsg(String userId, String topicId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map = topicService.updateMeetWindowAndMissMsg(userId, topicId);
		return MVC.toString(map);
	}

	@RequestMapping("/updateWindowStatus")
	public ModelAndView updateWindowStatus(String userId, String topicId,
			String status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map = topicService.updateWindowStatus(userId, topicId, status);
		return MVC.toString(map);
	}

	@RequestMapping("/deleteTopicAboutUser")
	public ModelAndView deleteTopicAboutUser(String userId, String topicId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map = topicService.deleteTopicAboutUser(userId, topicId);
		return MVC.toString(map);
	}
	
	@RequestMapping("/getTopicMsg")
	public ModelAndView getTopicMsg(String topicId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map = topicService.getTopicMsg(topicId);
		return MVC.toString(map);
	}
	
	@RequestMapping("/initAllWindowStatus")
	public ModelAndView initAllWindowStatus(String userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map = topicService.initAllWindowStatus(userId);
		return MVC.toString(map);
	}
	
	@RequestMapping("/addTopicForUser")
	public ModelAndView addTopicForUser(String userId,String topicId){
		Map<String, Object> map = new HashMap<String, Object>();
		map = topicService.addTopicForUser(userId, topicId);
		return MVC.toString(map);
	}
	
	@RequestMapping("/getRecommendTopic")
	public ModelAndView getRecommendTopic(String userId,String typeId,int pageSize,int pageNum){
		Map<String, Object> map = new HashMap<String, Object>();
		map = topicService.getRecommendTopic(userId, typeId,pageSize,pageNum);
		return MVC.toString(map);
	}
}
