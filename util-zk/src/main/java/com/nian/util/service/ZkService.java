package com.nian.util.service;

import com.google.common.collect.Lists;
import com.google.gson.JsonSerializer;
import com.nian.util.constant.Constants;
import com.nian.util.model.Config;
import com.nian.util.server.BusinessServer;
import com.nian.util.util.PropertiesUtil;
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
                logger.info("zookeeper create config|{}, path|{} success.", config, path);
                return true;
            }
            return false;
        }catch (Exception e){
            logger.info("zookeeper create config path|{} error", Constants.CONFIG_PATH_PRE+"/"+config.getId());
        }
        return false;
    }

    public boolean update(Config config){
        try {
            zkClient.writeData(Constants.CONFIG_PATH_PRE + "/" + config.getId(), config);
            logger.info("zookeeper update config|{}", config);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(int id){
        //通知各业务方
        logger.info("zookeeper delete config|{}", id);
        return zkClient.delete(Constants.CONFIG_PATH_PRE+"/"+id);
    }

    public Config getConfig(int id){
        return zkClient.readData(Constants.CONFIG_PATH_PRE+"/"+id);
    }

    public <T> List<T> getDataByParent(String parentPath){
        List<T> datas = Lists.newArrayList();
        List<String> pathStrs = zkClient.getChildren(parentPath);
        for(String path : pathStrs){
            T t = zkClient.readData(path);
            datas.add(t);
        }
        logger.info("getDataByParent|{} return.", datas);
        return datas;
    }

    /**
     * 推送配置到远程业务服务器
     */
    public void pushConfigToBusiness(){
        //通知各业务方
        //获取所有的业务服务器。为了简单方便，把业务服务器配置在配置文件中。
        List<BusinessServer> businessServers = this.getDataByParent(Constants.SERVER_PATH_PRE);
        logger.info("pushConfigToBusiness businessServers|{}", businessServers);
        //分发配置到各业务端
        //获取所有配置
        List<Config> configs = this.getDataByParent(Constants.CONFIG_PATH_PRE);
        for (BusinessServer server : businessServers) {
            server.setConfigs(configs);
        }
    }

}
