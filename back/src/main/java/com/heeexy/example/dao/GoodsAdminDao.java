package com.heeexy.example.dao;

import com.alibaba.fastjson.JSONObject;;

import java.util.List;

/**
 * @author: 王淦
 * @description: 货物Dao层
 * @date: 2019/4/10 16:06
 */
public interface GoodsAdminDao {
    /**
     * 新增文章
     *
     * @param jsonObject
     * @return
     */
    int addOrder(JSONObject jsonObject);

    /**
     * 统计货物总数
     *
     * @param jsonObject
     * @return
     */
    int countOrder(JSONObject jsonObject);

    /**
     * 货物列表
     *
     * @param jsonObject
     * @return
     */
    List<JSONObject> listOrder(JSONObject jsonObject);
    /**
     * 货物列表不是用分页返回全部数据
     *
     * @param jsonObject
     * @return
     */
    List<JSONObject> listOrders(JSONObject jsonObject);

    /**
     * 更新货物
     *
     * @param jsonObject
     * @return
     */
    int updateOrder(JSONObject jsonObject);
    /**
     * 删除货物
     *
     * @param jsonObject
     * @return
     */
    int deleteOrder(JSONObject jsonObject);
}
