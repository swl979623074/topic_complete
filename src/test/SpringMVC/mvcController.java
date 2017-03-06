package test.SpringMVC;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@Controller
@RequestMapping("/mvc")
public class mvcController {
 
    @RequestMapping("/hello")
    public String hello(){        
        return "hello";
    }
    
    
    @RequestMapping("/getList")  
    public ModelAndView getList() {  
    	 ModelAndView mav = new ModelAndView();  
         MappingJackson2JsonView view = new MappingJackson2JsonView();  
           
         Map<String, Object> map = new HashMap<String, Object>();  
         map.put("status", "success");  
         view.setAttributesMap(map);  
           
         mav.setView(view);  
         
           
        return mav;  
    }  
}
