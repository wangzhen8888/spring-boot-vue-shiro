package com.heeexy.example.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @author: hxy
 * @date: 2017/10/24 16:06
 */
public interface GoodsAdminService {
    /**
     * 新增盘点记录
     *
     * @param jsonObject
     * @return
     */
    JSONObject addOrder(JSONObject jsonObject);
    /**
     * 计算一个时间段内该商品的出入库数量
     *
     * @param jsonObject
     * @return
     */
    JSONObject orderMsg(JSONObject jsonObject);

    /**
     * 盘点货物记录列表
     *
     * @param jsonObject
     * @return
     */
    JSONObject listOrder(JSONObject jsonObject);
    /**
     * 盘点货物记录列表 不使用分页 数据全部返回  用于导出数据
     *
     * @param jsonObject
     * @return
     */
    JSONObject listOrders(JSONObject jsonObject);

    /**
     * 更新货物盘点记录
     *
     * @param jsonObject
     * @return
     */
    JSONObject updateOrder(JSONObject jsonObject);
    /**
     * 删除货物盘点记录
     *
     * @param jsonObject
     * @return
     */
    JSONObject deleteOrder(JSONObject jsonObject);
}
