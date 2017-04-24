package com.nian.util.server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.nian.util.listener.ConfigChildrenListener;
import com.nian.util.model.Config;
import org.I0Itec.zkclient.ZkClient;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.zookeeper.CreateMode;
import java.beans.Transient;
import java.util.List;

/**
 * @author niange
 * @ClassName: BusinessServer
 * @desp: 业务服务器
 * @date: 2017/4/17 下午9:40
 * @since JDK 1.7
 */
public class BusinessServer {
    /**
     * 机器ip
     */
    private String ip;
    /**
     * 服务器节点
     */
    private String serverPath;
    /**
     * 服务器名称
     */
    private String name;
    /**
     * zkClient
     */
    private ZkClient zkClient;
    /**
     * 服务器的所有的配置
     */
    private List<Config> configs;
    /**
     * 配置节点
     */
    private String configPath;

    public BusinessServer(String ip, String serverPath, ZkClient zkClient,
                          List<Config> configs, String configPath) {
        this.ip = ip;
        this.serverPath = serverPath;
        this.zkClient = zkClient;
        this.configs = configs;
        this.configPath = configPath;
    }

    public BusinessServer(String ip, String name, String serverPath, ZkClient zkClient,
                          List<Config> configs, String configPath) {
        this(ip, serverPath, zkClient, configs, configPath);
        this.name = name;
    }

    public void start(){
        //创建该服务器的临时节点
        zkClient.create(serverPath+"/"+this.getName(), new Gson().toJson(configs).getBytes(), CreateMode.EPHEMERAL);
        //订阅config子节点变化，并更新自己的配置
        zkClient.subscribeChildChanges(configPath, new ConfigChildrenListener(zkClient, this));
    }

    public String getServerPath() {
        return serverPath;
    }

    public void setServerPath(String serverPath) {
        this.serverPath = serverPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Transient
    public ZkClient getZkClient() {
        return zkClient;
    }

    public void setZkClient(ZkClient zkClient) {
        this.zkClient = zkClient;
    }

    public List<Config> getConfigs() {
        return configs;
    }

    public void setConfigs(List<Config> configs) {
        this.configs = configs;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getConfigPath() {
        return configPath;
    }

    public void setConfigPath(String configPath) {
        this.configPath = configPath;
    }

    @Override
    public String toString(){
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
