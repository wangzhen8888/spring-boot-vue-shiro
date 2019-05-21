package com.heeexy.example.dao;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

;

/**
 * @author: 王淦
 * @description: 盘点记录Dao层
 * @date: 2019/4/10 16:06
 */
public interface InventoryAdminDao {
    /**
     * 新增文章
     *
     * @param jsonObject
     * @return
     */
    int addInventoryAdmin(JSONObject jsonObject);

    /**
     * 统计货物总数
     *
     * @param jsonObject
     * @return
     */
    int countInventoryAdmin(JSONObject jsonObject);

    /**
     * 货物列表
     *
     * @param jsonObject
     * @return
     */
    List<JSONObject> listInventoryAdmin(JSONObject jsonObject);

    /**
     * 最近一个月资金支出记录
     * @param jsonObject
     * @return
     */
    List<JSONObject> selectDelOrderList(JSONObject jsonObject);
    /**
     * 最近一个月资金收入记录
     * @param jsonObject
     * @return
     */
    List<JSONObject> selectAddOrderList(JSONObject jsonObject);


    /**
     * 更新货物
     *
     * @param jsonObject
     * @return
     */
    int updateInventoryAdmin(JSONObject jsonObject);
    /**
     * 删除货物
     *
     * @param jsonObject
     * @return
     */
    int deleteInventoryAdmin(JSONObject jsonObject);
}
