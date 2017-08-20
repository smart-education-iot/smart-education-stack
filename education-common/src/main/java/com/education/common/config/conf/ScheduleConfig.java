package com.education.common.config.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;

/**
 * 定时任务配置类
 * @EnableAsync	支持异步调用(主方法上增加)
 * @author 		jamesli
 * @version		1.0
 * @date		2016-04-21
 */
//@Configuration
//@EnableScheduling // 启用定时任务
public class ScheduleConfig {
	//log
    @SuppressWarnings("unused")
	private final Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 每20秒执行一次
     */
    //@Scheduled(cron = "0/20 * * * * ?")
    public void scheduler() {
        //logger.info(">>>>>>>>>>>>> scheduled ... ");
    }

}