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
     * 打印日志
     */
    private static final Log logger = LogFactory.get(AssociationServiceImpl.class);
    @Resource
    private AssociationDao associationDao;


    /**
     * 查询社团列表
     *
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
     *
     * @param jsonObject
     * @return
     */
    @Override
    public JSONObject getAssociation(JSONObject jsonObject) {

        CommonUtil.fillPageParam(jsonObject);
        JSONObject map = associationDao.getAssociation(jsonObject);
        logger.info("查询社团详情的结果:" + map);
        String user_id = map.getString("user_id");
        if (user_id.isEmpty()) {
            map.put("user_id", "无社长");
            return CommonUtil.successJson(map);
        }
        JSONObject newJson = associationDao.getUserInfo(map);

        map.put("userName", newJson.getString("nickname"));
        map.put("class", newJson.getString("class"));
        logger.info(map.toString());


        return CommonUtil.successJson(map);

    }

    /**
     * 更新社团基本信息
     *
     * @param jsonObject
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public JSONObject updateAssociation(JSONObject jsonObject) {

        JSONObject json = new JSONObject();
        try {


            //首先先更新社团信息中社长id
            associationDao.updateAssociation(jsonObject);
            //判断是否是删除
            if("2".equals(jsonObject.getString("delete_status"))){
               int num= associationDao.assUserCount(jsonObject);
                if (num>0){
                    json.put("msg", "当前社团内有成员，无法删除");
                    return CommonUtil.successJson(json);
                }
                jsonObject.put("is_choose", 1);
                jsonObject.put("old_user_id", jsonObject.getString("user_id"));
                associationDao.updateAssUser(jsonObject);
                json.put("msg", "更新社团信息成功");
                return CommonUtil.successJson(json);
            }
            logger.info(jsonObject.getString("old_user_id"));
            //判断是否重新选择了社长

            if (!"".equals(jsonObject.getString("old_user_id"))) {
                //更新原有社长的可被选择状态
                jsonObject.put("is_choose", 1);
                associationDao.updateAssUser(jsonObject);
                jsonObject.put("is_choose", 2);
                jsonObject.put("old_user_id", jsonObject.getString("user_id"));
                associationDao.updateAssUser(jsonObject);

            }


            json.put("msg", "更新社团信息成功");
        } catch (Exception e) {
            json.put("msg", "更新社团信息失败，请稍后重试");
            e.printStackTrace();
        }

        return CommonUtil.successJson(json);


    }

    /**
     * 新增社团
     *
     * @param jsonObject
     * @return
     */

    @Transactional(rollbackFor = Exception.class)
    @Override
    public JSONObject addAssociation(JSONObject jsonObject) {
        associationDao.insert(jsonObject);
        //执行更新用户是否可被选择为社长的状态
        jsonObject.put("is_choose", 2);
        jsonObject.put("old_user_id", jsonObject.getString("user_id"));
        associationDao.updateAssUser(jsonObject);
        return CommonUtil.successJson();
    }

}
