package com.nian.util.server;

import com.nian.util.model.Config;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.Serializable;
import java.util.List;

/**
 * @author niange
 * @ClassName: BusinessServer
 * @desp: 业务服务器
 * @date: 2017/4/17 下午9:40
 * @since JDK 1.7
 */
public class BusinessServer implements Serializable {
    private static final long serialVersionUID = -8643073374822261059L;
    private static final Logger logger = LoggerFactory.getLogger(BusinessServer.class);
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
     * 服务器的所有的配置
     */
    private transient List<Config> configs;
    /**
     * 配置节点
     */
    private String configPath;

    public BusinessServer(String ip, String serverPath,
                          List<Config> configs, String configPath) {
        this.ip = ip;
        this.serverPath = serverPath;
        this.configs = configs;
        this.configPath = configPath;
    }

    public BusinessServer(String name){
        this.name = name;
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

    public List<Config> getConfigs() {
        return configs;
    }

    public void setConfigs(List<Config> configs) {
        logger.info("{} update my config|{}", this.getIp()+"_"+this.getName(), configs);
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
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE, true);
    }

    public void updateConfig(List<Config> configs) {
        //更新业务服务器的配置
        this.setConfigs(configs);
        //远程更新配置
        String ip = this.getIp();

    }
}
