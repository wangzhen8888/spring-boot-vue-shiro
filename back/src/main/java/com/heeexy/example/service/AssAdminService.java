package com.heeexy.example.service;

import com.alibaba.fastjson.JSONObject;

public interface AssAdminService {

    /**
     * 社员列表
     *
     * @param jsonObject
     * @return
     */
    JSONObject listAssUser(JSONObject jsonObject);
    /**
     * 审批社员
     *
     * @param jsonObject
     * @return
     */
    JSONObject agreeAssUser(JSONObject jsonObject);



}
