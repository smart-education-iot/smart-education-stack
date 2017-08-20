package com.education.common.config.base;

import java.beans.PropertyEditorSupport;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.util.HtmlUtils;
import org.springframework.web.util.JavaScriptUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.education.common.config.base.vo.Pager;
import com.education.common.config.base.vo.ResultMap;
import com.education.common.config.base.vo.ResultMap.ResultCode;
import com.education.common.utils.CSRFTokenUtils;
import com.education.common.utils.EducationConstant;
import com.github.pagehelper.Page;

/**
 * 控制器基类
 * @author lijinfeng
 * @date 2016-4-20
 * @version 1.0
 */
public abstract class BaseController {
	
	/**
	 * 日志信息
	 */
	public Logger logger = LoggerFactory.getLogger(getClass());
	
    /**
     * 注解消息资源
     */
    @Autowired
    public MessageSource messageSource;
    
    /**
     * 注入HttpServletRequest
     */
    @Autowired
    private HttpServletRequest request;
    
	/**
	 * 读取消息
	 * @param code
	 * @return
	 */
	public String getMessage(String code){
		Locale locale = LocaleContextHolder.getLocale();
		return messageSource.getMessage(code, null, locale);
	}
	
	/**
	 * 返回数据视图
	 * @param viewPath
	 * @param model
	 * @return
	 */
	public String getView(String viewPath,ModelMap model){
		//兼容老系统
		model.put("root", request.getContextPath());
		model.put("base", request.getContextPath());
		String token = CSRFTokenUtils.getTokenForRequest(request);
		model.put("csrf", token);
		return viewPath;
	}
	
	/**
	 * 跨站点攻击检查
	 * @return
	 */
	public void checkCrsfToken(HttpServletRequest request,
			HttpServletResponse response){
		//防站点攻击
		boolean flag = CSRFTokenUtils.checkCrsfTokenValidate(request);
		if(!flag){
			try {
				response.sendError(403);
				//request.getRequestDispatcher("/error").forward(request, response);
			} catch (IOException e) {
				//e.printStackTrace();
				logger.error(e.getMessage());
			}
		}
	}
		
	/**
	 * 渲染JSON
	 * @param object
	 * @param flag
	 * 		1. flag : true  正常输出->object为回传的数据对象或集合
	 *      2. flag : false 异常输出->object为回传的异常信息
	 * @return
	 * 		state : 0/1
	 *      data  : d...a...t...a
	 *      msg	  : failure/success
	 *      code  : 300(failure)/200(success)
	 */
	public String renderJson(Object object,boolean flag){
		return renderJson(object,0,flag);
	}
	/**
	 * 渲染JSON
	 * @param object
	 * @param count
	 * @param flag
	 * 		1. flag : true  正常输出->object为回传的数据对象或集合
	 *      2. flag : false 异常输出->object为回传的异常信息
	 * @return
	 * 		state : 0/1
	 *      data  : d...a...t...a
	 *      msg	  : failure/success
	 *      code  : 300(failure)/200(success)
	 */
	public String renderJson(Object object,long count,boolean flag){
		JSONObject json = new JSONObject();
		if(flag){
			//1.返回成功结果状态
			json.put(ResultMap.SUCCESS, 1);
			//2.返回数组或集合总数
			if(null != object){
				if(object instanceof List){
					json.put("total", ((List<?>)object).size());
					json.put("count", count);
				}
			}
			//3.设置回传数据信息
			json.put(ResultMap.DATA, object);
			//4.设置回传操作代码
			json.put(ResultMap.CODE, ResultCode.SUCCESS.getCode());
			//5.设置通用成功操作提示信息
			json.put(ResultMap.MSG, this.getMessage("app.success"));
		}else{
			//1.返回失败结果状态
			json.put(ResultMap.SUCCESS, 0);
			//2.返回数据异常信息
			json.put(ResultMap.DATA, object);
			//3.设置回传操作代码
			json.put(ResultMap.CODE, ResultCode.FAILURE.getCode());
			//4.发挥通用失败操作提示信息
			json.put(ResultMap.MSG, this.getMessage("app.failure"));
		}
	    return json.toJSONString();
	}
	
