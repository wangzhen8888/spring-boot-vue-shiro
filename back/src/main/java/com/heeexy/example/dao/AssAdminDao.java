package com.heeexy.example.dao;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface AssAdminDao {



    /**
     * 查询社员列表
     * @param jsonObject
     * @return
     */
    List<JSONObject> listAssUser(JSONObject jsonObject);

    /**
     * 查询活动列表
     * @param jsonObject
     * @return
     */
    List<JSONObject> listActivity(JSONObject jsonObject);
    /**
     * 获取社团活动数量
     * @param jsonObject
     * @return
     */
    int countActivity(JSONObject jsonObject);
    /**
     * 新增社团活动
     * @param jsonObject
     * @return
     */
    int createActivity(JSONObject jsonObject);
    /**
     * 更新社团活动
     * @param jsonObject
     * @return
     */
    int updateActivity(JSONObject jsonObject);
    /**
     * 获取社团成员数量
     * @param jsonObject
     * @return
     */
    int countAssUser(JSONObject jsonObject);
    /**
     * 审批社员
     * @param jsonObject
     * @return
     */
    int agreeAssUser(JSONObject jsonObject);
    /**
     * 获取社团id
     * @param jsonObject
     * @return
     */
    JSONObject getAdminId(JSONObject jsonObject);

}