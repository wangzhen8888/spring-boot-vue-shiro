package com.heeexy.example.service;

import com.alibaba.fastjson.JSONObject;

public interface AssociationService {
    /**
     * 新增社团
     *
     * @param jsonObject
     * @return
     */
    JSONObject addAssociation(JSONObject jsonObject);
    /**
     * 社团列表
     *
     * @param jsonObject
     * @return
     */
    JSONObject listAssociation(JSONObject jsonObject);
}
