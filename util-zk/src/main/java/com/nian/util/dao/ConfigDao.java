package com.nian.util.dao;

import com.nian.util.model.Config;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(ConfigDao.class);

    public void save(Config config) {
        //存入配置中心以及写入DB
        logger.info("config|{} have save to DB.", config);
    }


    public void update(Config config) {
        //存入配置中心以及写入DB
        logger.info("config|{} have update to DB.", config);
    }

    public void delete(int id) {
        logger.info("config|{} have delete from DB.", id);
    }
    
}
