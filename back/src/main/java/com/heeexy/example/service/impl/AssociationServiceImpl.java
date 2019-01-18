package com.heeexy.example.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dao.AssociationDao;
import com.heeexy.example.service.AssociationService;
import com.heeexy.example.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class AssociationServiceImpl implements AssociationService {

    @Autowired
    private AssociationDao associationDao;
    @Transactional(rollbackFor = Exception.class)
    /**
     * 查询社团列表
     * @param jsonObject
     * @return
     */
    @Override
    public JSONObject listAssociation(JSONObject jsonObject) {
        return null;
    }

    /**
     * 新增社团
     * @param jsonObject
     * @return
     */
    @Override
    public JSONObject addAssociation(JSONObject jsonObject) {
        associationDao.insert(jsonObject);
        return CommonUtil.successJson();
    }

}
