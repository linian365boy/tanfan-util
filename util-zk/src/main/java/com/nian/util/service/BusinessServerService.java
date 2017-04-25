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

    /**
     * 获取所有业务服务器节点相对path
     * @param parentPath node path
     * @return
     */
    public List<String> getServerNodes(String parentPath);

    /**
     * 根据name获取业务服务器节点
     * @param name
     * @return
     */
    BusinessServer getServerByName(String name);

    /**
     * 启动业务服务器
     * @param server 业务服务器
     */
    public void start(BusinessServer server);

    /**
     * 推送配置到业务服务器
     */
    public void pushConfigs();
}
