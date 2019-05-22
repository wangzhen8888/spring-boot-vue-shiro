package com.heeexy.example.service;

import com.alibaba.fastjson.JSONObject;


public interface StuService {
    /**
     * 参加社团
     *
     * @param jsonObject
     * @return
     */
    JSONObject addStuAssociation(JSONObject jsonObject);

    /**
     * 参加社团活动
     *
     * @param jsonObject
     * @return
     */
    JSONObject addAct(JSONObject jsonObject);

    /**
     * 批量新增文章
     *
     * @param jsonObject
     * @return
     */
    JSONObject batchAddArticle(JSONObject jsonObject);

    /**
     * 查询已加入社团列表
     *
     * @param jsonObject
     * @return
     */
    JSONObject listStuAssociation(JSONObject jsonObject);

    /**
     * 查询已加入社团的活动列表
     *
     * @param jsonObject
     * @return
     */
    JSONObject actList(JSONObject jsonObject);

    /**
     * 查询当前社团下已参加活动的人员列表
     *
     * @param jsonObject
     * @return
     */
    JSONObject actHaveList(JSONObject jsonObject);

    /**
     * 查询当前社团下所有的活动记录
     *
     * @param jsonObject
     * @return
     */
    JSONObject actAssoList(JSONObject jsonObject);

    /**
     * 更新文章
     *
     * @param jsonObject
     * @return
     */
    JSONObject updateArticle(JSONObject jsonObject);

    /**
     * 更新得分
     *
     * @param jsonObject
     * @return
     */
    JSONObject updateActInfo(JSONObject jsonObject);

    /**
     * 添加考勤记录
     *
     * @param jsonObject
     * @return
     */
    JSONObject createKaoQin(JSONObject jsonObject);

    /**
     * 查询当前社团下所有成员的考勤记录
     * @param jsonObject
     * @return
     */
    JSONObject selectKaoqinList(JSONObject jsonObject);

    /**
     * 更新考勤记录
     * @param jsonObject
     * @return
     */
    JSONObject updateKaoqinInfo(JSONObject jsonObject);


}
