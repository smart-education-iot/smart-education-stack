package com.education.common.config.base;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import com.education.common.config.base.vo.Pager;
import com.github.pagehelper.PageHelper;

/**
 * 抽象Repository
 * @author James Li
 * @param <T>
 */
@SuppressWarnings("rawtypes")
public abstract class BaseRepository<T> {
	/**
	 * Log日志
	 */
	private Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 注入SQLSessionTemplate
	 */
    @Autowired
	//@Qualifier("masterSqlSessionTemplate")
	public SqlSessionTemplate sqlSessionTemplate;
    
	/**
	 * 获取SqlSession
	 * @param template
	 * @param isAuto
	 * @return
	 */
	private SqlSession getSqlSession(SqlSessionTemplate template,boolean isAuto){
		if(template == null){
			//sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, isAuto);
			return sqlSessionTemplate;
		}else{
			return template.getSqlSessionFactory().openSession(ExecutorType.BATCH, isAuto);
		}
	}
	/**
	 * 更新参数
	 * @param sqlid
	 * @param paramObj
	 * @return
	 */
	public int update(String sql,Object param) {        
		SqlSession session= this.getSqlSession(null, true);
		return session.update(sql, param);    
	}  
	/**
	 * 更新参数
	 * @param sqlid
	 * @param paramObj
	 * @return
	 */
	public int update(String sql) {    
		SqlSession session= this.getSqlSession(null, true);
		return session.update(sql);    
	}
	/**
	 * 插入参数
	 * @param sqlid
	 * @param paramObj
	 * @return
	 */
	public int save(String sql,Object param) {        
		SqlSession session= this.getSqlSession(null, true);
		return session.insert(sql, param);    
	}  
	/**
	 * 插入参数
	 * @param sqlid
	 * @param paramObj
	 * @return
	 */
	public int save(String sql) {    
		SqlSession session= this.getSqlSession(null, true);
		return session.insert(sql);    
	}
	/**
	 * 插入参数
	 * @param sqlid
	 * @param paramObj
	 * @return
	 */
	public int save(SqlSessionTemplate template,String sql,Object param) {        
		SqlSession session= this.getSqlSession(template, true);
		return session.insert(sql, param);    
	}  
	/**
	 * 删除参数
	 * @param sqlid
	 * @param paramObj
	 * @return
	 */
	public int delete(String sql,Object param) {        
		SqlSession session= this.getSqlSession(null, true);
		return session.delete(sql, param);    
	}  
	/**
	 * 删除参数
	 * @param sqlid
	 * @param paramObj
	 * @return
	 */
	public int delete(String sql) {    
		SqlSession session= this.getSqlSession(null, true);
		return session.insert(sql);    
	}
	/**
	 * 插入参数
	 * @param sqlid
	 * @param paramObj
	 * @return
	 */
	public int delete(SqlSessionTemplate template,String sql,Object param) {        
		SqlSession session= this.getSqlSession(template, true);
		return session.delete(sql, param);    
	}  
    /**
     * 批量插入
     * 方法描述：批量插入（效率没有在配置文件上的高）
     * @param statementName
     * @param list
     * @throws DataAccessException
     * @author jamesli
     * @date 2014-8-3 上午11:27:39
     * @comment
     */
    @SuppressWarnings("null")
	public void batchInsert(SqlSessionTemplate sqlSessionTemplate,final String statementName, final List<?> list)  throws DataAccessException{
    	SqlSession session =  this.getSqlSession(sqlSessionTemplate, false);
        int size = 10000;
        try{
            if(null != list || list.size() > 0){
                for (int i = 0, n = list.size(); i < n; i++) {
                    session.insert(statementName, list.get(i));
                    if (i % 1000 == 0 || i == size - 1) {
                        //手动每1000个一提交，提交后无法回滚
                        session.commit();
                        //清理缓存，防止溢出
                        session.clearCache();
                    }
                }
            }
        }catch (Exception e){
            session.rollback();
            if (logger.isDebugEnabled()) {
                //e.printStackTrace();
            	logger.debug("batchInsert error: id [" + statementName + "], parameterObject [" + list + "].  Cause: " + e.getMessage());
            }
        } finally {
        	if(session != null){
        		session.close();
        	}
        }
    }
    /**
     * 批量插入
     * 方法描述：批量插入（效率没有在配置文件上的高）
     * @param statementName
     * @param list
     * @throws DataAccessException
     * @author jamesli
     * @throws SQLException 
     * @date 2014-8-3 上午11:27:39
     * @comment
     */
	public void batchInsert(final String statementName, final List<?> list)  throws DataAccessException, SQLException{
        this.batchInsert(null,statementName, list);
    }
	
