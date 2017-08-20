package com.education.common.utils;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
/**
* A manager for the CSRF token for a given session. The {@link #getTokenForSession(HttpSession)} should used to
* obtain the token value for the current session (and this should be the only way to obtain the token value).
* ***/
 
public final class CSRFTokenUtils {
	/**
	 * 日志信息
	 */
	private static Logger logger = LoggerFactory.getLogger(CSRFTokenUtils.class);
    /**
     * The token parameter name
     */
    static final String CSRF_PARAM_NAME = "CSRFToken";
 
    /**
     * The location on the session which stores the token
     */
    public static final  String CSRF_TOKEN_FOR_SESSION_ATTR_NAME = CSRFTokenUtils.class
            .getName() + ".tokenval";
    
    /**
     * Create Token from Request
     * @param request
     * @return
     */
    public static String getTokenForRequest(HttpServletRequest request){
    	HttpSession session = request.getSession();
    	return getTokenForSession(session);
    }
    
    /**
     * Create Token from Session
     * @param session
     * @return
     */
    public static String getTokenForSession(HttpSession session) {
        String token = null;
        
        // I cannot allow more than one token on a session - in the case of two
        // requests trying to
        // init the token concurrently
        synchronized (session) {
            token = (String) session
                    .getAttribute(CSRF_TOKEN_FOR_SESSION_ATTR_NAME);
            if (null == token) {
                token = UUID.randomUUID().toString();
                session.setAttribute(CSRF_TOKEN_FOR_SESSION_ATTR_NAME, token);
            }
        }
        return token;
    }
 
    /**
     * Extracts the token value from the session
     * 
     * @param request
     * @return
     */
    public static String getTokenFromRequest(HttpServletRequest request) {
        return request.getParameter(CSRF_PARAM_NAME);
    }
    
    /**
     * Check Crsf Token Validate
     * @param request
     * @return
     */
    public static boolean checkCrsfTokenValidate(HttpServletRequest request){
    	//CRSF编码
	    String _csrf = request.getParameter("csrf");
		//设置会话信息
		HttpSession session = request.getSession();
		String token = getTokenForSession(session);
		//防站点攻击
		if(_csrf == null || token == null || !_csrf.equals(token)){
	        if(logger.isDebugEnabled()){
	        	logger.debug("CSRF attack detected. URL:"+request.getRequestURI());
	        }
	        return false;
		} 
		return true;
    }
    
    private CSRFTokenUtils() {
    }
 
}