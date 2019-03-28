package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.service.ArticleService;
import com.heeexy.example.util.CommonUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;
import java.util.Scanner;

/**
 * @author: hxy
 * @description: 文章相关Controller
 * @date: 2017/10/24 16:04
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 查询文章列表
     *
     * @param request
     * @return
     */
    @RequiresPermissions("article:list")
    @GetMapping("/listArticle")
    public JSONObject listArticle(HttpServletRequest request) {
        return articleService.listArticle(CommonUtil.request2Json(request));
    }

    /**
     * 新增文章
     *
     * @param requestJson
     * @return
     */
    @RequiresPermissions("article:add")
    @PostMapping("/addArticle")
    public JSONObject addArticle(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "content");
        return articleService.addArticle(requestJson);
    }

    /**
     * 修改文章
     *
     * @param requestJson
     * @return
     */
    @RequiresPermissions("article:update")
    @PostMapping("/updateArticle")
    public JSONObject updateArticle(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "id,content");
        return articleService.updateArticle(requestJson);
    }
/*@Test
    public void test10(){
        Scanner sc=new Scanner(System.in);
        System.out.println("请输入一个奇数：");
        int a=sc.nextInt();
        int num=(a/2)+1;
        int[] b=new int[num];//存放需要打印的数据
        b[0]=a;//第1个值
        for(int i=1;i<num;i++){
            b[i]=(int) (b[i-1]+b[i-1]-Math.pow(2, i));

        }
        for(int i=0;i<num;i++){

            System.out.print(b[i]+" ");//打印
        }
        System.out.println(1111111111);

    }*/
}
