package com.heeexy.example.service;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonAlias;

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

    /**
     * 更新社团基本信息
     * @param jsonObject
     * @return
     */
    JSONObject updateAssociation(JSONObject jsonObject);
}
