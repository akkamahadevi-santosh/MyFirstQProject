package com.qutap.dash.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
/*import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;*/

import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizeConfig;


@Configuration
@EnableCaching
//@Profile("hazelcast-cache")
public class MongoCaching extends CachingConfigurerSupport{
   /* @Bean
    public JedisConnectionFactory jedisConnectionFactory()
    {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName("127.0.0.1");
        jedisConnectionFactory.setPort(6379);
        jedisConnectionFactory.getPoolConfig().setMaxIdle(30);
        jedisConnectionFactory.getPoolConfig().setMinIdle(10);
        return jedisConnectionFactory;
    }
    @Bean
    public RedisTemplate<Object, Object> redisTemplate()
    {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<Object, Object>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        redisTemplate.setExposeConnection(true);
        return redisTemplate;
    }
    @Bean
    public RedisCacheManager cacheManager()
    {
        RedisCacheManager redisCacheManager = new RedisCacheManager(redisTemplate());
        return redisCacheManager;
    }*/
	
	/*@Bean
    public JedisConnectionFactory redisConnectionFactory() {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setUsePool(true);
        return jedisConnectionFactory;
    }
    @Bean
    public RedisSerializer redisStringSerializer() {
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        return stringRedisSerializer;
    }
    @Bean(name="redisTemplate")
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory cf,RedisSerializer redisSerializer) {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<String, String>();
        redisTemplate.setConnectionFactory(cf);
        redisTemplate.setDefaultSerializer(redisSerializer);
        return redisTemplate;
    }
    @Bean
    public CacheManager cacheManager() {
        return new RedisCacheManager(redisTemplate(redisConnectionFactory(),redisStringSerializer()));
    }*/
    
	
	/*@Bean
    public Config hazelCastConfig() {

        Config config = new Config();
        config.setInstanceName("hazelcast-cache");

        MapConfig allUsersCache = new MapConfig();
        allUsersCache.setTimeToLiveSeconds(20);
        allUsersCache.setEvictionPolicy(EvictionPolicy.LFU);
        config.getMapConfigs().put("alluserscache",allUsersCache);

        MapConfig usercache = new MapConfig();
        usercache.setTimeToLiveSeconds(20);
        usercache.setEvictionPolicy(EvictionPolicy.LFU);
        config.getMapConfigs().put("usercache",usercache);

        return config;
    }*/
	
//	@Bean
//    public Config hazelcastConfig(){
//       return new Config().setInstanceName("hazelcast-instance")
//                .addMapConfig(new MapConfig().setName("itemCache")
//                .setMaxSizeConfig(new MaxSizeConfig(300,MaxSizeConfig.MaxSizePolicy.FREE_HEAP_SIZE))
//                .setEvictionPolicy(EvictionPolicy.LRU)
//                .setTimeToLiveSeconds(2000));
//    }
	
}