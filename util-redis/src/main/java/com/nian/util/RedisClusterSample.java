package com.nian.util;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author niange
 * @ClassName: RedisClusterSample
 * @desp: 使用redis 3.0的官方自带的集群方式
 * @date: 2017/4/2 下午12:21
 * @since JDK 1.7
 */
public class RedisClusterSample {

    public static JedisCluster js;

    public static JedisCluster getJedisCluster(){
        if(js == null){
            HostAndPort hostAndPort = new HostAndPort("127.0.0.1", 6379);
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(8);
            config.setMinIdle(8);
            js = new JedisCluster(hostAndPort, config);
        }
        return js;
    }

    public static void main(String[] args){
        JedisCluster js = getJedisCluster();
        js.set("key", "value");
        String string = js.get("key");
        System.out.println(string);
    }
}
