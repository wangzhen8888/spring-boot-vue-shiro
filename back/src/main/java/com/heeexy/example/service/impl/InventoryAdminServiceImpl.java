package com.heeexy.example.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dao.InventoryAdminDao;
import com.heeexy.example.service.InventoryAdminService;
import com.heeexy.example.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author: 王淦
 * @description:
 * @date:
 */
@Service
public class InventoryAdminServiceImpl implements InventoryAdminService {
    static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(InventoryAdminServiceImpl.class);
    @Autowired
    private InventoryAdminDao inventoryAdminDao;


    /**
     * 新增货物盘点记录
     *
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject addInventoryAdmin(JSONObject jsonObject) {

        inventoryAdminDao.addInventoryAdmin(jsonObject);

        return CommonUtil.successJson();
    }

    /**
     * 货物盘点记录列表
     *
     * @param jsonObject
     * @return
     */
    @Override
    public JSONObject listInventoryAdmin(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        logger.info("盘点记录列表入参为：" + jsonObject.toJSONString());
        int count = inventoryAdminDao.countInventoryAdmin(jsonObject);
        List<JSONObject> list = inventoryAdminDao.listInventoryAdmin(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }

    /**
     * 最近一个月资金出入记录
     *
     * @param jsonObject
     * @return
     */
    @Override
    public JSONObject selectOrderList(JSONObject jsonObject) {
        logger.info("最近一个月资金出入记录：" + jsonObject.toJSONString());
        List<JSONObject> delList = inventoryAdminDao.selectDelOrderList(jsonObject);
        List<JSONObject> addList = inventoryAdminDao.selectAddOrderList(jsonObject);
        //对查出的数据进行处理  用于echarts图标展示
//        时间数组
        //收入记录
        int[] adds = new int[30];
        //支出记录
        int[] dels = new int[30];
        for (int i=0;i<30;i++){
            adds[i]=0;
            dels[i]=0;
        }
        String[] Time = getDate();
        for(JSONObject json:delList){
            for (int j=0;j<30;j++){
                if(Time[j].equals(json.getString("time"))){
                    dels[j]=json.getIntValue("money");
                }

            }

        }
        for(JSONObject json:addList){
            for (int j=0;j<30;j++){
                if(Time[j].equals(json.getString("time"))){
                    adds[j]=json.getIntValue("money");
                }

            }

        }
        JSONObject resultJson=new JSONObject();
        resultJson.put("dateTime",Time);
        resultJson.put("dels",dels);
        resultJson.put("adds",adds);

        logger.info(resultJson.toJSONString());
        return CommonUtil.successJson(resultJson);
    }

    /**
     * 更新货物盘点记录信息
     *
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject updateInventoryAdmin(JSONObject jsonObject) {
        return CommonUtil.successJson();
    }

    /**
     * 删除货物盘点记录信息
     *
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject deleteInventoryAdmin(JSONObject jsonObject) {
//        goodsAdminDao.deleteOrder(jsonObject);
        return CommonUtil.successJson();
    }

    /**
     * 获取截止当前时间之前一个月日期
     *
     * @return
     */
    private String[] getDate() {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        String[] s = new String[30];
        String[] dateTimes = new String[30];
        for (int i = 0; i < 30; i++) {
            s[i] = sf.format(c.getTime());
            c.add(Calendar.DAY_OF_MONTH, -1);
        }
        int i = 29;
        for (String l : s) {

            dateTimes[i] = l;
            i--;
        }
        return dateTimes;
    }

    public static void main(String[] args) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        String d1 = sf.format(c.getTime());
        String[] s = new String[30];
        String[] dateTimes = new String[30];
        for (int i = 0; i < 30; i++) {
            s[i] = sf.format(c.getTime());
            c.add(Calendar.DAY_OF_MONTH, -1);
        }
        int i = 29;
        for (String l : s) {

            dateTimes[i] = l;
            i--;
            System.out.println(dateTimes[i + 1]);


        }
        for (String l : dateTimes) {
            System.out.println(l);
        }
        System.out.println(s[29]);


//        System.out.println("增加一天后日期:" + sf.format(c.getTime()));
//        try {
//            Date date = sf.parse(sf.format(c.getTime()));
//            System.out.println(date);
//        } catch (ParseException px) {
//            px.printStackTrace();
//        }

    }

}
