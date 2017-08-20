package com.education.common.config.base.vo;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
/**
 * 结果MAP对象
 * @author lijinfeng
 * @date 2016-4-20
 */
public class ResultMap extends LinkedHashMap<String, Object> {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 结果状态
	 */
	public static final String SUCCESS = "state";
	/**
	 * 结果数据
	 */
	public static final String DATA = "data";
    /**
     * 结果提示
     */
	public static final String MSG = "msg";
	/**
	 * 结果状态码
	 */
	public static final String CODE = "code";
	
	/**
	 * 结果状态码
	 * @author Jamesli
	 * @version 1.0
	 * @date 2016-05-16
	 */
	public static enum ResultCode{
		SUCCESS(200),FAILURE(300);
		private int code;
		ResultCode(int code){
			this.code = code;
		}
		public int getCode(){
			return this.code;
		}
	}

	/**
	 * 获取是否成功
	 * 
	 * @return
	 */
	public Boolean getSuccess() {
		return (Boolean) this.get(SUCCESS);
	}
	/**
	 * 设置成功状态
	 * @param success
	 */
	private void setSuccess(Boolean success) {
		this.put(SUCCESS, success);
		this.put(CODE, 1);
	}

	/**
	 * 获取返回数据
	 * 
	 * @return
	 */
	public Object getData() {
		return this.get(DATA);
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getDataMap() {
		return (Map<String, Object>) this.get(DATA);
	}

	private void setData(Object data) {
		this.put(DATA, data);
	}

	/**
	 * 获取返回消息
	 * 
	 * @return
	 */
	public Object getMsg() {
		return this.get(MSG);
	}

	private void setMsg(Object msg) {
		this.put(MSG, msg);
	}

	/**
	 * 返回结果成功，返回数据为null
	 */
	public void success() {
		this.clear();
		this.setSuccess(true);
		this.setData(new HashMap<String, Object>());
	}

	/**
	 * 返回结果成功，返回数据为data
	 * 
	 * @param data
	 */
	public void success(Object data) {
		this.clear();
		this.setSuccess(true);
		this.setData(data);
	}

	/**
	 * 返回结果失败，返回消息为null
	 */
	public void failure() {
		this.clear();
		this.setSuccess(false);
		this.setMsg(null);
		this.put(CODE, 0);
	}

	/**
	 * 返回结果失败，返回消息为msg
	 * 
	 * @param msg
	 */
	public void failure(Object msg) {
		this.clear();
		this.setSuccess(false);
		this.setMsg(msg);
	}

}