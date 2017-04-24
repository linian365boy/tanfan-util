package com.nian.util.service;

import com.google.common.collect.Lists;
import com.nian.util.constant.Constants;
import com.nian.util.model.Config;
import com.nian.util.server.BusinessServer;
import org.I0Itec.zkclient.ZkClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author niange
 * @ClassName: TestBootStrap
 * @desp:
 * @date: 2017/4/17 下午9:39
 * @since JDK 1.7
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class TestBootStrap {
    private static Logger logger = LoggerFactory.getLogger(TestBootStrap.class);
    @Autowired
    public ConfigService configService;
    @Autowired
    public BusinessServerService businessServerService;
    @Autowired
    private ZkClient zkClient;
    List<Config> configList = null;

    @Before
    public void before(){
        logger.info("before set init value........");
        configList = new ArrayList<>();
        for(int i=0;i<5;i++){
            BusinessServer server = new BusinessServer("192.168.1."+(i+1),
                    Constants.SERVER_PATH_PRE, zkClient, null, Constants.CONFIG_PATH_PRE);
            server.setName("servername"+i);
            server.start();
            logger.info("start server|{} success", server);
        }
    }

    @Test
    public void testGetAllBusiness(){
        List<BusinessServer> servers = businessServerService.getServers();
        assertNotNull(servers);
        assertEquals(5, servers.size());
        logger.info("get all business server | {}", servers);
    }

    @Test
    public void testSaveConfig(){
        //模拟创建配置
        for (int i = 0; i < 10; i++) {
            Config config = new Config();
            config.setId(i);
            config.setKey("key"+i);
            config.setValue("value"+i);
            config.setComment("comment"+i);
            configList.add(config);
            configService.saveConfig(config);
            boolean flag = zkClient.exists(Constants.CONFIG_PATH_PRE+"/"+config.getId());
            assertEquals(true, flag);
        }
    }

    @Test
    public void testUpdateConfig(){
        int configId = 1;
        Config config = configService.getById(configId);
        assertNotNull(config);
        logger.info("before update config value | {}", config);
        config.setValue("niubilo");
        config.setComment("niubiComment");
        configService.updateConfig(config);
        Config tempConfig = configService.getById(configId);
        assertNotNull(tempConfig);
        logger.info("after update config value | {}", tempConfig);
    }

    @Test
    public void testDeleteConfig(){
        int configId = 2;
        configService.deleteConfig(configId);
        boolean flag = zkClient.exists(Constants.CONFIG_PATH_PRE+"/"+configId);
        assertFalse(flag);
        List<Config> configs = configService.getConfigs();
        assertTrue(configs.size()<10);
    }

    @Test
    public void testPushConfig(){
        int configId = 3;
        //1、需要先修改配置，改成非实时更新配置
        //2、修改配置
        Config config = configService.getById(configId);
        assertNotNull(config);
        logger.info("before update config value | {}", config);
        config.setValue("hello");
        config.setComment("commentniubi");
        configService.updateConfig(config);
        Config tempConfig = configService.getById(configId);
        assertNotNull(tempConfig);
        logger.info("after update config value | {}", tempConfig);
        //3、推送配置
        configService.pushConfigs();
        Config pushConfig = configService.getById(configId);
        logger.info("after push, config value | {}", pushConfig);
        assertEquals("hello", pushConfig.getValue());
    }

    @After
    public void close(){
        logger.info("zookeeper test is end.......");
    }
}
