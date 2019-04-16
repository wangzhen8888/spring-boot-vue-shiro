package com.heeexy.example.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dao.AssAdminDao;
import com.heeexy.example.dao.AssociationDao;
import com.heeexy.example.service.AssAdminService;
import com.heeexy.example.service.AssociationService;
import com.heeexy.example.util.CommonUtil;
import com.xiaoleilu.hutool.log.Log;
import com.xiaoleilu.hutool.log.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Service
public class AssAdminServiceImpl implements AssAdminService {
    /**
     * 打印日志
     */
    private static final Log logger = LogFactory.get(AssAdminServiceImpl.class);
    @Resource
    private AssAdminDao assAdminDao;


    /**
     * 查询社团列表
     *
     * @param jsonObject
     * @return
     */
    @Override
    public JSONObject listAssUser(JSONObject jsonObject) {
        logger.info("社团入参" + jsonObject.toString());
        CommonUtil.fillPageParam(jsonObject);
        int count = assAdminDao.countAssUser(jsonObject);
        JSONObject json = assAdminDao.getAdminId(jsonObject);
        jsonObject.put("id",json.getString("id"));
        List<JSONObject> list = assAdminDao.listAssUser(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }
    /**
     * 查询活动列表
     *
     * @param jsonObject
     * @return
     */
    @Override
    public JSONObject listActivity(JSONObject jsonObject) {
        logger.info("查询社团活动入参" + jsonObject.toString());
        CommonUtil.fillPageParam(jsonObject);
        int count = assAdminDao.countActivity(jsonObject);
        //根据社长id获取社团id
        JSONObject json = assAdminDao.getAdminId(jsonObject);
        jsonObject.put("id",json.getString("id"));
        List<JSONObject> list = assAdminDao.listActivity(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }
    /**
     * 创建活动
     *
     * @param jsonObject
     * @return
     */
    @Override
    public JSONObject createActivity(JSONObject jsonObject) {
        logger.info("创建社团活动入参" + jsonObject.toString());

        //根据社长id获取社团id
        JSONObject json = assAdminDao.getAdminId(jsonObject);
        jsonObject.put("association_id",json.getString("id"));
        int num=assAdminDao.createActivity(jsonObject);
        logger.info("创建活动sql执行的返回值为："+String.valueOf(num));
        JSONObject resultJson=new JSONObject();
        if(num>0){

            resultJson.put("msg","创建活动成功");
            return CommonUtil.successJson(resultJson);
        }
        resultJson.put("msg","创建活动失败");
        return CommonUtil.successJson(resultJson);

    }
    /**
     * 更新活动
     *
     * @param jsonObject
     * @return
     */
    @Override
    public JSONObject updateActivity(JSONObject jsonObject) {
        logger.info("更新社团活动入参" + jsonObject.toString());


        int num=assAdminDao.updateActivity(jsonObject);
        logger.info("更新活动sql执行的返回值为："+String.valueOf(num));
        JSONObject resultJson=new JSONObject();
        if(num>0){

            resultJson.put("msg","更新活动成功");
            return CommonUtil.successJson(resultJson);
        }
        resultJson.put("msg","更新活动失败");
        return CommonUtil.successJson(resultJson);

    }
    /**
     * 审批社员
     * @param jsonObject
     * @return
     */
    @Override
    public JSONObject agreeAssUser(JSONObject jsonObject) {
        int count=assAdminDao.agreeAssUser(jsonObject);
        JSONObject json=new JSONObject();
        if (count>0){
            json.put("msg","审批成功");
            return CommonUtil.successJson(json);
        }
        json.put("msg","审批失败");
        return CommonUtil.successJson(json);

    }


}
