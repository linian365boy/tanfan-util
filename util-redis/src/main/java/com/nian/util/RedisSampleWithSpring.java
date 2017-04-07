package com.nian.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;

/**
 * @author niange
 * @ClassName: RedisSampleWithSpring
 * @desp:
 * @date: 2017/4/4 下午1:21
 * @since JDK 1.7
 */


public class RedisSampleWithSpring {

    private static JedisPool pool;
    private static JedisCluster jedisCluster;

    public static void main(String[] args){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        pool = (JedisPool) context.getBean("pool");
        Jedis jedis = pool.getResource();
        String value = jedis.get("nima");
        System.out.println(value);
        jedis.close();
        pool.close();

        jedisCluster = (JedisCluster) context.getBean("jedisCluster");
        String value2 = jedisCluster.get("nima");
        System.out.println(value2);
        try {
            jedisCluster.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }


}
