package com.heeexy.example.dao;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @description: 文章Dao层
 * @date:
 */
public interface StuDao {
    /**
     * 新增文章
     *
     * @param jsonObject
     * @return
     */
    int addStuAssociation(JSONObject jsonObject);


    /**
     * 批量新增
     *
     * @param jsonObject
     * @return
     */
    int batchddArticle(List<JSONObject> list);

    /**
     * 统计所加入社团总数
     *
     * @param jsonObject
     * @return
     */
    int countStuAssociation(JSONObject jsonObject);
    /**
     * 判断是否加入过该社团
     *
     * @param jsonObject
     * @return
     */
    int countIsStuAssociation(JSONObject jsonObject);
    /**
     * 判断是否是该社团的社长
     *
     * @param jsonObject
     * @return
     */
    int countIsAdminAssociation(JSONObject jsonObject);

    /**
     * 所加入的社团列表
     *
     * @param jsonObject
     * @return
     */
    List<JSONObject> listStuAssociation(JSONObject jsonObject);

    /**
     * 更新加入社团的信息
     *
     * @param jsonObject
     * @return
     */
    int updateArticle(JSONObject jsonObject);
}
