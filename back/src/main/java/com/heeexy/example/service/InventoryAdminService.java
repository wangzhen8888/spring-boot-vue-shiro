package com.heeexy.example.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @author: hxy
 * @date: 2017/10/24 16:06
 */
public interface InventoryAdminService {
    /**
     * 新增盘点记录
     *
     * @param jsonObject
     * @return
     */
    JSONObject addInventoryAdmin(JSONObject jsonObject);

    /**
     * 盘点货物记录列表
     *
     * @param jsonObject
     * @return
     */
    JSONObject listInventoryAdmin(JSONObject jsonObject);
    /**
     * 最近一个月资金出入记录
     *
     * @param jsonObject
     * @return
     */
    JSONObject selectOrderList(JSONObject jsonObject);

    /**
     * 更新货物盘点记录
     *
     * @param jsonObject
     * @return
     */
    JSONObject updateInventoryAdmin(JSONObject jsonObject);
    /**
     * 删除货物盘点记录
     *
     * @param jsonObject
     * @return
     */
    JSONObject deleteInventoryAdmin(JSONObject jsonObject);
}
