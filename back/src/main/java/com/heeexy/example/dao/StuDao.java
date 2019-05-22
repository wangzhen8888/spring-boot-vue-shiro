package com.heeexy.example.dao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @description: 文章Dao层
 * @date:
 */
public interface StuDao {
    /**
     * 加入社团
     *
     * @param jsonObject
     * @return
     */
    int addStuAssociation(JSONObject jsonObject);

    /**
     * 加入社团活动
     *
     * @param jsonObject
     * @return
     */
    int addAct(JSONObject jsonObject);

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
     * 统计所加入社团的活动总数
     *
     * @param jsonObject
     * @return
     */
    int countActList(JSONObject jsonObject);

    /**
     * 判断是否参加过该活动
     *
     * @param jsonObject
     * @return
     */
    int countHaveActList(JSONObject jsonObject);


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
     * 所加入的社团的活动记录
     *
     * @param jsonObject
     * @return
     */
    List<JSONObject> actHaveList(JSONObject jsonObject);

    /**
     * 统计所加入社团的活动总数
     *
     * @param jsonObject
     * @return
     */
    int countActHaveList(JSONObject jsonObject);

    /**
     * 更新加入社团的信息
     *
     * @param jsonObject
     * @return
     */
    int updateArticle(JSONObject jsonObject);

    /**
     * 更新加入社团的信息
     *
     * @param jsonObject
     * @return
     */
    List<JSONObject> actList(JSONObject jsonObject);

    /**
     * 获取当前社团下所有的活动记录
     *
     * @param jsonObject
     * @return
     */
    List<JSONObject> actAssoList(JSONObject jsonObject);

    /**
     * 获取当前社团下所有的活动记录数量
     *
     * @param jsonObject
     * @return
     */
    int countActAssoList(JSONObject jsonObject);

    /**
     * 更新得分情况
     *
     * @param jsonObject
     * @return
     */
    int updateActInfo(JSONObject jsonObject);

    /**
     * 添加考勤记录
     *
     * @param jsonObject
     * @return
     */
    int createKaoQin(JSONObject jsonObject);

    /**
     * 获取当前社团下所有成员的考勤记录
     * @param jsonObject
     * @return
     */
    List<JSONObject> selectKaoqinList(JSONObject jsonObject);

    int countKaoqinList(JSONObject jsonObject);

    /**
     * 更新考勤记录
     * @param jsonObject
     * @return
     */
    int updateKaoqinInfo(JSONObject jsonObject);
}
