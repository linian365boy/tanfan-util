package com.nian.util.service;

import com.nian.util.constant.Constants;
import com.nian.util.server.BusinessServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<BusinessServer> getServers() {
        List<BusinessServer> servers =
                zkService.<BusinessServer>getDataByParent(Constants.SERVER_PATH_PRE);
        logger.info("getServers return datas|{}", servers);
        return servers;
    }
}
