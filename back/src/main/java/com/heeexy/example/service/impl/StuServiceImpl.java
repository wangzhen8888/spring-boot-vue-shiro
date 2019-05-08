package com.heeexy.example.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dao.ArticleDao;
import com.heeexy.example.dao.StuDao;
import com.heeexy.example.service.ArticleService;
import com.heeexy.example.service.StuService;
import com.heeexy.example.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Service
public class StuServiceImpl implements StuService {

    @Resource
    private StuDao stuDao;

    /**
     * 加入社团
     *
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject addStuAssociation(JSONObject jsonObject) {
        //判断该学生是否是该社团的社长
        int adminNum=stuDao.countIsAdminAssociation(jsonObject);
        JSONObject msg=new JSONObject();
        if(0!=adminNum){
            msg.put("msg","你就是本社团的社长，无需参加");
            return  CommonUtil.successJson(msg);
        }
        //判断该学生是否已经申请加入过该社团
        int stuNum=stuDao.countIsStuAssociation(jsonObject);
        if(0!=stuNum){
            msg.put("msg","你已参加过该社团，无需重复参加");
            return  CommonUtil.successJson(msg);
        }

        msg.put("msg","你已成功发送入团申请，请耐心等待社长审批");
        stuDao.addStuAssociation(jsonObject);
        return CommonUtil.successJson(msg);
    }

    /**
     * 参加社团活动
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject addAct(JSONObject jsonObject) {

        //判断该学生是否参加过该社团
        int num=stuDao.countHaveActList(jsonObject);
        JSONObject msg=new JSONObject();
        if(0!=num){
            msg.put("msg","你已参加过该活动，无需重复参加");
            return CommonUtil.successJson(msg);
        }
        msg.put("msg","参加成功，感谢你的参与");
        stuDao.addAct(jsonObject);
        return CommonUtil.successJson(msg);
    }

    /**
     *批量新增测试
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

       stuDao.batchddArticle(list);
       long end = System.currentTimeMillis();
       System.out.println("批量插入10000条数据时间为---------------" + (start - end) +"秒"+ "---------------");
        return CommonUtil.successJson();
    }
    /**
     * 所加入社团列表
     *
     * @param jsonObject
     * @return
     */
    @Override
    public JSONObject listStuAssociation(JSONObject jsonObject) {

        CommonUtil.fillPageParam(jsonObject);
        int count = stuDao.countStuAssociation(jsonObject);
        List<JSONObject> list = stuDao.listStuAssociation(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }

    /**
     * 所加入社团的活动列表
     *
     * @param jsonObject
     * @return
     */
    @Override
    public JSONObject actList(JSONObject jsonObject) {

        CommonUtil.fillPageParam(jsonObject);
        int count = stuDao.countActList(jsonObject);
        List<JSONObject> list = stuDao.actList(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }
    /**
     * 当前社团下的所参加的列表
     *
     * @param jsonObject
     * @return
     */
    @Override
    public JSONObject actHaveList(JSONObject jsonObject) {

        CommonUtil.fillPageParam(jsonObject);
        int count = stuDao.countActHaveList(jsonObject);
        List<JSONObject> list = stuDao.actHaveList(jsonObject);
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
        stuDao.updateArticle(jsonObject);
        return CommonUtil.successJson();
    }
}
