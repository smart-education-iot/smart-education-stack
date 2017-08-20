package com.education.common.config.conf;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//import org.springframework.session.data.redis.RedisOperationsSessionRepository;
//import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
//import org.springframework.session.data.redis.config.annotation.web.http.RedisHttpSessionConfiguration;
//import org.springframework.session.web.http.SessionRepositoryFilter;
//import com.inspur.base.web.CookieHttpSessionStrategy;
//
///**
// * Created by jamesli on 16/4/6.
// */
//@Configuration  
//@EnableRedisHttpSession
//public class HttpSessionConfig {
//	/**
//	 * 默认2个小时：2*60*60
//	 */
//	@Value("${server.session-timeout:7200}")
//    private int maxInactiveIntervalInSeconds;
//	
//	/**
//	 * Jedis连接工厂
//	 */
//	@Autowired
//	public RedisConnectionFactory redisConnectionFactory;
//	
//	/**
//	 * Key 序列化
//	 * @return
//	 */
//	@Bean
//	public StringRedisSerializer keySerializer(){
//		return new org.springframework.data.redis.serializer.StringRedisSerializer();
//	}
//	
//	
////	@Bean
////	public RedisOperationsSessionRepository sessionRepository(){
////		return new RedisOperationsSessionRepository(redisConnectionFactory);
////	}
//	
//	/**
//	 * Session
//	 * @return
//	 */
//    @Primary
//    @Bean
//    public RedisOperationsSessionRepository sessionRepository(){
//        RedisOperationsSessionRepository sessionRepository = new RedisOperationsSessionRepository(redisConnectionFactory);
//        // customize
//        sessionRepository.setDefaultMaxInactiveInterval(maxInactiveIntervalInSeconds);
//        return sessionRepository;
//    }
//	
//	/**
//	 * redisConfiguration
//	 * @return
//	 */
//	@Bean
//	public RedisHttpSessionConfiguration redisConfiguration(){
//		RedisHttpSessionConfiguration config = new RedisHttpSessionConfiguration();
//		config.setHttpSessionStrategy(httpSessionStrategy());
//		config.setMaxInactiveIntervalInSeconds(maxInactiveIntervalInSeconds);
//		return config;
//	}
//
//	
//	/**
//	 * RedisTemplate
//	 * @return
//	 */
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	@Bean
//	public RedisTemplate redisTemplate(){
//		RedisTemplate redisTpl = new RedisTemplate();
//		redisTpl.setConnectionFactory(redisConnectionFactory);
//		redisTpl.setKeySerializer(keySerializer());
//		//redisTpl.setValueSerializer(keySerializer());//new GenericToStringSerializer<Long>(Long.class));
//		return redisTpl;
//	}
//	
//	/**
//	 * Session Strategy
//	 * @return
//	 */
//	@Bean
//	public CookieHttpSessionStrategy httpSessionStrategy(){
//		return new CookieHttpSessionStrategy();
//	}
//	
//	/**
//	 * Session Filter
//	 * @return
//	 */
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	@Bean
//	public SessionRepositoryFilter<?> sessionFilter(){
//		SessionRepositoryFilter filter = new SessionRepositoryFilter(sessionRepository());
//		filter.setHttpSessionStrategy(httpSessionStrategy());
//		return filter;
//	}
//	
//}