    /**     
     * 批量更新     
	 * 方法描述：批量更新（效率没有在配置文件上的高）     
	 * @param statementName     
	 * @param list    
	 * @throws DataAccessException    
	 * @author jamesli     
	 * @date 2014-8-3 上午11:14:37     
	 * @comment     
	 */    
    @SuppressWarnings("null")
	public void batchUpdate(SqlSessionTemplate template,final String statementName, final List<?> list)  throws DataAccessException{        
    	SqlSession session = this.getSqlSession(template, false);        
    	try{            
    		if(null != list || list.size() > 0){                
    			int size = 10000;                             
    			for (int i = 0, n = list.size(); i < n; i++) {                    
    				session.update(statementName, list.get(i));                    
    				if (i % 1000 == 0 || i == size - 1) {                        
    					//手动每1000个一提交，提交后无法回滚                        
    					session.commit();                        
    					//清理缓存，防止溢出                        
    					session.clearCache();                    
    				}
    			}
    		}            
    	}catch (Exception e){            
    			session.rollback();            
    			if (logger.isDebugEnabled()) {                
    				e.printStackTrace();                
    				logger.debug("batchUpdate error: id [" + statementName + "], parameterObject [" + list + "].  Cause: " + e.getMessage());           
    			}        
    	} finally {            
    			session.close();        
    	}    
    }
    /**     
     * 批量更新     
	 * 方法描述：批量更新（效率没有在配置文件上的高）     
	 * @param statementName     
	 * @param list    
	 * @throws DataAccessException    
	 * @author jamesli     
	 * @date 2014-8-3 上午11:14:37     
	 * @comment     
	 */    
	public void batchUpdate(final String statementName, final List<?> list)  throws DataAccessException{        
    	this.batchUpdate(null, statementName, list);
    }
    /**
     * 批量删除     
     * 方法描述：批量删除（效率没有在配置文件上的高）     
     * @param statementName     
     * @param list     
     * @throws DataAccessException    
     * @author jamesli     
     * @date 2014-8-3 上午11:29:53     
     * @comment     
     */    
    @SuppressWarnings("null")
	public void batchDelete(SqlSessionTemplate template,final String statementName, final List<?> list)  throws DataAccessException{   
    	//sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false); 
    	SqlSession session = this.getSqlSession(template, false);
    	int size = 10000;        
    	try{            
    		if(null != list || list.size() > 0){                
    			for (int i = 0, n = list.size(); i < n; i++) {                    
    				session.delete(statementName, list.get(i));
    				if (i % 1000 == 0 || i == size - 1) {                       
    					//手动每1000个一提交，提交后无法回滚                        
    					session.commit();                       
    					//清理缓存，防止溢出                       
    					session.clearCache();                   
    				}                
    			}            
    		}       
    	}catch (Exception e){            
    		session.rollback();            
    		if (logger.isDebugEnabled()) {                
    			//e.printStackTrace();                
    			logger.debug("batchDelete error: id [" + statementName + "], parameterObject [" + list + "].  Cause: " + e.getMessage());            
    		}        
    	} finally {            
    		session.close();        
    	}    
    }
    /**
     * 批量删除     
     * 方法描述：批量删除（效率没有在配置文件上的高）     
     * @param statementName     
     * @param list
     * @throws DataAccessException    
     * @author jamesli     
     * @date 2014-8-3 上午11:29:53     
     * @comment     
     */    
	public void batchDelete(final String statementName, final List<?> list)  throws DataAccessException{        
    	this.batchDelete(null, statementName, list);
    }
	
