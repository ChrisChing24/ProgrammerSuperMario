package com.white.ghost.programmersupermario.bean;

import com.white.ghost.programmersupermario.base.BaseResponse;

/**
 * Function: 搜索列表数据模型
 * Author Name: Chris
 * Date: 2019/5/16 10:19
 */
public class SearchBean extends BaseResponse {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
