package com.heeexy.example.service.impl;

import com.alibaba.fastjson.JSONObject;;
import com.heeexy.example.dao.GoodsDao;
import com.heeexy.example.service.GoodsService;
import com.heeexy.example.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: 王淦
 * @description:
 * @date:
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsDao goodsDao;


    /**
     * 新增货物
     *
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject addGoods(JSONObject jsonObject) {
        goodsDao.addGoods(jsonObject);
        return CommonUtil.successJson();
    }

    /**
     * 货物列表
     *
     * @param jsonObject
     * @return
     */
    @Override
    public JSONObject listGoods(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        int count = goodsDao.countGoods(jsonObject);
        List<JSONObject> list = goodsDao.listGoods(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }

    /**
     * 更新货物信息
     *
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject updateGoods(JSONObject jsonObject) {
        goodsDao.updateGoods(jsonObject);
        return CommonUtil.successJson();
    }
    /**
     * 删除货物信息
     *
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject deleteGoods(JSONObject jsonObject) {
        goodsDao.deleteGoods(jsonObject);
        return CommonUtil.successJson();
    }
}
