package com.nian.util.service;

import com.google.common.collect.Lists;
import com.nian.util.constant.Constants;
import com.nian.util.model.Config;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author tanfan
 * @ClassName: ZkService
 * @Description:    管理zookeeper服务
 * @date: 2017/4/7 11:17
 * @since JDK 1.7
 */
@Service
public class ZkService {
    private static final Logger logger = LoggerFactory.getLogger(ZkService.class);
    @Autowired
    private ZkClient zkClient;

    public boolean save(Config config) {
        try{
            String path = zkClient.create(Constants.CONFIG_PATH_PRE+"/"+config.getId(), config, CreateMode.PERSISTENT);
            if(!StringUtils.isEmpty(path)){
                //通知各业务方
                //TODO

                return true;
            }
            return false;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(Config config){
        try {
            zkClient.writeData(Constants.CONFIG_PATH_PRE + "/" + config.getId(), config);
            //通知各业务方
            //TODO
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(int id){
        //通知各业务方
        //TODO
        return zkClient.delete(Constants.CONFIG_PATH_PRE+"/"+id);
    }

    public Config getConfig(int id){
        return zkClient.readData(Constants.CONFIG_PATH_PRE+"/"+id);
    }

    public List<Config> getAllConfig(){
        List<Config> configs = Lists.newArrayList();
        List<String> list = zkClient.getChildren(Constants.CONFIG_PATH_PRE);
        for (String path : list) {
            Config config = zkClient.readData("/config/"+path);
            configs.add(config);
        }
        return configs;
    }

}
