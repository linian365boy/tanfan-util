package com.nian.util.service;

import com.nian.util.model.Config;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author tanfan
 * @ClassName: ZkService
 * @Description:    管理zookeeper服务
 * @date: 2017/4/7 11:17
 * @since JDK 1.7
 */
@Service
public class ZkService {

    @Autowired
    private ZkClient zkClient;

    public void save(Config config) {
        //zkClient.
        zkClient.create("/config/"+config.getId(), config, CreateMode.PERSISTENT);
    }

    public void update(Config config){
        zkClient.writeData("/config/"+config.getId(), config);
    }

    public void delete(int id){
        zkClient.delete("/config/"+id);
    }

    public Config getConfig(int id){
        return zkClient.readData("/config/"+id);
    }

}
