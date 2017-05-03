package com.nian.util.service;

import com.google.gson.Gson;
import com.nian.util.constant.Constants;
import com.nian.util.model.Config;
import com.nian.util.server.BusinessServer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tanfan
 * @ClassName: BusinessServerServiceImpl
 * @Description:
 * @date: 2017/4/20 17:29
 * @since JDK 1.7
 */
@Service
public class BusinessServerServiceImpl implements BusinessServerService {
    private static final Logger logger = LoggerFactory.getLogger(BusinessServerServiceImpl.class);
    @Autowired
    private ZkService zkService;
    @Autowired
    private RedisService redisService;

    public List<BusinessServer> getServers() {
        List<String> servers =
                this.getServerNodes(Constants.SERVER_PATH_PRE);
        logger.info("getServers return datas|{}", servers);
        List<BusinessServer> serversList = new ArrayList<>(servers.size());
        for(String str : servers){
            serversList.add(this.getServerByName(str));
        }
        return serversList;
    }

    public List<String> getServerNodes(String parentPath){
        return zkService.getNodesByParent(parentPath);
    }

    public BusinessServer getServerByName(String name){
        BusinessServer server = redisService.get(name, BusinessServer.class);
        logger.info("get BusinessServer|{} from redis.", server);
        return server;
    }

    public void start(BusinessServer server){
        //判断父节点是否存在
        if(!zkService.exists(server.getServerPath())){
            zkService.createPersistent(server.getServerPath());
        }
        //创建该服务器的临时节点
        String path = zkService.createEphemeral(server.getServerPath()+"/"+server.getName(),
                new Gson().toJson(server.getConfigs()).getBytes());
        if(StringUtils.isNotBlank(path)){
            logger.info("zkClient create path | {}, path | {} success.", path, server.getServerPath()+"/"+server.getName());
        }
        //订阅config子节点变化，并更新自己的配置
        zkService.subscribeConfigChildChanges(server);
        //保持服务器的信息到redis缓存中
        redisService.set(server.getName(), server);
    }

    /**
     * 推送配置到远程业务服务器
     */
    public void pushConfigs(){
        //通知各业务方
        //从存储介质中获取所有的业务服务器。
        List<BusinessServer> businessServers = getServers();
        logger.info("pushConfigToBusiness get businessServers from | {}", businessServers);
        //分发配置到各业务端
        //获取所有配置
        List<Config> configs = zkService.getDataByParent(Constants.CONFIG_PATH_PRE);
        for (BusinessServer server : businessServers) {
            server.updateConfig(configs);
            //修改zookeeper节点server保存的config数据
            zkService.updateData(server.getServerPath()+"/"+server.getName(),
                    new Gson().toJson(server.getConfigs()).getBytes());
        }
    }
}
