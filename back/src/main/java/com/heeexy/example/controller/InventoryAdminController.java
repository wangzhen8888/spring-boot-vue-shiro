package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.service.InventoryAdminService;
import com.heeexy.example.util.CommonUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

;

/**
 * @author: 王淦
 * @description: 盘点记录相关Controller
 * @date: 2019/4/10 16:04
 */
@RestController
@RequestMapping("/inventoryAdmin")
public class InventoryAdminController {

    @Autowired
    private InventoryAdminService inventoryAdminService;

    /**
     * 查询货物列表
     *
     * @param request
     * @return
     */
    @RequiresPermissions("inventoryAdmin:list")
    @GetMapping("/listInventoryAdmin")
    public JSONObject listInventoryAdmin(HttpServletRequest request) {
        return inventoryAdminService.listInventoryAdmin(CommonUtil.request2Json(request));
    }

    /**
     * 新增货物
     *
     * @param requestJson
     * @return
     */
    @RequiresPermissions("inventoryAdmin:add")
    @PostMapping("/addInventoryAdmin")
    public JSONObject addInventoryAdmin(@RequestBody JSONObject requestJson) {
//        CommonUtil.hasAllRequired(requestJson, "goods_name,goods_num,goods_price");
        return inventoryAdminService.addInventoryAdmin(requestJson);
    }

    /**
     * 修改盘点记录
     *
     * @param requestJson
     * @return
     */
    @RequiresPermissions("inventoryAdmin:update")
    @PostMapping("/updateInventoryAdmin")
    public JSONObject updateInventoryAdmin(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "id");
        return inventoryAdminService.updateInventoryAdmin(requestJson);
    }

    /**
     * 删除货物
     *
     * @param requestJson
     * @return
     */
    @RequiresPermissions("inventoryAdmin:update")
    @PostMapping("/deleteInventoryAdmin")
    public JSONObject deleteInventoryAdmin(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "id");
        return inventoryAdminService.deleteInventoryAdmin(requestJson);
    }

}
