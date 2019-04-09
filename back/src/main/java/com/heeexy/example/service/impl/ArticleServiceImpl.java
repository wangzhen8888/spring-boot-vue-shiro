package com.heeexy.example.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dao.ArticleDao;
import com.heeexy.example.service.ArticleService;
import com.heeexy.example.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: hxy
 * @description:
 * @date: 2017/10/24 16:07
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleDao articleDao;

    /**
     * 新增文章
     *
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject addArticle(JSONObject jsonObject) {
        int i=0;
        while (i==100000){
            articleDao.addArticle(jsonObject);
            i++;
        }

        return CommonUtil.successJson();
    }
    /**
     *批量新增文章
     *
     * @param jsonObject
     * @return
     */
   @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject batchAddArticle(JSONObject jsonObject) {
       long start = System.currentTimeMillis();
        int i=0;
        List<JSONObject> list=new ArrayList<>();
        JSONObject json=new JSONObject();
        while (i<=10000){
            json=new JSONObject();
            json.put("content","测试"+String.valueOf(i));
            list.add(json);
            i++;
        }

        articleDao.batchddArticle(list);
       long end = System.currentTimeMillis();
       System.out.println("批量插入10000条数据时间为---------------" + (start - end) +"秒"+ "---------------");
        return CommonUtil.successJson();
    }
    /**
     * 文章列表
     *
     * @param jsonObject
     * @return
     */
    @Override
    public JSONObject listArticle(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        int count = articleDao.countArticle(jsonObject);
        List<JSONObject> list = articleDao.listArticle(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }

    /**
     * 更新文章
     *
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject updateArticle(JSONObject jsonObject) {
        articleDao.updateArticle(jsonObject);
        return CommonUtil.successJson();
    }
}
