package com.heeexy.example.service;

import com.alibaba.fastjson.JSONObject;


public interface StuService {
    /**
     * 新增文章
     *
     * @param jsonObject
     * @return
     */
    JSONObject addStuAssociation(JSONObject jsonObject);
    /**
     * 批量新增文章
     *
     * @param jsonObject
     * @return
     */
    JSONObject batchAddArticle(JSONObject jsonObject);

    /**
     * 文章列表
     *
     * @param jsonObject
     * @return
     */
    JSONObject listStuAssociation(JSONObject jsonObject);

    /**
     * 更新文章
     *
     * @param jsonObject
     * @return
     */
    JSONObject updateArticle(JSONObject jsonObject);
}
