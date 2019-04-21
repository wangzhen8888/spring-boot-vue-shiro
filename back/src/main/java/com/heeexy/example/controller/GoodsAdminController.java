package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONObject;;
import com.heeexy.example.service.GoodsAdminService;
import com.heeexy.example.util.CommonUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: 王淦
 * @description: 货物出入库相关Controller
 * @date: 2019/4/10 16:04
 */
@RestController
@RequestMapping("/order")
public class GoodsAdminController {

    @Autowired
    private GoodsAdminService goodsAdminService;

    /**
     * 查询货物列表
     *
     * @param request
     * @return
     */
    @RequiresPermissions("order:list")
    @GetMapping("/listOrder")
    public JSONObject listOrder(HttpServletRequest request) {
        return goodsAdminService.listOrder(CommonUtil.request2Json(request));
    }

    /**
     * 新增货物
     *
     * @param requestJson
     * @return
     */
    @RequiresPermissions("order:add")
    @PostMapping("/addOrder")
    public JSONObject addOrder(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "goods_name,goods_num,goods_price");
        return goodsAdminService.addOrder(requestJson);
    }

    /**
     * 修改货物
     *
     * @param requestJson
     * @return
     */
    @RequiresPermissions("order:update")
    @PostMapping("/updateOrder")
    public JSONObject updateOrder(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "id");
        return goodsAdminService.updateOrder(requestJson);
    }
    /**
     * 删除货物
     *
     * @param requestJson
     * @return
     */
    @RequiresPermissions("order:update")
    @PostMapping("/deleteOrder")
    public JSONObject deleteOrder(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "id");
        return goodsAdminService.deleteOrder(requestJson);
    }

}
