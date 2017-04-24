package com.nian.util.listener;

import com.google.common.collect.Lists;
import com.nian.util.model.Config;
import com.nian.util.server.BusinessServer;
import com.nian.util.util.PropertiesUtil;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author tanfan
 * @ClassName: ConfigDataListener
 * @Description:
 * @date: 2017/4/10 17:34
 * @since JDK 1.7
 */
public class ConfigChildrenListener implements IZkChildListener {
    private static final Logger logger = LoggerFactory.getLogger(ConfigChildrenListener.class);
    private ZkClient zkClient;
    private BusinessServer server;

    public ConfigChildrenListener(ZkClient zkClient, BusinessServer server){
        this.zkClient = zkClient;
        this.server = server;
    }

    public void handleChildChange(String parentPath, List<String> children) throws Exception {
        List<Config> configs = Lists.newArrayList();
        for(String path : children){
            logger.info("handleChildChange parentPath|{}, path|{}", parentPath, path);
            Config config  = zkClient.readData(path);
            configs.add(config);
        }
        //更新服务器的配置
        logger.info("handleChildChange configs|{}", configs);
        String refresh = PropertiesUtil.getValue("config.realtime.refresh", "true");
        //是否需要实时更新配置
        if(Boolean.valueOf(refresh)){
            server.setConfigs(configs);
        }
    }
}
