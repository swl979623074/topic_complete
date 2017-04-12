package com.topic.controller;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.topic.timer.TopicTask;

@Component
public class TimerTask {
	private TopicTask TopicTask = new TopicTask();
	/**  
	 * 描述：定期关闭满足关闭条件的话题
     * 启动时执行一次，之后每隔1分钟执行一次  
     */    
    @Scheduled(fixedRate = 1000*60*1)   
    public void closeTopic(){  
    	int num = TopicTask.closeTopic();
    	System.out.println("关闭了"+num+"个话题");
        //System.out.println("启动时执行一次，之后每隔2秒执行一次  ");  
    	
    }  
    
    @Scheduled(cron = "0 0 3 * * ?")  
    public void job2() {  
       // System.out.println("任务进行中。。。");  
    }  
}
