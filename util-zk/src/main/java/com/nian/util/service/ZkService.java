package com.nian.util.service;

import com.nian.util.model.Config;
import org.I0Itec.zkclient.ZkClient;
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

    private ZkClient zkClient;

    public void save(Config config) {
       // zkClient.create("");
    }


}
