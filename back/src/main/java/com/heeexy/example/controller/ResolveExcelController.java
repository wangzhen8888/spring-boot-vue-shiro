package com.heeexy.example.controller;

import com.heeexy.example.response.ApiResponse;
import com.heeexy.example.response.BusinessException;
import com.heeexy.example.service.ResolveExcelService;
import com.xiaoleilu.hutool.log.Log;
import com.xiaoleilu.hutool.log.LogFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * <p>
 * 类说明
 * </p>
 *
 * @author Alemand
 * @since 2018/3/19
 */
@RestController
@RequestMapping("/resolve")
public class ResolveExcelController {
    /**
     *打印日志
     */
    private static final Log logger = LogFactory.get(ResolveExcelController.class);

    @Resource(name = "resolveExcelServiceImpl")
    private ResolveExcelService resolveExcelService;

    /**
     * 文件上传
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public ApiResponse uploadExcel(@RequestParam("file") MultipartFile file) throws BusinessException {

        Object result;
        try {
            result = resolveExcelService.resolveExcel(file);
            //如果需要将文件放到服务其中加以下代码
           /* try {
                BufferedOutputStream out = new BufferedOutputStream(
                        new FileOutputStream(new File(file.getOriginalFilename())));
                out.write(file.getBytes());
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        } catch (BusinessException e) {
            e.printStackTrace();
            return ApiResponse.failOf(-1, e.getErrMsg());
        }
        logger.info(ApiResponse.successOf(result).getResultObject().toString());
        return ApiResponse.successOf(result);
    }


}
