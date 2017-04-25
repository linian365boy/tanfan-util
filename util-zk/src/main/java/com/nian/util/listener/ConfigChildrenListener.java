package com.nian.util.listener;

import com.google.gson.Gson;
import com.nian.util.model.Config;
import com.nian.util.server.BusinessServer;
import com.nian.util.util.PropertiesUtil;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
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
        //更新服务器的配置
        String refresh = PropertiesUtil.getValue("config.realtime.refresh", "true");
        logger.info("handleChildChange parentPath | {}, children | {}, refresh | {}", parentPath, children, refresh);
        //是否需要实时更新配置
        if (Boolean.valueOf(refresh)) {
            if(!CollectionUtils.isEmpty(children)) {
                List<Config> configs = new ArrayList<>(children.size());
                for (String path : children) {
                    logger.info("handleChildChange parentPath|{}, path|{}", parentPath, path);
                    Config config = zkClient.readData(parentPath + "/" + path);
                    logger.info("handleChildChange read config|{} form zookeeper", config);
                    configs.add(config);
                }
                server.setConfigs(configs);
            }
        }
    }
}
