package com.heeexy.example.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.controller.ResolveExcelController;
import com.heeexy.example.dao.AssociationDao;
import com.heeexy.example.dao.StuDao;
import com.heeexy.example.dao.UserDao;
import com.heeexy.example.service.AssociationService;
import com.heeexy.example.service.UserService;
import com.heeexy.example.util.CommonUtil;
import com.xiaoleilu.hutool.log.Log;
import com.xiaoleilu.hutool.log.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Resource
    private UserService userService;
    @Resource
    private StuDao  stuDao;


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
        if (user_id == null || user_id.length() == 0) {
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


        //判断是否是删除
        if ("2".equals(jsonObject.getString("delete_status"))) {
            int num = associationDao.assUserCount(jsonObject);
            if (num > 0) {
                json.put("msg", "当前社团内有成员，无法删除");
                return CommonUtil.successJson(json);
            }

            if ("".equals(jsonObject.getString("user_id"))||jsonObject.getString("user_id")==null){
                //删除社团
                associationDao.updateAssociation(jsonObject);
                json.put("msg", "更新社团信息成功");
                return CommonUtil.successJson(json);

            }

                JSONObject roleJson = new JSONObject();
                roleJson.put("role_name", "学生");
                JSONObject roJson = userService.getStuRole(roleJson);
                if ("1".equals(roJson.getString("code"))) {
                    return CommonUtil.successJson(roJson);
                }


            jsonObject.put("role_id", roleJson.getString("id"));
            jsonObject.put("is_choose", 1);
            jsonObject.put("old_user_id", jsonObject.getString("user_id"));
            associationDao.updateAssUser(jsonObject);
            //删除社团
            associationDao.updateAssociation(jsonObject);
            json.put("msg", "更新社团信息成功");
            return CommonUtil.successJson(json);
        }


        logger.info(jsonObject.getString("old_user_id"));
        //判断是否重新选择了社长

        if (!"".equals(jsonObject.getString("old_user_id"))) {
            //更新原有社长的可被选择状态
            jsonObject.put("is_choose", 1);

            JSONObject roleJson = new JSONObject();
            roleJson.put("role_name", "学生");
            JSONObject roJson = userService.getStuRole(roleJson);
            if ("1".equals(roJson.getString("code"))) {
                return CommonUtil.successJson(roJson);
            }

            jsonObject.put("role_id", roleJson.getString("id"));
            associationDao.updateAssUser(jsonObject);
            jsonObject.put("is_choose", 2);
            JSONObject assJson = new JSONObject();
            assJson.put("role_name", "社长");
            JSONObject adminJson = userService.getStuRole(assJson);
            if ("1".equals(adminJson.getString("code"))) {
                return CommonUtil.successJson(adminJson);
            }

            jsonObject.put("role_id", assJson.getString("id"));
            jsonObject.put("old_user_id", jsonObject.getString("user_id"));
            associationDao.updateAssUser(jsonObject);
            associationDao.updateAssociation(jsonObject);
            json.put("msg", "更新社团信息成功");
            return CommonUtil.successJson(json);
        } else {
            JSONObject assJson = new JSONObject();
            assJson.put("role_name", "社长");
            JSONObject adminJson = userService.getStuRole(assJson);
            if ("1".equals(adminJson.getString("code"))) {
                return CommonUtil.successJson(assJson);
            }

            jsonObject.put("role_id", assJson.getString("id"));


            jsonObject.put("old_user_id", jsonObject.getString("user_id"));
            associationDao.updateAssUser(jsonObject);
            associationDao.updateAssociation(jsonObject);
            json.put("msg", "更新社团信息成功");
            return CommonUtil.successJson(json);
        }


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
        if ("".equals(jsonObject.getString("user_id"))) {
            associationDao.insert(jsonObject);
            return CommonUtil.successJson();
        }

        //执行更新用户是否可被选择为社长的状态
        //查询当前学生角色的role_id
        JSONObject assJson = new JSONObject();
        assJson.put("role_name", "社长");
        JSONObject adminJson = userService.getStuRole(assJson);
        if ("1".equals(adminJson.getString("code"))) {
            return CommonUtil.successJson(assJson);
        }
        associationDao.insert(jsonObject);

        jsonObject.put("role_id", assJson.getString("id"));
        jsonObject.put("is_choose", 2);
        jsonObject.put("old_user_id", jsonObject.getString("user_id"));

        associationDao.updateAssUser(jsonObject);
        return CommonUtil.successJson();
    }

}
