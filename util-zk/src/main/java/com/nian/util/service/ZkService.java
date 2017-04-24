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
                String refresh = PropertiesUtil.getValue("config.realtime.refresh", "true");
                if(Boolean.valueOf(refresh)){
                    //实时更新业务服务器的配置
                    pushConfigToBusiness();
                }
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
            String refresh = PropertiesUtil.getValue("config.realtime.refresh", "true");
            if(Boolean.valueOf(refresh)){
                //实时更新业务服务器的配置
                pushConfigToBusiness();
            }
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
        // 实际生产中，按测试、回归、预发布、生产环境区分业务服务器
        String businessServers = PropertiesUtil.getValue("businessServer");
        logger.info("pushConfigToBusiness businessServers|{}", businessServers);
        String[] servers = businessServers.split(",");
        //分发配置到各业务端
        //获取所有配置
        List<Config> configs = this.getDataByParent(Constants.CONFIG_PATH_PRE);

    }

}
