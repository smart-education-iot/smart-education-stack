package com.education.common.config.aop;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
  
/** 
 * 自定义注解 拦截Service
 * @company  浪潮软件股份有限公司 
 * @author jamesli
 * @version 1.0 
 * @date 2015-07-03 
 * @description 类的方法描述注解 
 */  
@Target({ElementType.PARAMETER, ElementType.METHOD})  
@Retention(RetentionPolicy.RUNTIME)     
@Documented    
@Inherited    
public @interface ServiceLogAction {  
    /**
     * 应用编码
     * @return
     */
	public String code() default "";
	/**
	 * 日志描述信息
	 * @return
	 */
    public String description() default "";   
    
}  