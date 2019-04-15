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
