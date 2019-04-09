package com.heeexy.example.dao;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface AssociationDao {
    /**
     * 通过主键删除
     *
     * @param jsonObject
     * @return
     */
    int deleteByPrimaryKey(JSONObject jsonObject);

    /**
     * 新增社团
     * @param jsonObject
     * @return
     */
    int insert(JSONObject jsonObject);

    /**
     * 查询社团列表
     * @param jsonObject
     * @return
     */
    List<JSONObject> listAssociation(JSONObject jsonObject);
    /**
     * 查询社团详情
     * @param jsonObject
     * @return
     */
    JSONObject getAssociation(JSONObject jsonObject);
    /**
     * 查询社团数量
     * @param jsonObject
     * @return
     */
    int countAssociation(JSONObject jsonObject);
    /**
     * 更新社团信息
     * @param jsonObject
     * @return
     */
    int updateAssociation(JSONObject jsonObject);


    /**
     * 更新社长信息
     * @param jsonObject
     * @return
     */
    int updateAssUser(JSONObject jsonObject);


}