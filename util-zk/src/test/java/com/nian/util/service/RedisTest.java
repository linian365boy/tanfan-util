package com.nian.util.service;

import com.nian.util.constant.Constants;
import com.nian.util.server.BusinessServer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author tanfan
 * @ClassName: RedisTest
 * @Description:
 * @date: 2017/4/25 17:06
 * @since JDK 1.7
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class RedisTest {
    private static final Logger logger = LoggerFactory.getLogger(RedisTest.class);
    @Autowired
    private RedisService redisService;

    @Test
    public void testSet(){
        redisService.set("hello","world");
    }

    @Test
    public void testGet(){
        String data = redisService.get("hello", String.class);
        logger.info("test Get from redis cluster, data value is |{}", data);
        assertNotNull(data);
        assertEquals("world", data);
    }

    @Test
    public void testGetServer(){
        for(int i=0;i<5;i++){
            BusinessServer server = redisService.get("servername"+i, BusinessServer.class);
            logger.info("test Get from redis cluster, data value is |{}", server);
            assertNotNull(server);
            assertEquals("192.168.1."+(i+1), server.getIp());
        }
    }

    @After
    public void after(){
        redisService.closeRedisCluster();
    }

}
