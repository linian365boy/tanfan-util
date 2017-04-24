package com.nian.util.service;

import com.nian.util.server.BusinessServer;

import java.util.List;

/**
 * @author tanfan
 * @ClassName: BusinessServerService
 * @Description:
 * @date: 2017/4/20 17:29
 * @since JDK 1.7
 */
public interface BusinessServerService {
    /**
     * 获取所有的业务服务器
     * @return
     */
    public List<BusinessServer> getServers();
}
