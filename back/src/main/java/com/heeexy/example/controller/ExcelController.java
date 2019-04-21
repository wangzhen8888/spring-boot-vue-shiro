package com.heeexy.example.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.service.GoodsAdminService;
import com.heeexy.example.util.CommonUtil;
import org.apache.poi.hssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Iterator;


@RestController
@RequestMapping(value = "/excel")
public class ExcelController {
    static Logger logger = LoggerFactory.getLogger(ExcelController.class);

    @Autowired
    private GoodsAdminService goodsAdminService;

    /**
     * 创建表头
     *
     * @param workbook
     * @param sheet
     */
    private void createTitle(HSSFWorkbook workbook, HSSFSheet sheet) {
        HSSFRow row = sheet.createRow(0);
        //设置列宽，setColumnWidth的第二个参数要乘以256，这个参数的单位是1/256个字符宽度
        sheet.setColumnWidth(1, 12 * 256);
        sheet.setColumnWidth(3, 17 * 256);

        //设置为居中加粗
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setFont(font);

        HSSFCell cell;
        cell = row.createCell(0);
        cell.setCellValue("ID");
        cell.setCellStyle(style);


        cell = row.createCell(1);
        cell.setCellValue("货物名称");
        cell.setCellStyle(style);

        cell = row.createCell(2);
        cell.setCellValue("货物编号");
        cell.setCellStyle(style);

        cell = row.createCell(3);
        cell.setCellValue("货物数量");
        cell.setCellStyle(style);

        cell = row.createCell(4);
        cell.setCellValue("单位/个人");
        cell.setCellStyle(style);

        cell = row.createCell(5);
        cell.setCellValue("联系方式");
        cell.setCellStyle(style);

        cell = row.createCell(6);
        cell.setCellValue("操作人");
        cell.setCellStyle(style);

        cell = row.createCell(7);
        cell.setCellValue("订单类型");
        cell.setCellStyle(style);

        cell = row.createCell(8);
        cell.setCellValue("创建时间");
        cell.setCellStyle(style);

        cell = row.createCell(9);
        cell.setCellValue("更新时间");
        cell.setCellStyle(style);

        cell = row.createCell(10);
        cell.setCellValue("备注");
        cell.setCellStyle(style);
    }

    //生成user表excel
    @PostMapping(value = "/getUser")
    public String getUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("订单统计表");
        createTitle(workbook, sheet);


        JSONObject listJson = goodsAdminService.listOrders(CommonUtil.request2Json(request));
        logger.info("接收到的数据" + CommonUtil.request2Json(request).toJSONString());
        logger.info("的到查询的数据" + listJson.toJSONString());

        JSONArray jsonArray = listJson.getJSONArray("list");


        //设置日期格式
        HSSFCellStyle style = workbook.createCellStyle();
        style.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));

        //新增数据行，并且设置单元格数据
        int rowNum = 1;
        for (Iterator iterator = jsonArray.iterator(); iterator.hasNext(); ) {
            JSONObject ob = (JSONObject) iterator.next();
            HSSFRow row = sheet.createRow(rowNum);
            row.createCell(0).setCellValue(ob.get("id").toString());
            row.createCell(1).setCellValue(ob.get("goods_name").toString());
            row.createCell(2).setCellValue(ob.get("goods_id").toString());
            row.createCell(3).setCellValue(ob.get("goods_num").toString());
            row.createCell(4).setCellValue(ob.get("company_name").toString());
            row.createCell(5).setCellValue(ob.get("company_phone").toString());
            row.createCell(6).setCellValue(ob.get("user_name").toString());

            switch (ob.get("order_type").toString()) {
                case "0":
                    row.createCell(7).setCellValue("入库");
                    break;
                case "1":
                    row.createCell(7).setCellValue("出库");
                    break;
                case "2":
                    row.createCell(7).setCellValue("报废");
                    break;
                case "3":
                    row.createCell(7).setCellValue("缺货");
                    break;
            }

            row.createCell(8).setCellValue(ob.get("createTime").toString());
            row.createCell(9).setCellValue(ob.get("updateTime").toString());
            row.createCell(10).setCellValue(ob.get("marks").toString());
            rowNum++;


        }


        String fileName = "导出excel例子.xls";

        //生成excel文件
        buildExcelFile(fileName, workbook);

        //浏览器下载excel
        buildExcelDocument(fileName, workbook, response);
        logger.info("执行了导出");
        return "download excel";
    }

    //生成excel文件
    protected void buildExcelFile(String filename, HSSFWorkbook workbook) throws Exception {
        FileOutputStream fos = new FileOutputStream(filename);
        workbook.write(fos);
        fos.flush();
        fos.close();
    }

    //浏览器下载excel
    protected void buildExcelDocument(String filename, HSSFWorkbook workbook, HttpServletResponse response) throws Exception {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "utf-8"));
        OutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }


}