	/**
	 * 带对象成功返回
	 * @param object
	 * @return
	 */
	protected ResultMap success(Object object) {
		ResultMap resultMap = new ResultMap();
		resultMap.success(object);
		return resultMap;
	}
	
	/**
	 * 不带对象成功返回
	 * @return
	 */
	protected ResultMap success(){
		ResultMap resultMap = new ResultMap();
		resultMap.success();
		return resultMap;
	}

	/**
	 * 失败提示
	 * @param msg
	 * @return
	 */
	protected ResultMap failure(String msg) {
		ResultMap resultMap = new ResultMap();
		resultMap.failure(msg);
		return resultMap;
	}

	/**
	 * 成功提示
	 * @return
	 */
	protected ResultMap failure(){
		ResultMap resultMap = new ResultMap();
		resultMap.failure();
		return resultMap;
	}
	
	/**
	 * 数据分页不带其他返回结果
	 * @param page
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	protected ResultMap sucess(Page page) {
		Map<String, Object> data = new HashMap<String, Object>();
//		data.put("pageInfo", page.pageInfo);
//		data.put("list", page.list);
		return success(data);
	}
	
	/**
	 * 数据分页带其他返回结果
	 * @param page
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	protected ResultMap sucess(Page page,Map<String,Object> map) {
		Map<String, Object> data = new HashMap<String, Object>();
//		data.put("pageInfo", page.pageInfo);
//		data.put("list", page.list);
		data.putAll(map);
		return success(data);
	}
	
	/**	
	 * 读取当前会话帐号信息
	 * @return
	 */
	public JSONObject getCurrentUser(){
		HttpSession session = request.getSession();
		JSONObject jsonUser = (JSONObject)session.getAttribute(EducationConstant.USER_TOCKEN);
		if(jsonUser==null){
//			try {
//				response.sendError(403, this.getMessage("webfinal.action.session"));
//			} catch (IOException e) {
//				//e.printStackTrace();
//				logger.error("Session Timeout:"+e.getMessage());
//			}
			//
			throw new BaseException(300,this.getMessage("webfinal.action.session"));
		}
		return jsonUser;
	}
	
	/**
	 * 读取参数
	 * @param request
	 * @return
	 */
	public Map<String,Object> getParameter(HttpServletRequest req){
		Map<String,Object> map = new HashMap<String,Object>();
		Enumeration<String> pNames = req.getParameterNames();
		while(pNames.hasMoreElements()){
		    String key= pNames.nextElement();
		    String value = this.getPara(key);
		    //String value=request.getParameter(name);
		    map.put(key, value);
		}
		return map;
	}
	
