package com.nian.util;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author niange
 * @ClassName: RedisClusterSample
 * @desp: 使用redis 3.0的官方自带的集群方式
 * @date: 2017/4/2 下午12:21
 * @since JDK 1.7
 */
public class RedisClusterSample {

    private static JedisCluster jedisCluster;

    public static JedisCluster getCluster(){
        if(jedisCluster == null){
            HostAndPort hostAndPort7000 = new HostAndPort("192.168.139.159", 7000);
            HostAndPort hostAndPort7001 = new HostAndPort("192.168.139.159", 7001);
            HostAndPort hostAndPort7002 = new HostAndPort("192.168.139.159", 7002);
            HostAndPort hostAndPort7003 = new HostAndPort("192.168.139.159", 7003);
            HostAndPort hostAndPort7004 = new HostAndPort("192.168.139.159", 7004);
            HostAndPort hostAndPort7005 = new HostAndPort("192.168.139.159", 7005);

            JedisPoolConfig config = new JedisPoolConfig();
            config.setTestOnBorrow(true);
            config.setMaxIdle(8);
            config.setMaxTotal(8);
            Set<HostAndPort> hostAndPorts = new HashSet<>();
            hostAndPorts.add(hostAndPort7000);
            hostAndPorts.add(hostAndPort7001);
            hostAndPorts.add(hostAndPort7002);
            hostAndPorts.add(hostAndPort7003);
            hostAndPorts.add(hostAndPort7004);
            hostAndPorts.add(hostAndPort7005);
            jedisCluster = new JedisCluster(hostAndPorts, 15000, config);
        }
        return jedisCluster;
    }

    public static void main(String[] args){
        long start = System.currentTimeMillis();
        JedisCluster jc = getCluster();
        Map<String, JedisPool> map = jc.getClusterNodes();
        for(Map.Entry<String, JedisPool> entry : map.entrySet()){
            System.out.println("redis cluster : "+entry.getKey()+" "+entry.getValue());
        }
        String setResp = jc.set("nima", "niubi");
        System.out.println("set result = "+setResp);
        String value = jc.get("nima");
        System.out.println("heheda-=-=-===="+value);
        System.out.println((System.currentTimeMillis() - start) +" ms ");
    }
}
