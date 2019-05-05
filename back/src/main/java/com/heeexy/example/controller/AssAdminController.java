package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.service.AssAdminService;
import com.heeexy.example.util.CommonUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: 徐敏杰
 * @description: 社长相关Controller
 * @date:
 */
@RestController
@RequestMapping("/assAdmin")
public class AssAdminController {
    @Autowired
    private AssAdminService assAdminService;

    /**
     * 查询活动列表
     *
     * @param request
     * @return
     */
//    @RequiresPermissions("assAdmin:list")
    @PostMapping("/listActivity")
    public JSONObject listActivity(HttpServletRequest request) {
        return assAdminService.listActivity(CommonUtil.request2Json(request));
    }

    /**
     * 创建活动
     *
     * @param request
     * @return
     */
//    @RequiresPermissions("assAdmin:add")
    @PostMapping("/createActivity")
    public JSONObject createActivity(HttpServletRequest request) {
        return assAdminService.createActivity(CommonUtil.request2Json(request));
    }
    /**
     * 更新活动
     *
     * @param request
     * @return
     */
//    @RequiresPermissions("assAdmin:admin")
    @PostMapping("/updateActivity")
    public JSONObject updateActivity(HttpServletRequest request) {
        return assAdminService.updateActivity(CommonUtil.request2Json(request));
    }
    /**
     * 查询社员列表
     *
     * @param request
     * @return
     */
//    @RequiresPermissions("assAdmin:list")
    @PostMapping("/listAssUser")
    public JSONObject listAssUser(HttpServletRequest request) {
        return assAdminService.listAssUser(CommonUtil.request2Json(request));
    }

    /**
     * 审批社员
     * @param request
     * @return
     */
//    @RequiresPermissions("assAdmin:agree")
    @PostMapping("/agreeAssUser")
    public JSONObject agreeAssUser(HttpServletRequest request) {
        return assAdminService.agreeAssUser(CommonUtil.request2Json(request));
    }


}
