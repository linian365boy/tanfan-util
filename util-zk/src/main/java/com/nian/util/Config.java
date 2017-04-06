package com.nian.util;

import java.io.Serializable;

/**
 * @author niange
 * @ClassName: Config
 * @desp: 配置实体
 * @date: 2017/4/6 下午10:13
 * @since JDK 1.7
 */
public class Config implements Serializable {

    private int id;
    private String key;
    private String comment;
    private String value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
