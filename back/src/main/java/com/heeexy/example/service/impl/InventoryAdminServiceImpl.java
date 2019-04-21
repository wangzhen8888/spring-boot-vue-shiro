package com.heeexy.example.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dao.InventoryAdminDao;
import com.heeexy.example.service.InventoryAdminService;
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
public class InventoryAdminServiceImpl implements InventoryAdminService {
    static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(InventoryAdminServiceImpl.class);
    @Autowired
    private InventoryAdminDao   inventoryAdminDao;


    /**
     * 新增货物盘点记录
     *
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject addInventoryAdmin(JSONObject jsonObject) {

        inventoryAdminDao.addInventoryAdmin(jsonObject);

        return CommonUtil.successJson();
    }

    /**
     * 货物盘点记录列表
     *
     * @param jsonObject
     * @return
     */
    @Override
    public JSONObject listInventoryAdmin(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        logger.info("盘点记录列表入参为：" + jsonObject.toJSONString());
        int count = inventoryAdminDao.countInventoryAdmin(jsonObject);
        List<JSONObject> list = inventoryAdminDao.listInventoryAdmin(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }

    /**
     * 更新货物盘点记录信息
     *
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject updateInventoryAdmin(JSONObject jsonObject) {
        return CommonUtil.successJson();
    }

    /**
     * 删除货物盘点记录信息
     *
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject deleteInventoryAdmin(JSONObject jsonObject) {
//        goodsAdminDao.deleteOrder(jsonObject);
        return CommonUtil.successJson();
    }
}
