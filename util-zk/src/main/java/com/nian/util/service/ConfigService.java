package com.nian.util.service;

import com.nian.util.model.Config;

import java.util.List;

/**
 * @author tanfan
 * @ClassName: ConfigService
 * @Description: 配置的Service层
 * @date: 2017/4/7 10:30
 * @since JDK 1.7
 */
public interface ConfigService {
    public void saveConfig(Config config);
    public void updateConfig(Config config);
    public void deleteConfig(int id);
    public List<Config> getConfigs();
    public Config getById(int id);
}