	/**
	 * 防止SQL注入
	 * @param name
	 * @return
	 */
	public String getPara(String name){
		String s1 = request.getParameter(name);
		s1 = StringEscapeUtils.escapeSql(s1);
	    s1 = HtmlUtils.htmlEscape(s1);
	    s1 = JavaScriptUtils.javaScriptEscape(s1);
		return s1;
	}
	/**
	 * 读取参数
	 * @param name
	 * @return
	 */
	public Integer getParaInt(String name){
		return toInt(getPara(name), 0);
	}
	/**
	 * 转化Int类型
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public Integer getParaToInt(String name, Integer defaultValue) {
		return toInt(getPara(name), defaultValue);
	}
	/**
	 * 转化Long类型
	 * @param name
	 * @return
	 */
	public Long getParaToLong(String name) {
		return toLong(getPara(name), null);
	}
	/**
	 * 转化Long类型
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public Long getParaToLong(String name, Long defaultValue) {
		return toLong(getPara(name), defaultValue);
	}
	/**
	 * 转化数字
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	private Integer toInt(String value, Integer defaultValue) {
		try {
			if (value == null || "".equals(value.trim()))
				return defaultValue;
			value = value.trim();
			if (value.startsWith("N") || value.startsWith("n"))
				return -Integer.parseInt(value.substring(1));
			return Integer.parseInt(value);
		}
		catch (Exception e) {
			throw new BaseException(404, "Can not parse the parameter \"" + value + "\" to Integer value.");
		}
	}

	private Long toLong(String value, Long defaultValue) {
		try {
			if (value == null || "".equals(value.trim()))
				return defaultValue;
			value = value.trim();
			if (value.startsWith("N") || value.startsWith("n"))
				return -Long.parseLong(value.substring(1));
			return Long.parseLong(value);
		}catch (Exception e) {
			throw new BaseException(404, "Can not parse the parameter \"" + value + "\" to Long value.");
		}
	}
	
	/**
	 * 读取参数(默认值)
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public String getPara(String name, String defaultValue) {
		String result = this.getPara(name);
		return result != null && !"".equals(result) ? result : defaultValue;
	}
	
	/**
	 * 分页规范化
	 * @param page
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public com.alibaba.fastjson.JSONObject toBootstrapTable(Pager page){
		com.alibaba.fastjson.JSONObject jsonObject = new com.alibaba.fastjson.JSONObject();
		jsonObject.put("page", Integer.valueOf(page.getPageNum()));
		jsonObject.put("total", Long.valueOf(page.getTotal()));
        List<?> list = page.getList();
        com.alibaba.fastjson.JSONArray jsonarray = (JSONArray)JSONArray.toJSON(list);
        jsonObject.put("rows", jsonarray);
        return jsonObject;
    }
	
	/**
	 * 文件
	 * @param file
	 */
	public void renderFile(HttpServletResponse response,File file,String fileName) {
		if (file == null || !file.isFile() || file.length() > Integer.MAX_VALUE) {
			return ;
        }
		try {
			response.addHeader("Content-disposition", "attachment; filename=" + new String(file.getName().getBytes("GBK"), "ISO8859-1"));
		} catch (UnsupportedEncodingException e) {
			response.addHeader("Content-disposition", "attachment; filename=" + file.getName());
		}
        String contentType = request.getServletContext().getMimeType(file.getName());
        if (contentType == null) {
        	contentType =  "application/octet-stream";
        }
        response.setContentType(contentType);
        response.setContentLength((int)file.length());
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(file));
            outputStream = response.getOutputStream();
            byte[] buffer = new byte[1024];
            for (int n = -1; (n = inputStream.read(buffer)) != -1;) {
                outputStream.write(buffer, 0, n);
            }
            outputStream.flush();
        }catch (Exception e) {
        	response.setStatus(HttpServletResponse.SC_BAD_REQUEST);	// HttpServletResponse.SC_XXX_XXX
        }finally {
            if (inputStream != null) {
                try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
            if (outputStream != null) {
            	try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
        }
	}
	/**
	 * 发送文本。使用UTF-8编码。
	 * 
	 * @param response
	 *            HttpServletResponse
	 * @param text
	 *            发送的字符串
	 */
	public void renderText(HttpServletResponse response, String text) {
		render(response, "text/plain;charset=UTF-8", text);
	}
	/**
	 * 发送json。使用UTF-8编码。
	 * @param response
	 *            HttpServletResponse
	 * @param text
	 *            发送的字符串
	 * @param csrf
	 * 			  隐形检查跨站点攻击防御
	 */
	public void renderJson(HttpServletRequest request,HttpServletResponse response, String text) {
		//1.跨站点攻击防御
		this.checkCrsfToken(request, response);
		//2.处理返回JSON结果
		render(response, "application/json;charset=UTF-8", text);
	}
	/**
	 * 发送json。使用UTF-8编码。
	 * @param response
	 *            HttpServletResponse
	 * @param text
	 *            发送的字符串
	 */
	public void renderJson(HttpServletResponse response, String text) {
		
		render(response, "application/json;charset=UTF-8", text);
	}
	/**
	 * 发送xml。使用UTF-8编码。
	 * 
	 * @param response
	 *            HttpServletResponse
	 * @param text
	 *            发送的字符串
	 */
	public void renderXml(HttpServletResponse response, String text) {
		render(response, "text/xml;charset=UTF-8", text);
	}
	
	/**
	 * 发送Html。使用UTF-8编码。
	 * @param response
	 *            HttpServletResponse
	 * @param text
	 *            发送的字符串
	 */
	public void renderHtml(HttpServletResponse response, String text) {
		render(response, "text/html;charset=UTF-8", text);
	}

	/**
	 * 发送内容。使用UTF-8编码。
	 * @param response
	 * @param contentType
	 * @param text
	 */
	private void render(HttpServletResponse response, String contentType,
			String text) {
		response.setContentType(contentType);
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		try {
			response.getWriter().write(text);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
	/**
	 * 对象转换为MAP
	 * @param o
	 * @return
	 */
	protected Map<String,Object> object2map(Object o) {
        if (o == null) {
            return new HashMap<String, Object>();
        }
        Field[] fileds = o.getClass().getDeclaredFields();
        Map<String, Object> map = new HashMap<String, Object>();
        for (Field field : fileds) {
            field.setAccessible(true);
            try {
                map.put(field.getName(), field.get(o));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }
	
	/**
	 * 数据绑定格式转换
	 * @param request
	 * @param binder
	 */
    @InitBinder
    protected void init(HttpServletRequest request, ServletRequestDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                dateFormat, false));
        binder.registerCustomEditor(String.class, new StringEscapeEditor(false,false,false));

        //binder.registerCustomEditor(Double.class,);
        //initDataBinder(request, binder);
    }
	/**
	 * 定义内部类
	 * @author Jamesli
	 * @version 1.0
	 * @date 2016-06-05
	 */
	class StringEscapeEditor extends PropertyEditorSupport{
		private boolean escapeHTML;
		private boolean escapeJavaScript;
		private boolean escapeSQL;
		/**
		 * 构造方法
		 */
		public StringEscapeEditor() { 
			super(); 
		}
		/**
		 * 构造方法
		 * @param escapeHTML
		 * @param escapeJavaScript
		 * @param escapeSQL
		 */
		public StringEscapeEditor(boolean escapeHTML, boolean escapeJavaScript, boolean escapeSQL) {
			super();
			this.escapeHTML = escapeHTML;
			this.escapeJavaScript = escapeJavaScript;
			this.escapeSQL = escapeSQL;
		}
		/**
		 * 处理Value值
		 */
    	@Override
    	public void setAsText(String text) {
    		if (text == null) {
    			setValue(null);
    		} else {
    			String value = text;
    			if (escapeHTML) { 
    				value = StringEscapeUtils.escapeHtml(value); 
    			}
    			if (escapeJavaScript) { 
    				value = StringEscapeUtils.escapeJavaScript(value); 
    			}
    			if (escapeSQL) { 
    				value = StringEscapeUtils.escapeSql(value);
    			} 
    			setValue(value);
    		}

    	}

    	@Override
    	public String getAsText() { 
    		Object value = getValue(); 
    		return value != null ? value.toString() : ""; 
    	}
	}

	/**
	 * 执行成功操作
	 * @param response
	 * @param message
	 * @param callback
	 */
	public void ajaxDoneSuccess(HttpServletResponse response,String message) {
		com.alibaba.fastjson.JSONObject result = new com.alibaba.fastjson.JSONObject();
		result.put("state", 1);
		result.put("statusCode", 200);
		if(StringUtils.isEmpty(message)){
			result.put("msg",this.getMessage("webfinal.action.success"));
		}else{
			result.put("msg",message);
		}
		this.renderJson(response, result.toString());
	}
	/**
	 * request设置参数
	 * @param key
	 * @param value
	 */
	public void setAttr(String key,Object value){
		request.setAttribute(key, value);
	}
	/**
	 * 执行失败操作
	 * @param response
	 * @param callback
	 * @param message
	 */
	public void ajaxDoneError(HttpServletResponse response,String message) {
		com.alibaba.fastjson.JSONObject result = new com.alibaba.fastjson.JSONObject();
		result.put("state", 0);
		result.put("statusCode", 300);
		if(StringUtils.isEmpty(message)){
			result.put("msg",this.getMessage("webfinal.action.failure"));
		}else{
			result.put("msg",message);
		}
		this.renderJson(response, result.toString());
	}
    /**
	 * 获取主键
	 * @return
	 */
	public static String getUUID(){ 
	   return UUID.randomUUID().toString();
	}
    
}
