package com.education.common.config.base.vo;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 自定义Page对象
 * @author 		jamesli
 * @version 	1.0
 * @date		2016年6月5日
 * @param <T>
 */
public class Pager<T> implements Serializable {
    private static final long serialVersionUID = 8656597559014685635L;
    //总记录数
    private long total;  
    //结果集
    private List<T> list;
    // 第几页
    private int pageNum;
    // 每页记录数
    private int pageSize;
    // 总页数
    private int pages;   
    // 当前页的数量 <= pageSize，该属性来自ArrayList的size属性
    private int size;       
    /**
     * 默认构造方法
     */
    public Pager(){
    	
    }
    /**
     * 包装Page对象，因为直接返回Page对象，在JSON处理以及其他情况下会被当成List来处理，
     * 而出现一些问题。
     * @param list          page结果
     * @param navigatePages 页码数量
     */
    public Pager(List<T> list) {
        if (list instanceof com.github.pagehelper.Page) {
        	com.github.pagehelper.Page<T> page = (com.github.pagehelper.Page<T>) list;
            this.pageNum = page.getPageNum();
            this.pageSize = page.getPageSize();
            this.total = page.getTotal();
            this.pages = page.getPages();
            this.list = page;
            this.size = page.size();
        }
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
    /**
     * 转化为JSON对象
     * @return
     */
    public JSONObject toJSONObject(){
    	JSONObject json = new JSONObject();
		json.put("page", this.pageNum);
		json.put("total", this.total);
		json.put("size", this.size);
		JSONArray jsonArray = new JSONArray();
		if(list!=null&&!list.isEmpty()){
			String jsonStr= JSON.toJSONString(list, true); 
			jsonArray = JSONArray.parseArray(jsonStr);
		}
		json.put("rows", jsonArray);
    	return json;
    
    }
    
    /**
     * 转化为String对象
     */
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Pager:{");
        sb.append("pageNum=").append(pageNum);
        sb.append(", pageSize=").append(pageSize);
        sb.append(", size=").append(size);
        sb.append(", total=").append(total);
        sb.append(", pages=").append(pages);
        sb.append(", list=").append(list);
        sb.append("}");
        return sb.toString();
    }
}