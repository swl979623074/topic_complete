package com.topic.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.topic.tool.MVC;

@Controller
@RequestMapping("/test")
public class testMVC {
	
	@RequestMapping("/getnews")  
	public ModelAndView getNews(){
		 Map<String, Object> map = new HashMap<String, Object>();  
		 map.put("status", "success news");    
		 return MVC.toString(map);  
	}
}
