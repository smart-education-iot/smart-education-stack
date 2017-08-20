package com.education.common.config.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 错误页面定义
 * @author jamesli
 * @version 1.0
 * @date 2016-05-20
 */
@Controller
public class ErrorsController implements ErrorController {
	/**
	 * ERROR PATH
	 */
	private static final String ERROR_PATH = "/error";
	
	/**
	 * 错误处理
	 * @param response
	 * @return
	 */
	@RequestMapping(value=ERROR_PATH)
    public String handleError(HttpServletResponse response,
    		HttpServletRequest request,
    		ModelMap model){
		int code = response.getStatus();
		String errorPage = "error";
		switch(code){
			case 403:
				//forbiden
				errorPage = "error/403";
				break;
			case 404:
				//page not found
				errorPage = "error/404";
				break;
			case 500:
				//server error
				errorPage = "error/500";
				break;
			default:
				errorPage = "error/error";
		}
		model.put("page", "error");
		model.put("base", request.getContextPath());
		return errorPage;
    }
	
	/**
	 * 错误路径
	 */
	@Override
	public String getErrorPath() {
	  return ERROR_PATH;
	}

}