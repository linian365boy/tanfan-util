package com.nian.util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import java.util.Arrays;
import java.util.List;

/**
 * Hello world!
 *
 */
public class RedisSample {

    private static JedisPool pool;

    public static JedisPool getPool(){
        if(pool == null){
            JedisPoolConfig config = new JedisPoolConfig();
            //默认为true。当连接资源耗尽时，是否阻塞。false报异常
            config.setBlockWhenExhausted(true);
            //设置的逐出策略类名, 默认DefaultEvictionPolicy(当连接超过最大空闲时间,或连接数超过最大空闲连接数)
            config.setEvictionPolicyClassName("org.apache.commons.pool2.impl.DefaultEvictionPolicy");
            //是否启用pool的jmx管理功能, 默认true
            config.setJmxEnabled(true);
            config.setJmxNamePrefix("jedisPoolTanfan");
            //是否启用后进先出, 默认true
            config.setLifo(true);
            //最大空闲连接数, 默认8个
            config.setMaxIdle(8);
            //最大连接数
            config.setMaxTotal(8);
            //获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,  默认-1
            config.setMaxWaitMillis(-1);
            //逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
            config.setMinEvictableIdleTimeMillis(1800000);
            //最小空闲连接数
            config.setMinIdle(0);
            //每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
            config.setNumTestsPerEvictionRun(3);
            //对象空闲多久后逐出, 当空闲时间>该值 且 空闲连接>最大空闲数 时直接逐出,不再根据MinEvictableIdleTimeMillis判断  (默认逐出策略)
            config.setSoftMinEvictableIdleTimeMillis(1800000);
            //在获取连接的时候检查有效性, 默认false
            config.setTestOnBorrow(true);
            //在空闲时检查有效性, 默认false
            config.setTestWhileIdle(false);
            //逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
            config.setTimeBetweenEvictionRunsMillis(-1);
            pool = new JedisPool(config, "127.0.0.1", 6379);
        }
        return pool;
    }

    public static void returnResource(JedisPool pool){
        if(pool != null){
            Jedis jedis = pool.getResource();
            jedis.close();
        }
    }

    public static String getByString(String key){
        String value = null;
        try{
            pool = getPool();
            Jedis jedis = pool.getResource();
            value = jedis.get(key);
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            returnResource(pool);
        }
        return value;
    }

    public static byte[] getByByte(String key){
        byte[] value = null;
        try{
            pool = getPool();
            Jedis jedis = pool.getResource();
            value = jedis.get(key.getBytes());
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            returnResource(pool);
        }
        return value;
    }

    public static void setList(String key, List<String> valueList){
        try{
            pool = getPool();
            Jedis jedis = pool.getResource();
            Gson gson = new Gson();
            String value = gson.toJson(valueList);
            jedis.set(key.getBytes(), value.getBytes());
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            returnResource(pool);
        }

    }

    public static void main( String[] args ) {
        String key = "foo";
        String value =  getByString(key);
        System.out.println(value);

        setList("niubi", Arrays.asList("a","ab","abc"));
        byte[] valueByte = getByByte("niubi");
        String str = new String(valueByte);
        Gson gson = new Gson();
        List<String> valueList = gson.fromJson(str, new TypeToken<List<String>>(){}.getType());
        for(String valueStr : valueList){
            System.out.println(valueStr);
        }
    }
}
