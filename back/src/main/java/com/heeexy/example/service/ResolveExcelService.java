package com.heeexy.example.service;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.entity.ReqImportClient;
import com.heeexy.example.response.BusinessException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface ResolveExcelService {


    /**
     * 解析Excel
     *
     * @param file 文件
     * @return 得到的结果
     * @throws BusinessException 业务异常统一处理
     */
    JSONObject resolveExcel(MultipartFile file) throws BusinessException;


}
