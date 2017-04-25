package com.nian.util.service;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.nian.util.constant.Constants;
import com.nian.util.listener.ConfigChildrenListener;
import com.nian.util.model.Config;
import com.nian.util.server.BusinessServer;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
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
            //判断父节点是否存在
            if(!zkClient.exists(Constants.CONFIG_PATH_PRE)){
                zkClient.createPersistent(Constants.CONFIG_PATH_PRE);
            }
            String path = zkClient.create(Constants.CONFIG_PATH_PRE+"/"+config.getId(), config, CreateMode.PERSISTENT);
            if(!StringUtils.isEmpty(path)){
                logger.info("zookeeper create config|{}, path|{} success.", config, path);
                return true;
            }
            return false;
        }catch (Exception e){
            logger.error("zookeeper create config path|{} error", Constants.CONFIG_PATH_PRE+"/"+config.getId(), e);
        }
        return false;
    }

    public boolean update(Config config){
        try {
            zkClient.writeData(Constants.CONFIG_PATH_PRE + "/" + config.getId(), config);
            logger.info("zookeeper update config|{}", config);
            return true;
        }catch (Exception e){
            logger.error("zookeeper update config |{} error", Constants.CONFIG_PATH_PRE+"/"+config.getId(), e);
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
        List<String> pathStrs = zkClient.getChildren(parentPath);
        List<T> datas = new ArrayList<>(pathStrs.size());
        for(String path : pathStrs){
            logger.info("read data from path | {}", parentPath+"/"+path);
            T t = zkClient.readData(parentPath+"/"+path);
            datas.add(t);
        }
        logger.info("getDataByParent|{} return.", datas);
        return datas;
    }

    public List<String> getNodesByParent(String parentPath) {
        return zkClient.getChildren(parentPath);
    }

    public boolean exists(String path) {
        return zkClient.exists(path);
    }


    public void createPersistent(String path) {
        zkClient.createPersistent(path);
    }


    public String createEphemeral(String path, byte[] bytes) {
        return zkClient.create(path, bytes, CreateMode.EPHEMERAL);
    }

    public Config readData(String path) {
        return zkClient.readData(path);
    }

    public void subscribeConfigChildChanges(BusinessServer server) {
        zkClient.subscribeChildChanges(server.getConfigPath(), new ConfigChildrenListener(zkClient, server));
    }

    public void updateData(String path, byte[] bytes) {
        zkClient.writeData(path, bytes);
    }
}
