package com.heeexy.example.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.controller.ResolveExcelController;
import com.heeexy.example.dao.AssociationDao;
import com.heeexy.example.service.AssociationService;
import com.heeexy.example.util.CommonUtil;
import com.xiaoleilu.hutool.log.Log;
import com.xiaoleilu.hutool.log.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.logging.Logger;


@Service
public class AssociationServiceImpl implements AssociationService {
    /**
     *打印日志
     */
    private static final Log logger = LogFactory.get(AssociationServiceImpl.class);
    @Resource
    private AssociationDao associationDao;


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
     * 查询社团详情
     * @param jsonObject
     * @return
     */
    @Override
    public JSONObject getAssociation(JSONObject jsonObject) {

        CommonUtil.fillPageParam(jsonObject);
        JSONObject map = associationDao.getAssociation(jsonObject);
        logger.info("查询社团详情的结果:"+map);

        return CommonUtil.successJson(map);

    }
    /**
     * 更新社团基本信息
     * @param jsonObject
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public JSONObject addAssociation(JSONObject jsonObject) {
        associationDao.insert(jsonObject);
        return CommonUtil.successJson();
    }

}
