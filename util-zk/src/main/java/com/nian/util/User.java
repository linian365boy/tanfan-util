package com.nian.util;

import java.io.Serializable;

/**
 * @author tanfan
 * @ClassName: User
 * @Description:
 * @date: 2017/4/6 15:04
 * @since JDK 1.7
 */
public class User implements Serializable {
    private static final long serialVersionUID = -2831684630845295792L;
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
