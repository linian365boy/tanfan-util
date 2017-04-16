package com.nian.util.service;

import com.nian.util.model.Config;
import com.nian.util.dao.ConfigDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author tanfan
 * @ClassName: ConfigServiceImpl
 * @Description:    配置service实现
 * @date: 2017/4/7 10:31
 * @since JDK 1.7
 */

@Service
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    private ConfigDao configDao;
    @Autowired
    private ZkService zkService;

    public void saveConfig(Config config) {
        //存入配置中心
        if(zkService.save(config)){
            //存入DB
            configDao.save(config);
        }
    }

    public void updateConfig(Config config) {
        if(zkService.update(config)){
            configDao.update(config);
        }
    }

    public void deleteConfig(int id) {
        if(zkService.delete(id)){
            configDao.delete(id);
        }
    }

    public List<Config> getConfigs() {
        return zkService.getAllConfig();
    }

    public Config getById(int id) {
        return zkService.getConfig(id);
    }
}
