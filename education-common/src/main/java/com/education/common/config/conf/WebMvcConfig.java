package com.education.common.config.conf;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ThemeResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.theme.CookieThemeResolver;

import com.education.common.utils.DateTimeUtils;
/**
 * WebMvcConfig
 * @author lijinfeng
 * @date 2016-4-20
 * @version 1.0
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
	/**
	 * Mybatis日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(WebMvcConfig.class);

//  @Autowired
//  private SecurityInterceptor interceptor;
	
//	/**
//	 * 用于处理编码问题
//	 * @return
//	 */
//    @Bean
//    public Filter characterEncodingFilter() {
//        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
//        characterEncodingFilter.setEncoding("UTF-8");
//        characterEncodingFilter.setForceEncoding(true);
//        return characterEncodingFilter;
//    }
	/**
	 * 定制URL匹配规则
	 * 1. setUseSuffixPatternMatch(false)不使用后缀模式匹配（如.*,.do,.html）
	 * 2. setUseTrailingSlashMatch(true)系统不区分URL的最后一个字符是否是斜杠/
	 */
	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
	    configurer.setUseSuffixPatternMatch(false).setUseTrailingSlashMatch(true);
	}

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }
    
    /**
     * 国际化解析器
     * @return
     */
    @Bean
    public LocaleResolver localeResolver(){
    	//CookieLocaleResolver slr = new CookieLocaleResolver();
    	SessionLocaleResolver slr = new SessionLocaleResolver();
    	slr.setDefaultLocale(Locale.CHINA);
    	return slr;
    }
    
    /**
     * 主题解析器
     * @return
     */
    @Bean
    public ThemeResolver themeResolver(){
    	CookieThemeResolver slr = new CookieThemeResolver();
    	slr.setCookieName("theme");
    	slr.setCookiePath("/");
    	slr.setCookieMaxAge(31536000);
    	slr.setDefaultThemeName("default");
    	return slr;
    }
    
	
//	@Autowired
//	UserHandlerMethodArgumentResolver currentUserHandlerMethodArgumentResolver;
	
//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/").setViewName("index");
//        registry.addViewController("/error").setViewName("error");
//    }
//    
//    @Override
//    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers){
//    	//argumentResolvers.add(currentUserHandlerMethodArgumentResolver);
//    }
    /**
     * 注册拦截器(多个拦截器组成一个拦截器链)
     * 1. addPathPatterns 用于添加拦截规则
     * 2. excludePathPatterns 用户排除拦截
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//    	 LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
//       localeChangeInterceptor.setParamName("language");
//       registry.addInterceptor(localeChangeInterceptor).addPathPatterns("/**");
//       
//       ThemeChangeInterceptor themeChangeInterceptor = new ThemeChangeInterceptor();
//       themeChangeInterceptor.setParamName("theme");
//       registry.addInterceptor(themeChangeInterceptor).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
    

    /**
     * 向页面添加css
     *
     * @param cssPath
     * @return
     */
    @SuppressWarnings("deprecation")
	public static String addCss(String cssPath) {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            return "<link rel=\"stylesheet\" type=\"text/css\" href=\"" + request.getContextPath() + "/" + cssPath + "?v=" + DateTimeUtils.getCurrentYearLast().getYear() + "\" />";
        } catch (Exception e) {
            logger.error("addCss Error:", e);
        }
        return "";
    }

    /**
     * 向页面添加javascript
     *
     * @param jsPath
     * @return
     */
    @SuppressWarnings("deprecation")
	public static String addJs(String jsPath) {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            return "<script type=\"text/javascript\" src=\"" + request.getContextPath() + "/" + jsPath + "?v=" +  DateTimeUtils.getCurrentYearLast().getYear() + "\"></script>";
        } catch (Exception e) {
            logger.error("addJs Error:", e);
        }
        return "";
    }
}