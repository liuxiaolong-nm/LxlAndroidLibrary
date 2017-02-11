package com.lxl.support;

import com.lxl.support.save.db.SupportDBTableEngine;

/**
 * Created by liuxiaolong on 17/1/18.
 */
public class DbEntityTest extends SupportDBTableEngine {
    public String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
