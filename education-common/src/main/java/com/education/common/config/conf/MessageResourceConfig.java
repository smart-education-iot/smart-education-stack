package com.education.common.config.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * 国际化资源配置
 * @author jamesli
 * @date 2016/3/30
 */
@Configuration
@ConfigurationProperties(value = "spring.messages", ignoreNestedProperties = false)
public class MessageResourceConfig {

	private String basename;
	private int cacheSeconds;
	private String encoding;
	
	public String getBasename() {
		return basename;
	}

	public void setBasename(String basename) {
		this.basename = basename;
	}

	public int getCacheSeconds() {
		return cacheSeconds;
	}

	public void setCacheSeconds(int cacheSeconds) {
		this.cacheSeconds = cacheSeconds;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

    /**
     * 重新加载配置
     * @return
     */
    @Bean
    public ReloadableResourceBundleMessageSource messageSource(){
    	ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
    	if(this.getBasename().indexOf(",")==1){
    		messageSource.setBasename(this.getBasename());
    	}else{
    		messageSource.setBasenames(this.getBasename().split(","));
    	}
    	messageSource.setCacheSeconds(this.getCacheSeconds());
    	messageSource.setUseCodeAsDefaultMessage(true);
    	messageSource.setDefaultEncoding(this.getEncoding());
    	return messageSource;
    }
}
