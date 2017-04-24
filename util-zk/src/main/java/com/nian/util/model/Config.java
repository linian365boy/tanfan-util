package com.nian.util.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * @author niange
 * @ClassName: Config
 * @desp: 配置实体
 * @date: 2017/4/6 下午10:13
 * @since JDK 1.7
 */
public class Config implements Serializable {

    private static final long serialVersionUID = -6750033623826396948L;
    private int id;
    /**
     * key
     */
    private String key;
    /**
     * 备注
     */
    private String comment;
    /**
     * value
     */
    private String value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