	/**
	 * 查询集合
	 * @param sql
	 * @param param
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "hiding" })    
	public <T> List<T> selectList(String sql, Object param) {        
		SqlSession session= this.getSqlSession(null, false);
		return (List<T>)session.selectList(sql, param);    
	}
	/**
	 * 查询集合
	 * @param template
	 * @param sql
	 * @param param
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "hiding" })    
	public <T> List<T> selectList(SqlSessionTemplate template,String sql, Object param) {        
		SqlSession session= this.getSqlSession(template, false);
		return (List<T>)session.selectList(sql, param);    
	}   
	/**
	 * 查询对象
	 * @param sql
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "hiding" })    
	public <T> List<T> selectList(String sql) {        
		SqlSession session= this.getSqlSession(null, false);
		return (List<T>) session.selectList(sql);    
	} 
	/**
	 * 查询对象
	 * @param sql
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "hiding" })    
	public <T> List<T> selectList(SqlSessionTemplate template,String sql) {        
		SqlSession session= this.getSqlSession(null, false);
		return (List<T>) session.selectList(sql);    
	} 
	/**
	 * 查询集合
	 * @param sql
	 * @param param
	 * @param rowBund
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "hiding" })    
	public <T> List<T> selectList(String sql,Object param,RowBounds rowBund) {      
		SqlSession session= this.getSqlSession(null, false);
		return (List<T>) session.selectList(sql, param, rowBund);   
	}  
	/**
	 * 查询集合
	 * @param sql
	 * @param param
	 * @param rowBund
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "hiding" })    
	public <T> List<T> selectList(SqlSessionTemplate template,String sql,Object param,RowBounds rowBund) {      
		SqlSession session= this.getSqlSession(template, false);
		return (List<T>) session.selectList(sql, param, rowBund);   
	}   
	/**
	 * 查询对象
	 * @param sql
	 * @param param
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "hiding" })    
	public <T> T selectOne(String sql) {       
		SqlSession session= this.getSqlSession(null, false);
		return (T) session.selectOne(sql);    
	} 
	/**
	 * 查询对象
	 * @param sql
	 * @param param
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "hiding" })    
	public <T> T selectOne(String sql,Object param) {       
		SqlSession session= this.getSqlSession(null, false);
		return (T) session.selectOne(sql,param);    
	}    
	/**
	 * 查询对象
	 * @param sql
	 * @param param
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "hiding" })    
	public <T> T selectOne(SqlSessionTemplate template,String sql,Object param) {       
		SqlSession session= this.getSqlSession(template, false);
		return (T) session.selectOne(sql,param);    
	}    
	/**
	 * 查询对象
	 * @param sql
	 * @param param
	 * @return
	 */
	@SuppressWarnings({ "hiding", "unchecked" })
	public <T> T selectOne(SqlSessionTemplate template,String sql) {       
		SqlSession session= this.getSqlSession(template, false);
		return (T) session.selectOne(sql);    
	}
	/**
	 * 查询对象
	 * @param sql
	 * @param param
	 * @return
	 */
	public Map<?,?> selectMap(String sql,String arg1) {        
		SqlSession session= this.getSqlSession(null, false);
		return session.selectMap(sql, arg1);    
	}    
	/**
	 * 查询对象
	 * @param sql
	 * @param param
	 * @return
	 */
	public Map<?,?> selectMap(SqlSessionTemplate template,String sql,String arg1) {        
		SqlSession session= this.getSqlSession(null, false);
		return session.selectMap(sql, arg1);    
	}    
	/**
	 * 查询对象
	 * @param sql
	 * @param param
	 * @return
	 */
	public Map<?,?> selectMap(String sql,Object param,String arg2) {        
		SqlSession session= this.getSqlSession(null, false);
		return session.selectMap(sql, param, arg2);    
	}    
	/**
	 * 查询对象
	 * @param sql
	 * @param param
	 * @return
	 */
	public Map<?,?> selectMap(SqlSessionTemplate template,String sql,Object param,String arg2) {        
		SqlSession session= this.getSqlSession(template, false);
		return session.selectMap(sql, param, arg2);    
	}   
	/**
	 * 查询对象
	 * @param sql
	 * @param param
	 * @return
	 */
	public Map<?,?> selectMap(String sql,Object param,String arg2,RowBounds arg3) {      
		SqlSession session= this.getSqlSession(null, false);
		return session.selectMap(sql, param, arg2, arg3);    
	}   
	/**
	 * 查询对象
	 * @param sql
	 * @param param
	 * @return
	 */
	public Map<?,?> selectMap(SqlSessionTemplate template,String sql,Object param,String arg2,RowBounds arg3) {      
		SqlSession session= this.getSqlSession(template, false);
		return session.selectMap(sql, param, arg2, arg3);    
	}   
	/**
	 * 查询对象
	 * @param sql
	 * @param param
	 * @return
	 */
	public void select(String sql,ResultHandler arg1) {   
		SqlSession session= this.getSqlSession(null, false);
		session.select(sql, arg1);    
	}  
	/**
	 * 查询对象
	 * @param sql
	 * @param param
	 * @return
	 */
	public void select(SqlSessionTemplate template, String sql,ResultHandler arg1) {   
		SqlSession session= this.getSqlSession(template, false);
		session.select(sql, arg1);    
	}
	/**
	 * 查询对象
	 * @param sql
	 * @param param
	 * @return
	 */
	public void select(String sql,Object param,ResultHandler arg1) {        
		SqlSession session= this.getSqlSession(null, false);
		session.select(sql, param,arg1);    
	}    
	/**
	 * 查询对象
	 * @param sql
	 * @param param
	 * @return
	 */
	public void select(SqlSessionTemplate template,String sqlid,Object paramObj,ResultHandler arg1) {        
		SqlSession session= this.getSqlSession(template, false);
		session.select(sqlid, paramObj,arg1);    
	}    
	/**
	 * 查询对象
	 * @param sql
	 * @param param
	 * @return
	 */
	public void select(String sql,Object param,RowBounds arg3,ResultHandler arg1) {       
		SqlSession session= this.getSqlSession(null, false);
		session.select(sql,param,arg3, arg1);    
	}         
	/**
	 * 查询对象
	 * @param sql
	 * @param param
	 * @return
	 */
	public void select(SqlSessionTemplate template,String sql,Object param,RowBounds arg3,ResultHandler arg1) {       
		SqlSession session= this.getSqlSession(template, false);
		session.select(sql,param,arg3, arg1);    
	}

