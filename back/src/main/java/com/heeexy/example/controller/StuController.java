package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.service.ArticleService;
import com.heeexy.example.service.StuService;
import com.heeexy.example.util.CommonUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @description: 文章相关Controller
 * @date:
 */
@RestController
@RequestMapping("/stuAssociation")
public class StuController {

    @Autowired
    private StuService stuService;

    /**
     * 查询已加入社团列表
     *
     * @param request
     * @return
     */
//    @RequiresPermissions("stuAssociation:list")
    @PostMapping("/listStuAssociation")
    public JSONObject listArticle(HttpServletRequest request) {
        return stuService.listStuAssociation(CommonUtil.request2Json(request));
    }

    /**
     * 加入社团
     *
     * @param requestJson
     * @return
     */
//    @RequiresPermissions("stuAssociation:add")
    @PostMapping("/addStuAssociation")
    public JSONObject addArticle(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "user_id,association_name,association_id");
        return stuService.addStuAssociation(requestJson);
    }
    /**
     * 参加社团活动
     *
     * @param requestJson
     * @return
     */
//    @RequiresPermissions("stuAssociation:add")
    @PostMapping("/addAct")
    public JSONObject addAct(@RequestBody JSONObject requestJson) {
//        CommonUtil.hasAllRequired(requestJson, "user_id,association_name,association_id");
        return stuService.addAct(requestJson);
    }

    /**
     * 修改文章
     *
     * @param requestJson
     * @return
     */
//    @RequiresPermissions("stuAssociation:update")
    @PostMapping("/updateStuAssociation")
    public JSONObject updateArticle(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "id,content");
        return stuService.updateArticle(requestJson);
    }
    /**
     * 查询所加入的社团的活动列表
     *
     * @param requestJson
     * @return
     */
//    @RequiresPermissions("stuAssociation:update")
    @PostMapping("/actList")
    public JSONObject actList(HttpServletRequest request) {

        return stuService.actList(CommonUtil.request2Json(request));
    }
    /**
     * 查询当前社团下所参加的活动记录
     *
     * @param requestJson
     * @return
     */
//    @RequiresPermissions("stuAssociation:update")
    @PostMapping("/actHaveList")
    public JSONObject actHaveList(HttpServletRequest request) {

        return stuService.actHaveList(CommonUtil.request2Json(request));
    }


}
