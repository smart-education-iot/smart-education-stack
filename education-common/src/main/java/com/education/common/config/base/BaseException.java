package com.education.common.config.base;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 基类异常
 * @author jamesli
 * @version 1.0
 * @date 2016-05-23 09:32
 */
public class BaseException extends RuntimeException {
	
	private static final long serialVersionUID = 1998063243843477017L;
	/**
	 * 日志服务
	 */
	private static final Logger logger = LoggerFactory.getLogger(BaseException.class);
	/**
	 * 错误编码
	 */
	private int errorCode;
	/**
	 * 错误试图
	 */
	private String errorView;
	/**
	 * 错误消息
	 */
	private String errorMessage;

	/**
	 * 异常处理
	 * @param errorCode
	 * @param errorView
	 * @param request
	 */
	public BaseException(int errorCode, String errorMessage) {
		this.errorCode = errorCode;
		//this.errorRender = RenderFactory.me().getErrorRender(errorCode, errorView);
		this.errorMessage = errorMessage;
		if(logger.isDebugEnabled()){
			logger.debug("{code:"+errorCode+",msg:"+errorMessage+"}");
		}
	}
	/**
	 * 异常处理
	 * @param errorCode
	 * @param errorView
	 * @param message
	 * @param request
	 */
	public BaseException(int errorCode, String errorView,String message) {
		if (StringUtils.isNotEmpty(errorView))
			throw new IllegalArgumentException("The parameter errorView can not be blank.");
		this.errorCode = errorCode;
		this.errorView = errorView;
		this.errorMessage = message;
		//this.errorRender = RenderFactory.me().getErrorRender(errorCode, errorView);
		if(logger.isDebugEnabled()){
			logger.debug("{code:"+errorCode+",view:"+errorView+",msg:"+message+"}");
		}
	}
	/**
	 * 错误编码
	 * @return
	 */
	public int getErrorCode() {
		return errorCode;
	}
	/**
	 * 错误视图
	 * @return
	 */
	public String getErrorView() {
		return errorView;
	}
	/**
	 * 错误消息
	 * @return
	 */
	public String getErrorMessage() {
		return errorMessage;
	}
	
}