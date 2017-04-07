package com.nian.util.dao;

import com.nian.util.model.Config;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author tanfan
 * @ClassName: ConfigDao
 * @Description: 配置的Dao层
 * @date: 2017/4/7 10:30
 * @since JDK 1.7
 */
@Service
public class ConfigDao {

    public void save(Config config) {
        //存入配置中心以及写入DB

    }


    public void update(Config config) {
        //存入配置中心以及写入DB

    }

    public void delete(int id) {


    }

    public List<Config> getConfigs() {
        return null;
    }

    public Config getById(int id){
        //zk获取配置
        return null;
    }
}
