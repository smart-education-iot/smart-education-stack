package com.education.portal;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 管理入口
 * @author  jamesli
 * @version 1.0
 * @date 	2017-07-04
 */
@ServletComponentScan
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages={"com.education"})
public class Application{
	/**
	 * 应用入口
	 * @param args
	 */
    public static void main( String[] args ){
    	SpringApplication.run(Application.class, args);
    }
}
