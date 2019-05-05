package com.heeexy.example.dao;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @description: 文章Dao层
 * @date:
 */
public interface ArticleDao {
    /**
     * 新增文章
     *
     * @param jsonObject
     * @return
     */
    int addArticle(JSONObject jsonObject);


    /**
     * 批量新增文章
     *
     * @param jsonObject
     * @return
     */
    int batchddArticle(List<JSONObject> list);

    /**
     * 统计文章总数
     *
     * @param jsonObject
     * @return
     */
    int countArticle(JSONObject jsonObject);

    /**
     * 文章列表
     *
     * @param jsonObject
     * @return
     */
    List<JSONObject> listArticle(JSONObject jsonObject);

    /**
     * 更新文章
     *
     * @param jsonObject
     * @return
     */
    int updateArticle(JSONObject jsonObject);
}
