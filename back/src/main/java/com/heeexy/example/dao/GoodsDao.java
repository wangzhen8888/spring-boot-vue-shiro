package com.heeexy.example.dao;

import com.alibaba.fastjson.JSONObject;;

import java.util.List;

/**
 * @author: 王淦
 * @description: 货物Dao层
 * @date: 2019/4/10 16:06
 */
public interface GoodsDao {
    /**
     * 新增货物
     *
     * @param jsonObject
     * @return
     */
    int addGoods(JSONObject jsonObject);

    /**
     * 统计货物总数
     *
     * @param jsonObject
     * @return
     */
    int countGoods(JSONObject jsonObject);

    /**
     * 货物列表
     *
     * @param jsonObject
     * @return
     */
    List<JSONObject> listGoods(JSONObject jsonObject);

    /**
     * 更新货物
     *
     * @param jsonObject
     * @return
     */
    int updateGoods(JSONObject jsonObject);
    /**
     * 删除货物
     *
     * @param jsonObject
     * @return
     */
    int deleteGoods(JSONObject jsonObject);

    /**
     * 根据id获取货物详情
     * @param jsonObject
     * @return
     */
    JSONObject getById(JSONObject jsonObject);
}
