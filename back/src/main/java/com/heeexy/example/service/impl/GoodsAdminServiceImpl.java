package com.heeexy.example.service.impl;


import com.alibaba.fastjson.JSONObject;

import com.heeexy.example.dao.GoodsAdminDao;
import com.heeexy.example.dao.GoodsDao;
import com.heeexy.example.service.GoodsAdminService;
import com.heeexy.example.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: 王淦
 * @description:
 * @date:
 */
@Service
public class GoodsAdminServiceImpl implements GoodsAdminService {
    static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(GoodsAdminServiceImpl.class);
    @Autowired
    private GoodsAdminDao goodsAdminDao;
    @Autowired
    private GoodsDao goodsDao;

    /**
     * 新增货物盘点记录
     *
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject addOrder(JSONObject jsonObject) {
        logger.info("新增盘点记录入参为:" + jsonObject.toString());
        String goodsId = jsonObject.getString("goods_id");
        //获取货物管理类型  记录类型  0 ：入库  1：出库  2：报废 3 缺货
        int orderType = jsonObject.getIntValue("order_type");
        //货物货物总数量
        int totalNum = jsonObject.getIntValue("goods_totalNum");
        //获取盘点的货物数量
        int num = jsonObject.getIntValue("goods_num");
        JSONObject json = new JSONObject();
        JSONObject jsonMsg = new JSONObject();
        switch (orderType) {
            //0 ：入库
            case 0:
                json.put("goods_num", totalNum + num);
                json.put("id", jsonObject.getString("goods_id"));
                logger.info("更新货物入参为：" + json.toString());
                goodsDao.updateGoods(json);


                break;
            //1：出库
            case 1:
                if (totalNum < num) {
                    jsonMsg.put("msg", "库存不足，出货失败");
                    return CommonUtil.successJson(jsonMsg);
                }
                json.put("goods_num", totalNum - num);

                json.put("id", jsonObject.getString("goods_id"));
                logger.info("更新货物入参为：" + json.toString());
                goodsDao.updateGoods(json);


                break;
            // 2：报废
            case 2:
                if (totalNum < num) {
                    jsonMsg.put("msg", "报废数量大于系统库存，请重新登记");
                    return CommonUtil.successJson(jsonMsg);
                }
                json.put("goods_num", totalNum - num);
                json.put("id", jsonObject.getString("goods_id"));
                logger.info("更新货物入参为：" + json.toString());
                goodsDao.updateGoods(json);


                break;
            //3 缺货
            case 3:
                if (totalNum >= num) {
                    jsonMsg.put("msg", "系统库存充足，无需登记缺货");
                    return CommonUtil.successJson(jsonMsg);
                }
                json.put("goods_type", 1);
                json.put("id", jsonObject.getString("goods_id"));
                logger.info("更新货物入参为：" + json.toString());
                goodsDao.updateGoods(json);


                break;
        }


//        logger.info("货物id为:" + goodsId);

        jsonObject.put("goods_num", num);
        goodsAdminDao.addOrder(jsonObject);
        return CommonUtil.successJson();
    }

    /**
     * 计算一个时间段内该商品的出入库数量
     *
     * @param jsonObject
     * @return
     */
    @Override
    public JSONObject orderMsg(JSONObject jsonObject) {
        List<JSONObject> list = goodsAdminDao.listOrders(jsonObject);
        int reduce_num = 0;//出库总数量
        int add_num = 0;//入库总数量
        int scrap_num=0;//报废数量
        JSONObject json=new JSONObject();
        for (JSONObject obj : list) {
            //订单为入库订单
            if (0==obj.getIntValue("order_type")){
                add_num=add_num+obj.getIntValue("goods_num");
            }
            //订单为出库订单
            if (1==obj.getIntValue("order_type")){
                reduce_num=reduce_num+obj.getIntValue("goods_num");
            }
            //订单为报废订单
            if (2==obj.getIntValue("order_type")){
                scrap_num=scrap_num+obj.getIntValue("goods_num");
            }



        }
        json.put("reduce_num",reduce_num);
        json.put("add_num",add_num);
        json.put("scrap_num",scrap_num);
        return CommonUtil.successJson(json);

    }

    /**
     * 货物盘点记录列表
     *
     * @param jsonObject
     * @return
     */
    @Override
    public JSONObject listOrder(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        logger.info("列表入参为：" + jsonObject.toJSONString());
        int count = goodsAdminDao.countOrder(jsonObject);
        List<JSONObject> list = goodsAdminDao.listOrder(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }

    /**
     * 货物盘点记录列表f  返回全部数据  用于导出数据
     *
     * @param jsonObject
     * @return
     */
    @Override
    public JSONObject listOrders(JSONObject jsonObject) {

        logger.info("导出数据列表入参为：" + jsonObject.toJSONString());

        List<JSONObject> list = goodsAdminDao.listOrders(jsonObject);
        JSONObject json = new JSONObject();
        json.put("list", list);
        return json;
    }

    /**
     * 更新货物盘点记录信息
     *
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject updateOrder(JSONObject jsonObject) {
        logger.info("更新货物订单记录入参为:" + jsonObject.toJSONString());
        //获取补货数量  将商品库存更新
        int num = jsonObject.getIntValue("goods_num");
        //查询货物现有库存
        JSONObject goodsJson = new JSONObject();
        goodsJson.put("id", jsonObject.getIntValue("goods_id"));
        JSONObject json = goodsDao.getById(goodsJson);
        int totalNum = json.getIntValue("goods_num");
        JSONObject jsonMsg = new JSONObject();
        if (totalNum < num) {


            jsonMsg.put("msg", "库存不足,请保证货物库存充足再进行补货");
            return CommonUtil.successJson(jsonMsg);
        }
        //更新库存
        int lastNum = totalNum - num;
        goodsJson.put("goods_num", lastNum);
        logger.info("更新库存的入参为：" + goodsJson.toJSONString());
        goodsDao.updateGoods(goodsJson);
        jsonMsg.put("msg", "补发货成功，货物库存已经更新");
        goodsAdminDao.updateOrder(jsonObject);
        return CommonUtil.successJson(jsonMsg);
    }

    /**
     * 删除货物盘点记录信息
     *
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject deleteOrder(JSONObject jsonObject) {
        goodsAdminDao.deleteOrder(jsonObject);
        return CommonUtil.successJson();
    }
}
