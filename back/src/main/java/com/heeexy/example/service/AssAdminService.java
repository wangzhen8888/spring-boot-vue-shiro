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
     * 活动列表
     *
     * @param jsonObject
     * @return
     */
    JSONObject listActivity(JSONObject jsonObject);
    /**
     * 创建活动
     *
     * @param jsonObject
     * @return
     */
    JSONObject createActivity(JSONObject jsonObject);
    /**
     * 更新活动
     *
     * @param jsonObject
     * @return
     */
    JSONObject updateActivity(JSONObject jsonObject);
    /**
     * 审批社员
     *
     * @param jsonObject
     * @return
     */
    JSONObject agreeAssUser(JSONObject jsonObject);



}
