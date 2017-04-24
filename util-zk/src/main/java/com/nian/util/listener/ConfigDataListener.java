package com.nian.util.listener;

import org.I0Itec.zkclient.IZkDataListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author tanfan
 * @ClassName: ConfigDataListener
 * @Description:
 * @date: 2017/4/10 17:34
 * @since JDK 1.7
 */
public class ConfigDataListener implements IZkDataListener {

    private static final Logger logger = LoggerFactory.getLogger(ConfigDataListener.class);

    public void handleDataChange(String s, Object o) throws Exception {
        logger.info("handleDataChange s|{}, o|{}", s, o);
    }

    public void handleDataDeleted(String s) throws Exception {
        logger.info("handleDataDeleted s|{}", s);
    }
}
