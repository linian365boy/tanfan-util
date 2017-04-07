package com.nian.util.service;

import com.nian.util.model.Config;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author tanfan
 * @ClassName: ConfigServiceImplTest
 * @Description:    配置测试类
 * @date: 2017/4/7 10:52
 * @since JDK 1.7
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "applicationContext.xml")
public class ConfigServiceImplTest {

    @Autowired
    private ConfigService configService;

    @org.junit.Test
    public void saveConfig() throws Exception {
        Config config = new Config();
        config.setId(1);
        config.setKey("test.switch");
        config.setValue("on");
        config.setComment("控制测试的开关");
        configService.saveConfig(config);
    }

    @org.junit.Test
    public void updateConfig() throws Exception {
        Config config = configService.getById(1);
        config.setValue("off");
        configService.updateConfig(config);
    }

    @org.junit.Test
    public void deleteConfig() throws Exception {
        configService.deleteConfig(1);
    }

    @org.junit.Test
    public void getConfigs() throws Exception {
        List<Config> allConfigs = configService.getConfigs();

    }

    @Test
    public void getById(){
        Config config = configService.getById(1);
        assertNotNull(config);
        assertEquals("test.switch",config.getKey());
    }


}