    /**
     * 带分页参数查询
     * @param sqlId
     * @param pageNumber
     * @param pageSize
     * @param parameter
     * @return
     */
	public <E> Pager<E> selectListByPage(int pageNumber,int pageSize,String sqlId,Object parameter){
		pageNumber = pageNumber/pageSize+1;
        PageHelper.startPage(pageNumber, pageSize, true);  
        List<E> result = sqlSessionTemplate.selectList(sqlId, parameter);   
        return new Pager<E>(result);
    }  
    /**
     * 分页查询
     * @param sqlId
     * @param parameter
     * @return
     */
	public <E> Pager<E> selectListByPage(String sqlId,Map<String,Object> parameter){  
        Map<String, Object> map = (Map<String, Object>) parameter;  
        int offset = Integer.parseInt(String.valueOf( map.get("offset")));  
        //int pageSize = ServiceConstant.DEFAULT_PAGE_SIZE;
        int pageSize = Integer.parseInt(String.valueOf( map.get("limit")));
        int pageNum = offset/pageSize+1;
        PageHelper.startPage(pageNum, pageSize, true);  
        List<E> result = sqlSessionTemplate.selectList(sqlId, parameter);
        return new Pager<E>(result);
    }
//	@SuppressWarnings("unchecked")
//	public <E> Pager<E> selectDataByPage(Connection conn,String sql,JSONObject parameter,Object... paras) throws SQLException{
//		Map<String, Object> map = (Map<String, Object>) parameter;  
//	    int offset = Integer.parseInt(String.valueOf( map.get("offset")));  
//	    //int pageSize = ServiceConstant.DEFAULT_PAGE_SIZE;
//	    int pageSize = Integer.parseInt(String.valueOf( map.get("limit")));
//	    int pageNum = offset/pageSize+1;
//	    PageHelper.startPage(pageNum, pageSize, true);  
//		ResultSet rs=DataBaseHelper.query(conn, sql, paras);
//		ResultSetMetaData md = rs.getMetaData();
//		int columnCount = md.getColumnCount();
//		List<E> result=new ArrayList<E>();
//		 Map<String,Object> rowData = new HashMap<String,Object>();
//		 while (rs.next()) {   
//	           rowData = new HashMap<String,Object>(columnCount);   
//	           for (int i = 1; i <= columnCount; i++) {   
//	                   rowData.put(md.getColumnName(i), rs.getObject(i));   
//	           }
//	           result.add((E)rowData);
//		 }
//		return new Pager<E>(result);
//	}
}
