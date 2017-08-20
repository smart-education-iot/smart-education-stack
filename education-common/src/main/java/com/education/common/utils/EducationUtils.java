package com.education.common.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 系统工具类
 * @author lijinfeng
 * @date 2014-07-31
 * @version 2.0
 * @description 用于常用系统工具定义
 */
public final class EducationUtils {
	/**
	 * 时间格式化
	 */
	private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * 测试
	 * @param args
	 */
	public static void main(String[] args){
		//System.out.println(hangeToBig(35.20d));
		System.out.println(getTimeStamp());
	}
	/**
	 * 获取当前年份
	 * @return
	 */
	public static int getCurrentYear(){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		return calendar.get(Calendar.YEAR);
	}
	/**
	 * 获取当前月份
	 * @return
	 */
	public static int getCurrentMonth(){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		return calendar.get(Calendar.MONTH)+1;
	}
	/**
	 * 获取当前日期
	 * @return
	 */
	public static int getCurrentDay(){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		return calendar.get(Calendar.DAY_OF_MONTH);
	}
	/**
	 * 时间格式化
	 * @param date
	 * @return
	 */
	public static String formateDate(Date date){
		if(date == null){
			return "";
		}
		return df.format(date);
	}
	/**
	 * 获取当前年月
	 * @return
	 */
	public static String getTimeStamp(){
		return new SimpleDateFormat("yyMMdd").format(new Date());
	}
	/**
	 * 获取当前年月
	 * @return
	 */
	public static String getCurrentTime(){
		return new SimpleDateFormat("yyyy年MM月dd日").format(new Date());
	}
	/**
	 * MD5
	 * @param source
	 * @return
	 */
	public static String getMD5(byte[] source) {
	    String s = null;
	    char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
	      'a', 'b', 'c', 'd', 'e', 'f' };
	    try {
	      MessageDigest md = MessageDigest.getInstance("MD5");
	      md.update(source);
	      byte[] tmp = md.digest();

	      char[] str = new char[32];

	      int k = 0;
	      for (int i = 0; i < 16; ++i) {
	        byte byte0 = tmp[i];
	        str[(k++)] = hexDigits[(byte0 >>> 4 & 0xF)];

	        str[(k++)] = hexDigits[(byte0 & 0xF)];
	      }
	      s = new String(str);
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	    return s;
	}
	/**
     * 人民币转成大写
     * @param value
     * @return String
     */
    public static String hangeToBig(double value){
        char[] hunit = { '拾', '佰', '仟' }; // 段内位置表示
        char[] vunit = { '万', '亿' }; // 段名表示
        char[] digit = { '零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖' }; // 数字表示
        long midVal = (long) (value * 100); // 转化成整形
        String valStr = String.valueOf(midVal); // 转化成字符串
        String head = valStr.substring(0, valStr.length() - 2); // 取整数部分
        String rail = valStr.substring(valStr.length() - 2); // 取小数部分
        String prefix = ""; // 整数部分转化的结果
        String suffix = ""; // 小数部分转化的结果
        // 处理小数点后面的数
        if (rail.equals("00"))
        { // 如果小数部分为0
            suffix = "整";
        }
        else
        {
            suffix = digit[rail.charAt(0) - '0'] + "角" + digit[rail.charAt(1) - '0'] + "分"; // 否则把角分转化出来
        }
        // 处理小数点前面的数
        char[] chDig = head.toCharArray(); // 把整数部分转化成字符数组
        char zero = '0'; // 标志'0'表示出现过0
        byte zeroSerNum = 0; // 连续出现0的次数
        for (int i = 0; i < chDig.length; i++)
        { // 循环处理每个数字
            int idx = (chDig.length - i - 1) % 4; // 取段内位置
            int vidx = (chDig.length - i - 1) / 4; // 取段位置
            if (chDig[i] == '0')
            { // 如果当前字符是0
                zeroSerNum++; // 连续0次数递增
                if (zero == '0')
                { // 标志
                    zero = digit[0];
                }
                else if (idx == 0 && vidx > 0 && zeroSerNum < 4)
                {
                    prefix += vunit[vidx - 1];
                    zero = '0';
                }
                continue;
            }
            zeroSerNum = 0; // 连续0次数清零
            if (zero != '0')
            { // 如果标志不为0,则加上,例如万,亿什么的
                prefix += zero;
                zero = '0';
            }
            prefix += digit[chDig[i] - '0']; // 转化该数字表示
            if (idx > 0)
                prefix += hunit[idx - 1];
            if (idx == 0 && vidx > 0)
            {
                prefix += vunit[vidx - 1]; // 段结束位置应该加上段名如万,亿
            }
        }
        if (prefix.length() > 0)
            prefix += '圆'; // 如果整数部分存在,则有圆的字样
        return prefix + suffix; // 返回正确表示
    }
    /**
     * 分析条件
     * @param condition
     * @return
     * @throws Exception 
     */
    public static Map<String,Object> parseCondition(String condition) throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		StringBuffer conditionStr = new StringBuffer(condition.substring(2, condition.length()-1));
		//定义正则表达式，解析运算符，以及运算符两侧
		String regex = "[!<>=]=*";
		//目前只识别仅有一个运算符的情况
		String[] complex = conditionStr.toString().split(regex);
		//key
		String key = complex[0];
		//value
		String value = complex[1];
		//operator
		String operator = "";
		//遍历 寻找出表达式
		Pattern p = Pattern.compile(regex);
		//遍历器
		Matcher m = p.matcher(conditionStr); 
		if(m.find()){
			operator = m.group();
		}
		map.put(key,setValue(operator,value));
		//返回值
		return map;
	}
    /**
	 * @description 获取taskservice的complete方法中的map对应的value
	 * @param operator
	 * @param value
	 * @return
     * @throws Exception 
	 */
	public static Object setValue(String operator,Object value) throws Exception{
		Object returnValue = new Object();
		//如果是相等
		try {
			if("==".equals(operator) || "=".equals(operator)){
				returnValue = value;
			}else if(operator.contains(">") ){
					returnValue = Integer.parseInt(String.valueOf(value))+1;
			}else if(operator.contains("<")){
					returnValue = Integer.parseInt(String.valueOf(value))-1;
			}else if(operator.contains("!")){
					returnValue = String.valueOf(value)+"1";
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("转化错误:"+e.getMessage());
		}
		return returnValue;
	}
	
	/**
	 * Clob类型转String`
	 * @param baseValue
	 * @return
	 */
	public static String clob2string(Object baseValue) {
		String strBaseValue = null;
		if (baseValue instanceof Clob) {
			Clob c = (Clob) baseValue;
			if (c != null) {
				try {
					strBaseValue = c.getSubString(1, (int) c.length());
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return strBaseValue;
	}
	
	
	
	/**
	 * 拼装IN串
	 * 
	 * @param list
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String generateIns(List list) {
		String inPara = "";
		if (null!=list&&list.size() > 0) {
			StringBuffer strBufinPara = new StringBuffer();
			for (int i = 0; i < list.size(); i++) {
				strBufinPara.append("?,");
			}
			inPara = strBufinPara.substring(0, strBufinPara.length() - 1)
					.toString();
		}
		return inPara;
	}
	/**
	 * 解码特殊字符
	 * @param content
	 * @return
	 */
	public static String decodeSpecialCharsWhenLikeUseBackslash(String content){
		// 单引号是oracle字符串的边界,oralce中用2个单引号代表1个单引号
		String afterDecode = content.replaceAll("'", "''");
		// 由于使用了/作为ESCAPE的转义特殊字符,所以需要对该字符进行转义
		// 这里的作用是将"a/a"转成"a//a"
		afterDecode = afterDecode.replaceAll("/", "//");
		// 使用转义字符 /,对oracle特殊字符% 进行转义,只作为普通查询字符，不是模糊匹配
		afterDecode = afterDecode.replaceAll("%", "/%");
	    // 使用转义字符 /,对oracle特殊字符_ 进行转义,只作为普通查询字符，不是模糊匹配
	    afterDecode = afterDecode.replaceAll("_", "/_");
	    return afterDecode;
	}
	
	/**
	 *  Map --> Bean 1: 利用Introspector,PropertyDescriptor实现 Map --> Bean  
	 * @param map
	 * @param obj
	 */
    public static void transMap2Bean(Map<String, Object> map, Object obj) {  
  
        try {  
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());  
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
  
            for (PropertyDescriptor property : propertyDescriptors) {  
                String key = property.getName();  
  
                if (map.containsKey(key)) {  
                    Object value = map.get(key);  
                    // 得到property对应的setter方法  
                    Method setter = property.getWriteMethod();  
                    setter.invoke(obj, value);  
                }  
  
            }  
  
        } catch (Exception e) {  
            System.out.println("transMap2Bean Error " + e);  
        }  
  
        return;  
  
    }  

	
	/**
	 * Bean 转化为 MAP
	 * @param obj
	 * @return
	 */
	public static Map<String, Object> transBean2Map(Object obj) {  
		  
        if(obj == null){  
            return null;  
        }          
        Map<String, Object> map = new HashMap<String, Object>();  
        try {  
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());  
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
            for (PropertyDescriptor property : propertyDescriptors) {  
                String key = property.getName();  
  
                // 过滤class属性  
                if (!key.equals("class")) {  
                    // 得到property对应的getter方法  
                    Method getter = property.getReadMethod();  
                    Object value = getter.invoke(obj);  
  
                    map.put(key, value);  
                }  
  
            }  
        } catch (Exception e) {  
            System.out.println("transBean2Map Error " + e);  
        }  
  
        return map;  
  
    }  
	/**
	 * 依申请类受理环节文书模板[受理通知书，补交告知通知书，不予受理通知书]
	 * @param code
	 *  code = 1 受理
	 *  code = 2 补交
	 *  code = 3 不予受理
	 * @return
	 */
	public static String getAcceptPaper(String type,int code){
		if(code<10){
			return  type+"-00"+code;
		}else if(code>9&&code<100){
			return  type+"-0"+code;
		}else{
			return type+"-"+code;
		}
	}
	/**
	 * 目录编码规则定义【12位】
	 * [前端码7位:组织机构代码]/[后段码5位：业务类型码2位+3位顺序码]
	 * @param orgCode	7位组织机构代码
	 * @param orderCode	3位顺序码
	 * @param  type		2位业务类型码（默认：00，根据注册页面业务类型变化而变化）
	 * @return
	 */
	public static String getSDCatalogCode(String orgCode,String orderCode){
		StringBuffer code = new StringBuffer(100);
		//1.[前段码5位]constant.properties:app.catalog
		code.append(orgCode);
		//2.[前段/后段码分隔符]
		code.append("/");
		//3.[后段码]9位组织机构编码+3位顺序码
		int order = Integer.valueOf(orderCode);
		if(order<10){
			orderCode = "00"+order; 
		}else if(order<100){
			orderCode = "0"+order; 
		}
		code.append("00").append(orderCode);
		return code.toString();
	}
	/**
	 * 目录编码规则定义【18位】
	 * [前段码5位]/[后段码:9位组织机构代码(6位区划编码+3位部门编码)+3位顺序码]
	 * @param regionCode	5位前段编码
	 * @param orgCode		9位组织机构编码(6位区划编码+3位部门编码)
	 * @param orderCode		3位顺序码
	 * @return
	 */
	public static String getCatalogCode(String regionCode,String orgCode,String orderCode){
		StringBuffer code = new StringBuffer(100);
		//1.[前段码5位]constant.properties:app.catalog
		code.append(regionCode);
		//2.[前段/后段码分隔符]
		code.append("/");
		//3.[后段码]9位组织机构编码+3位顺序码
		int order = Integer.valueOf(orderCode);
		if(order<10){
			orderCode = "00"+order; 
		}else if(order<100){
			orderCode = "0"+order; 
		}
		code.append(orgCode).append(orderCode);
		/**
		if(StringUtils.isNotBlank(regionCode)){
			//1.[前段码前2位]行政区划编码首2位
			code.append(regionCode.substring(0,2));
			//2.[前段码第3位]0分配给省级部门，1-Z分配给地级市
			String cityCode = regionCode.substring(2,4);
			if(0==Integer.valueOf(cityCode)){
				//省级区划
				code.append("0");
			}else{
				//市级区划
				int three = Integer.valueOf(cityCode);
				if(three>=10){
					if(three<45){
						char e = (char)(55+three);
						code.append(e);
					}else if(three<55){
						char e = (char)(35+three);
						code.append(e);
					}else{
						char e = (char)(three);
						code.append(e);
					}
				}else{
					code.append(three);
				}
			}
			//3.[前段码第4位]0分配给地市级目录管理者，Q-Z分配给地级市部门,1-P分给区县
			String countyCode = regionCode.substring(4,6);
			if(Integer.valueOf(countyCode)>0){
				//区县级区划
				int four = Integer.valueOf(countyCode);
				if(four>=10){
					if(four<45){
						char e = (char)(55+four);
						code.append(e);
					}else if(four<55){
						char e = (char)(35+four);
						code.append(e);
					}else{
						char e = (char)(four);
						code.append(e);
					}
				}else{
					code.append(four);
				}
			}else{
				//省或地市级区划
				code.append("0");
			}
			//4.[前段码第5位]0分配给区县管理部门，1-Z保留
			if(Integer.valueOf(countyCode)>0){
				//行政区划4-6位不为0表示区县
				code.append("0");
			}else{
				code.append("X");
			}
			//5.[前段码/后段码]分隔符
			code.append("/");
			//6.[后段码]9位组织机构编码+3位顺序码
			int order = Integer.valueOf(orderCode);
			if(order<10){
				orderCode = "00"+order; 
			}else if(order<100){
				orderCode = "0"+order; 
			}
			code.append(orgCode).append("-").append(orderCode);
		}**/
		return code.toString();
	}
	public static String strToParamSql(String value){
		String result="";
		String[] strs=value.split(",");
		for(String s:strs){
			s="\'"+s+"\'";
			result+=s+",";
		}
		int index=result.lastIndexOf(",");
		result =result.substring(0,index);
		return result;
	}
	public static List<Object> strToList(String value){
		List<Object> result=new ArrayList<Object>();
		String[] strs=value.split(",");
		for(String s:strs){
			result.add(s);
		}
		return result;
	}
	public static List<Map<String,Object>> strToParamSqlList(String value){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String[] strs=value.split(",");
		for(String s:strs){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("roleCode", s);
			list.add(map);
		}
		return list;
	}
}
