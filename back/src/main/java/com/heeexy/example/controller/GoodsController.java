package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONObject;;
import com.heeexy.example.service.GoodsService;
import com.heeexy.example.util.CommonUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: 王淦
 * @description: 货物相关Controller
 * @date: 2019/4/10 16:04
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    /**
     * 查询货物列表
     *
     * @param request
     * @return
     */
    @RequiresPermissions("goods:list")
    @GetMapping("/listGoods")
    public JSONObject listGoods(HttpServletRequest request) {
        return goodsService.listGoods(CommonUtil.request2Json(request));
    }

    /**
     * 新增货物
     *
     * @param requestJson
     * @return
     */
    @RequiresPermissions("goods:add")
    @PostMapping("/addGoods")
    public JSONObject addGoods(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "goods_name,goods_num,goods_price");
        return goodsService.addGoods(requestJson);
    }

    /**
     * 修改货物
     *
     * @param requestJson
     * @return
     */
    @RequiresPermissions("goods:update")
    @PostMapping("/updateGoods")
    public JSONObject updateGoods(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "id");
        return goodsService.updateGoods(requestJson);
    }
    /**
     * 删除货物
     *
     * @param requestJson
     * @return
     */
    @RequiresPermissions("goods:update")
    @PostMapping("/deleteGoods")
    public JSONObject deleteGoods(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "id");
        return goodsService.deleteGoods(requestJson);
    }

}
