package com.heeexy.example.dao;

import com.alibaba.fastjson.JSONObject;
public interface AssociationDao {
    /**
     * 通过主键删除
     *
     * @param jsonObject
     * @return
     */
    int deleteByPrimaryKey(JSONObject jsonObject);

    int insert(JSONObject jsonObject);

    int insertSelective(JSONObject jsonObject);

    JSONObject selectByPrimaryKey(JSONObject jsonObject);

    int updateByPrimaryKeySelective(JSONObject jsonObject);

    int updateByPrimaryKey(JSONObject jsonObject);
}