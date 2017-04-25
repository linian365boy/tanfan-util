package com.nian.util.service;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

/**
 * @author tanfan
 * @ClassName: RedisService
 * @Description:
 * @date: 2017/4/25 13:54
 * @since JDK 1.7
 */
@Service
public class RedisService {
    private static final Logger logger = LoggerFactory.getLogger(RedisService.class);
    @Autowired
    private JedisCluster jedisCluster;

    /**
     * set进redis
     * @param key redis保存的是key的字节
     * @param data redis保存的是data的字节
     */
    public void set(String key, Object data){
        try{
            String value = new Gson().toJson(data);
            jedisCluster.set(key.getBytes(), value.getBytes());
        }catch(Exception e){
            logger.error("redis cluster set error.", e);
        }
    }

    /**
     * get 出redis
     * @param key
     * @param clazz
     */
    public <T> T get(String key, Class<T> clazz){
        try {
            byte[] bytes = jedisCluster.get(key.getBytes());
            return new Gson().fromJson(new String(bytes), clazz);
        }catch (Exception e){
            logger.error("redis cluster get error.", e);
        }
        return null;
    }

    public void closeRedisCluster() {
        try{
            if(jedisCluster != null) {
                jedisCluster.close();
            }
        }catch (Exception e){
            logger.error("close redis cluster error", e);
        }
    }
}
