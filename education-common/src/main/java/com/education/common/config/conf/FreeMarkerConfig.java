package com.education.common.config.conf;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

/**
 * Freemarker tags 
 * @author jamesli
 */
@Configuration
//@ConfigurationProperties(value = "spring.freemarker", ignoreNestedProperties = false)
public class FreeMarkerConfig extends FreeMarkerAutoConfiguration{
	/**
	 * Constructor
	 * @param applicationContext
	 * @param properties
	 */
	public FreeMarkerConfig(ApplicationContext applicationContext, FreeMarkerProperties properties) {
		super(applicationContext, properties);
	}
	/**
	 * Date Format
	 */
	private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd";
	/**
	 * Date Time Format
	 */
	private static final String DATETIME_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";
	/**
	 * freemarker configuration
	 */
    @Autowired
    private freemarker.template.Configuration configuration;
    
    /**
     * 自动导入Map
     */
    private Map<String,String> autoImportMap = new HashMap<String,String>();
    /**
     * 设置共享变量
     */
    @PostConstruct
    public void  setSharedVariable(){
        configuration.setDateFormat(DATE_FORMAT_PATTERN);
        configuration.setDateTimeFormat(DATETIME_FORMAT_PATTERN);
        //注入资源宏文件
        autoImportMap.put("education", "common/ftl/education.ftl");
        configuration.setAutoImports(autoImportMap);
    }
}