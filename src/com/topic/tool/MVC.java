package com.topic.tool;

import java.util.Map;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

public class MVC {  
    public static ModelAndView toString(Map<String, Object> map){  
        ModelAndView mav = new ModelAndView();  
        MappingJackson2JsonView view = new MappingJackson2JsonView();  
          
        view.setAttributesMap(map);  
          
        mav.setView(view);  
        return mav;       
    }  
}  
