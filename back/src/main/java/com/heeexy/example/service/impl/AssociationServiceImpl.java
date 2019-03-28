package com.heeexy.example.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dao.AssociationDao;
import com.heeexy.example.service.AssociationService;
import com.heeexy.example.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Service
public class AssociationServiceImpl implements AssociationService {

    @Resource
    private AssociationDao associationDao;

    @Transactional(rollbackFor = Exception.class)
    /**
     * 查询社团列表
     * @param jsonObject
     * @return
     */
    @Override
    public JSONObject listAssociation(JSONObject jsonObject) {

        CommonUtil.fillPageParam(jsonObject);
        int count = associationDao.countAssociation(jsonObject);
        List<JSONObject> list = associationDao.listAssociation(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }

    /**
     * 更新社团基本信息
     * @param jsonObject
     * @return
     */
    @Override
    public JSONObject updateAssociation(JSONObject jsonObject) {

        JSONObject json= new JSONObject();
try {

            associationDao.updateAssociation(jsonObject);
            jsonObject.put("is_accept",1);
            jsonObject.put("user_type",1);
            associationDao.updateAssUser(jsonObject);

            json.put("msg","更新社团信息成功");
        } catch (Exception e) {
            json.put("msg","更新社团信息失败，请稍后重试");
            e.printStackTrace();
        }

        return  CommonUtil.successJson(json);


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
