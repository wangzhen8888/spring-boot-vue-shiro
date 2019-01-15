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
    @Override
    public JSONObject addAssociation(JSONObject jsonObject) {
        associationDao.insert(jsonObject);
        return CommonUtil.successJson();
    }
}
