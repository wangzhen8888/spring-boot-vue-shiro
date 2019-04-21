package com.heeexy.example.service;

import com.alibaba.fastjson.JSONObject;;

/**
 * @author: hxy
 * @date: 2017/10/24 16:06
 */
public interface GoodsService {
    /**
     * 新增货物
     *
     * @param jsonObject
     * @return
     */
    JSONObject addGoods(JSONObject jsonObject);

    /**
     * 货物列表
     *
     * @param jsonObject
     * @return
     */
    JSONObject listGoods(JSONObject jsonObject);

    /**
     * 更新货物
     *
     * @param jsonObject
     * @return
     */
    JSONObject updateGoods(JSONObject jsonObject);
    /**
     * 删除货物
     *
     * @param jsonObject
     * @return
     */
    JSONObject deleteGoods(JSONObject jsonObject);
}
