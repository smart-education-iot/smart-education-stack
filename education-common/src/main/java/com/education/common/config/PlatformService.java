package com.education.common.config;

import org.springframework.stereotype.Service;

/**
 * 平台接口服务集合
 * @author jamesli
 * @version 1.0
 */
@Service("platformService")
public class PlatformService {
//	/**
//	 * 引入用户RPC服务接口
//	 */
//	@Reference(group="bsp",check=false)
//	private UserAuthorityService userAuthorityService;
//	/**
//	 * 引入机构RPC服务接口
//	 */
//	@Reference(group="bsp",check=false)
//	private OrganizationService organizationService;
//
//	/**
//	 * 引用应用配置常量
//	 */
//	@Value("${spring.profiles.active}")
//	private String active;
//	@Value("${spring.application.id}")
//	private String appCode;
//	@Value("${spring.application.region}")
//	private String regionCode;
//
//	
//	public String getAppCode() {
//		return appCode;
//	}
//	public void setAppCode(String appCode) {
//		this.appCode = appCode;
//	}
//	public String getProfile(){
//		return active;
//	}
//	public String getRegionCode(){
//		return regionCode;
//	}
//	/**
//	 * 用户登录
//	 * @param account
//	 * @param password
//	 * @return
//	 */
//	public JSONObject userLogin(String account,String password){
//		return this.userAuthorityService.userLogin(account, password, this.getAppCode(), false);
//	}
//	/**
//	 * 读取用户基本信息
//	 * @param userId
//	 * @return
//	 */
//	public JSONObject getUserBasicInfo(String userId){
//		return this.userAuthorityService.getUserBasicInfo(userId);
//	}
//	/**
//	 * 更新用户登录密码
//	 * @param userId
//	 * @param userPassword
//	 * @return
//	 */
//	public JSONObject updateUserPassword(String userId,String userPassword){
//		return this.userAuthorityService.updateUserPassword(userId, userPassword);
//	}
//	/**
//	 * 读取用户应用信息
//	 * @param userId
//	 * @param type
//	 * @return
//	 */
//	public JSONObject getUserApps(String userId, String type){
//		return this.userAuthorityService.getUserApps(userId, type);
//	}
//	/**
//	 * 读取用户意见信息
//	 * @param userId
//	 * @return
//	 */
//	public JSONObject userOpinionShowById(String userId){
//		return this.userAuthorityService.userOpinionShowById(userId);
//	}
//	/**
//	 * 新增用户意见
//	 * @param json
//	 * @return
//	 */
//	public JSONObject userOpinionAdd(JSONObject json){
//		return this.userAuthorityService.userOpinionAdd(json);
//	}
//	/**
//	 * 更新用户意见
//	 * @param json
//	 * @return
//	 */
//	public JSONObject userOpinionUpdate(JSONObject json){
//		return this.userAuthorityService.userOpinionUpdate(json);
//	}
//	/**
//	 * 删除用户意见
//	 * @param userId
//	 * @return
//	 */
//	public JSONObject userOpinionDelById(String opinionId){
//		return this.userAuthorityService.userOpinionDelById(opinionId);
//	}
//	/**
//	 * 删除用户反馈意见
//	 * @param id
//	 * @return
//	 */
//	public JSONObject deleteUserFeedback(String id){
//		return this.userAuthorityService.deleteUserFeedback(id);
//	}
//	/**
//	 * 查询反馈意见信息
//	 * @param id
//	 * @return
//	 */
//	public JSONObject findUserFeedbackInfo(String id){
//		return this.userAuthorityService.findUserFeedbackInfo(id);
//	}
//	/**
//	 * 返回用户反馈信息
//	 * @param userId
//	 * @param type
//	 * @param page
//	 * @param size
//	 * @return
//	 */
//	public JSONObject getUserFeedback(String userId,String type,int page,int size){
//		return this.userAuthorityService.getUserFeedback(userId, type, page, size);
//	}
//	/**
//	 * 读取用户意见列表信息
//	 * @param userId
//	 * @param page
//	 * @param size
//	 * @return
//	 */
//	public JSONObject getUserOpinion(String userId,int page,int size){
//		return this.userAuthorityService.getUserOpinion(userId, page, size);
//	}
//	/**
//	 * 更新用户信息
//	 * @param user
//	 * @return
//	 */
//	public JSONObject updateUserBasicInfo(JSONObject userInfo){
//		return this.userAuthorityService.updateUserBasicInfo(userInfo);
//	}
//	/**
//	 * 更新用户手机号码
//	 * @param userId
//	 * @param mobile
//	 * @return
//	 */
//	public JSONObject updateUserMobile(String userId,String mobile){
//		return this.userAuthorityService.updateUserMobile(userId, mobile);
//	}
//	/**
//	 * 更新用户电子邮箱
//	 * @param userId
//	 * @param email
//	 * @return
//	 */
//	public JSONObject updateUserEmail(String userId,String email){
//		return this.userAuthorityService.updateUserEmail(userId, email);
//	}
//	/**
//	 * 用户登录
//	 * @param account
//	 * @param password
//	 * @return
//	 */
//	public JSONObject userLogin(String account,String password,boolean flag){
//		return this.userAuthorityService.userLogin(account, password, this.getAppCode(), flag);
//	}
//	
//	/**
//	 * 根据行政区划编码获取行政区划信息
//	 * @param regionCode
//	 * @return
//	 */
//	public JSONObject getRegionInfoByRegionCode(String regionCode){
//		return this.organizationService.getRegionInfoByRegionCode(regionCode);
//	}
//	/**
//	 * 根据区划编码读取下级区划列表
//	 * @param regionCode 父区划编码
//	 * @param defaultTree 是否默认树（默认树显示市直、省直机关节点）
//	 * @return [{CODE:"420000",PARENT_CODE:"#",NAME:"湖北省",PARENT_NAME:"",TYPE:"0|1",GARDE:"1|2|3|4|5|6",CHILDS:0}]
//	 */
//	public JSONObject getRegionByParentCode(String regionCode,boolean defaultTree){
//		return this.organizationService.getRegionByParentCode(regionCode, defaultTree);
//	};
//	/**
//	 * 根据部门编码获取部门信息
//	 * @param organCode
//	 * @return
//	 */
//	public JSONObject getOrganInfoById(String organCode){
//		return this.organizationService.getOrganInfoById(organCode);
//	}
//
//	/**
//	 * 
//	 * @param regionCode
//	 * @param flag
//	 * @return
//	 */
//	public List<Map<String, Object>> findOrganRegionTree(String regionCode,boolean flag) {
//		return this.organizationService.findOrganRegionTree(regionCode, flag);
//	}
//
//	/**
//	 * 读取用户登录日志信息
//	 * @param userId
//	 * @param page
//	 * @param rows
//	 * @return
//	 */
//	public JSONObject getUserHistory(String userId,int page,int rows){
//		return this.userAuthorityService.getUserHistory(userId, page, rows);
//	}
//	/**
//	 * 删除用户登录日志信息
//	 * @param userId
//	 * @return
//	 */
//	public JSONObject deleteUserHistory(String userId){
//		return this.userAuthorityService.deleteUserHistory(userId);
//	}
//	
//	/**
//	 * 依据应用类型获取字典项
//	 * @param kind
//	 * @param page
//	 * @param size
//	 * @param appCode
//	 * @return
//	 */
//	public JSONObject getDictByKindAppCode(String kind,int page, int size,String appCode){
//		return this.organizationService.getDictByKindAppCode(kind, page, size, appCode);
//	}
//	
//	/**
//	 * 依据应用编码获取字典配置
//	 * @param appCode
//	 * @return
//	 */
//	public JSONObject getDictConfigKindByAppCode(String appCode){
//		return this.organizationService.getDictConfigKindByAppCode(appCode);
//	}
//	
//	
//	/**
//	 * 我的成长统计
//	 * @param time
//	 * @param userId
//	 * @return
//	 */
//	public JSONObject getUserHistoryCount(String time,String userId){
//		return userAuthorityService.getUserHistoryCount(time,userId);
//	}
//	/**
//	 * 读取行政区划信息
//	 * @param regionCode
//	 * @param appCode
//	 * @return
//	 */
//	public JSONObject getRegionOrgenByRegionCode(String regionCode,String appCode){
//		return organizationService.getRegionOrgenByRegionCode(regionCode, appCode);
//	}
//	/**
//	 * 
//	 * @param json
//	 */
//	public void saveLogin(JSONObject json){
//		this.userAuthorityService.saveLogin(json);
//	}
//	/**
//	 * 获取业务类型信息(山东项目)
//	 * @param appCode
//	 * @return
//	 */
//	public JSONObject getDictBasicInfo(String appCode,String type){
//		return this.organizationService.getDictBasicInfo(type, appCode);
//	}
}