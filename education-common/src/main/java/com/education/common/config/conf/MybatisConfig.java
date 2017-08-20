package com.education.common.config.conf;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.ClassUtils;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageHelper;

@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = "com.igap.data")
public class MybatisConfig{// implements TransactionManagementConfigurer
	/**
	 * Mybatis日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(MybatisConfig.class);

	@Autowired
	private DruidDataSourceConfig druidDataSourceConfig;

	@Bean
	public DataSource dataSource() {
		if(logger.isDebugEnabled()){
			logger.debug("druidDataSourceConfig" + druidDataSourceConfig);
		}
		// 加载配置文件属性
		DruidDataSource ds = new DruidDataSource();
		ds.setDriverClassName(druidDataSourceConfig.getDriverClassName());
		ds.setUsername(druidDataSourceConfig.getUsername());
		ds.setPassword(druidDataSourceConfig.getPassword());
		ds.setUrl(druidDataSourceConfig.getUrl());
		ds.setMaxActive(druidDataSourceConfig.getMaxActive());
		ds.setValidationQuery(druidDataSourceConfig.getValidationQuery());
		ds.setTestOnBorrow(druidDataSourceConfig.isTestOnBorrow());
		ds.setTestOnReturn(druidDataSourceConfig.isTestOnReturn());
		ds.setTestWhileIdle(druidDataSourceConfig.isTestWhileIdle());
		ds.setTimeBetweenEvictionRunsMillis(druidDataSourceConfig.getTimeBetweenEvictionRunsMillis());
		ds.setMinEvictableIdleTimeMillis(druidDataSourceConfig.getMinEictableIdleTimeMillis());
		ds.setPoolPreparedStatements(druidDataSourceConfig.isPoolPreparedStatements());
		ds.setMaxOpenPreparedStatements(druidDataSourceConfig.getMaxOpenPreparedStatements());
		try {
			ds.setFilters(druidDataSourceConfig.getFilters());
		} catch (SQLException e) {
			//e.printStackTrace();
			logger.error(e.getSQLState());
		}
		return ds;
	}


	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		if(logger.isDebugEnabled()){
			logger.debug("--> sqlSessionFactory");
		}
		final SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
		sqlSessionFactory.setDataSource(dataSource());
		sqlSessionFactory.setFailFast(true);
		//sqlSessionFactory.setTransactionFactory(transactionFactory);
		//sqlSessionFactory.setTypeAliasesPackage("com.igap.igp.model");
		//1.mybatis配置
		//sqlSessionFactory.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
		Properties sqlSessionFactoryProperties = new Properties();
		sqlSessionFactoryProperties.setProperty("cacheEnabled", "true");
		sqlSessionFactoryProperties.setProperty("lazyLoadingEnabled", "true");
		sqlSessionFactoryProperties.setProperty("aggressiveLazyLoading", "true");
		sqlSessionFactoryProperties.setProperty("useGeneratedKeys", "false");
		sqlSessionFactoryProperties.setProperty("multipleResultSetsEnabled", "true");
		//sqlSessionFactoryProperties.setProperty("autoMappingBehavior", "PARTIAL");
		sqlSessionFactoryProperties.setProperty("mapUnderscoreToCamelCase", "true");
		//sqlSessionFactoryProperties.setProperty("useColumnLabel", "true");
		//SIMPLE REUSE BATCH
		sqlSessionFactoryProperties.setProperty("defaultExecutorType", "SIMPLE");
		sqlSessionFactoryProperties.setProperty("defaultStatementTimeout", "25");
		sqlSessionFactoryProperties.setProperty("jdbcTypeForNull", "NULL");
		sqlSessionFactoryProperties.setProperty("logImpl", "SLF4J");
		sqlSessionFactory.setConfigurationProperties(sqlSessionFactoryProperties);
		
		//分页插件
        PageHelper pageHelper = new PageHelper();
        Properties pageHelperProperties = new Properties();
        pageHelperProperties.setProperty("reasonable", "true");
        pageHelperProperties.setProperty("supportMethodsArguments", "true");
        pageHelperProperties.setProperty("returnPageInfo", "check");
        pageHelperProperties.setProperty("params", "pageNum=offset;pageSize=limit;");
        pageHelperProperties.setProperty("offsetAsPageNum", "true");
        pageHelperProperties.setProperty("rowBoundsWithCount", "true");
        pageHelperProperties.setProperty("pageSizeZero", "true");
        pageHelper.setProperties(pageHelperProperties);
        
        //2.添加插件
        //sqlSessionFactory.setPlugins(new Interceptor[]{pageHelper});
        //3.添加XML目录
		sqlSessionFactory.setMapperLocations(getResource("mapper", "**/*.xml"));
		return sqlSessionFactory.getObject();
	}
	/**
	 * SqlSessionTemplate
	 * @param sqlSessionFactory
	 * @return
	 * @throws Exception 
	 */
	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory());
	}
	/**
	 * TranscationManager
	 * @return
	 */
	@Bean
    public PlatformTransactionManager transactionManager() {
		if(logger.isDebugEnabled()){
			logger.debug("> transactionManager");
		}
        return new DataSourceTransactionManager(dataSource());
    }
//	@Bean
//	@Override
//	public PlatformTransactionManager annotationDrivenTransactionManager() {
//		return new DataSourceTransactionManager(dataSource());
//	}


    @PostConstruct
    public void postConstruct() {
    	if(logger.isInfoEnabled()){
    		logger.info("jdbc.settings={}", druidDataSourceConfig);
    	}
    }
	
	public Resource[] getResource(String basePackage, String pattern) throws IOException {
		String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + ClassUtils.convertClassNameToResourcePath(new StandardEnvironment().resolveRequiredPlaceholders(basePackage)) + "/" + pattern;
		Resource[] resources = new PathMatchingResourcePatternResolver().getResources(packageSearchPath);
		return resources;
	}
}