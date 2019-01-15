package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.service.AssociationService;
import com.heeexy.example.util.CommonUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: 徐敏杰
 * @description: 社团相关Controller
 * @date:
 */
@RestController
@RequestMapping("/association")
public class AssociationController {
    @Autowired
    private AssociationService associationService;
/**
 * 新增社团
 *
 * @param requestJson
 * @return
 */
    @RequiresPermissions("shetuan:add")
    @PostMapping("/addAssociation")
    public JSONObject addAssociation(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "name,details");
        return associationService.addAssociation(requestJson);
    }
}
