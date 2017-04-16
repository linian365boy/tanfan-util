package com.nian.util.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

/**
 * @author niange
 * @ClassName: PropertiesUtil
 * @desp:
 * @date: 2017/4/14 下午10:59
 * @since JDK 1.7
 */
public class PropertiesUtil {
    private static final Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);
    private static Properties prop = null;
    private static String fileName = "common";

    static {
        initProperties();
    }

    public static void initProperties(){
        prop = new Properties();
        try {
            prop.load(PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName+".properties"));
            logger.debug("load prop value|{}",prop);
        } catch (IOException e) {
            logger.error("加载{}文件出错:{}",fileName,e);
        }
    }

    public static String getValue(String key, String defaultValue){
        if(prop==null){
            return defaultValue;
        }
        return prop.getProperty(key);
    }

    public static String getValue(String key){
        return getValue(key,null);
    }
}
