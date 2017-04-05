package com.nian.util;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.util.ArrayList;
import java.util.List;

/**
 * @author niange
 * @ClassName: RedisShardSample
 * @desp: 使用客户端一致性哈希算法对redis分片
 * @date: 2017/4/1 下午10:18
 * @since JDK 1.7
 */
public class RedisShardSample {

    private static ShardedJedisPool pool;

    //客户端，一致性哈希分片
    private static ShardedJedisPool getPool(){
        if(pool == null){
            //分片1，不同的redis server
            JedisShardInfo shardInfo = new JedisShardInfo("",1234);
            //分片2，不同的redis server
            JedisShardInfo shardInfo2 = new JedisShardInfo("", 1235);
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMinIdle(8);
            config.setMaxTotal(8);
            List<JedisShardInfo> shardInfos = new ArrayList<JedisShardInfo>();
            shardInfos.add(shardInfo);
            shardInfos.add(shardInfo2);
            pool = new ShardedJedisPool(config, shardInfos);
        }
        return pool;
    }

    public static void main(String[] args){
        ShardedJedisPool pool = getPool();
        ShardedJedis jedis = pool.getResource();
        String hostName = jedis.getShard("key").getClient().getHost();
        System.out.println("sharded hostName = "+ hostName);
        jedis.set("key", "value");
    }
}
