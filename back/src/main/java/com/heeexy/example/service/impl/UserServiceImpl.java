package com.heeexy.example.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dao.UserDao;
import com.heeexy.example.entity.ReqImportClient;
import com.heeexy.example.service.UserService;
import com.heeexy.example.util.CommonUtil;
import com.heeexy.example.util.constants.ErrorEnum;
import com.xiaoleilu.hutool.log.Log;
import com.xiaoleilu.hutool.log.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.regex.Pattern;

/**
 * @author: hxy
 * @description: 用户/角色/权限
 * @date: 2017/11/2 10:18
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    /**
     * 打印日志
     */
    private static final Log logger = LogFactory.get(UserServiceImpl.class);
    /**
     * 注册url
     */
    private static final String SUFFIX_2003 = ".xls";
    private static final String SUFFIX_2007 = ".xlsx";
    /**
     * 电话的正则
     */
    public static final String PHONE_NUMBER_REG = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[01356789]|18[0-9]|19[89])\\d{8}$";
    /**
     * 密码长度
     */
    public static final int StuNumWardLength = 12;

    /**
     * 用户列表
     *
     * @param jsonObject
     * @return
     */
    @Override
    public JSONObject listUser(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        int count = userDao.countUser(jsonObject);
        List<JSONObject> list = userDao.listUser(jsonObject);
        System.out.println(list);
        return CommonUtil.successPage(jsonObject, list, count);
    }

    /**
     * 解析Excel
     * 通过excel导入用户
     *
     * @param file 文件
     * @return 得到的结果
     */
    @Override
    public JSONObject importUser(MultipartFile file) {
        List<JSONObject> list = new ArrayList<>();

        JSONObject json = new JSONObject();


        if (file == null) {

            CommonUtil.errorJson(ErrorEnum.UPLOAD_ERR_OK);


        }
        //获取名字
        String originalFilename = file.getOriginalFilename();
        Workbook workbook = null;
        try {
            if (originalFilename.endsWith(SUFFIX_2003)) {
                workbook = new HSSFWorkbook(file.getInputStream());
            } else if (originalFilename.endsWith(SUFFIX_2007)) {
                workbook = new XSSFWorkbook(file.getInputStream());
            }
        } catch (Exception e) {
            logger.info(originalFilename);
            return CommonUtil.errorJson(ErrorEnum.UPLOAD_ERR_OK);
        }
        if (workbook == null) {
            logger.info(originalFilename);
            return CommonUtil.errorJson(ErrorEnum.UPLOAD_ERR_OK);
        } else {
            //获取所有的工作表的的数量
            int numOfSheet = workbook.getNumberOfSheets();
            //遍历这个这些表
            for (int i = 0; i < numOfSheet; i++) {

                //获取一个sheet也就是一个工作簿
                Sheet sheet = workbook.getSheetAt(i);
                int lastRowNum = sheet.getLastRowNum();
                //从第一行开始第一行一般是标题
                for (int j = 1; j <= lastRowNum; j++) {
                    json=new JSONObject();
                    Row row = sheet.getRow(j);
                    ReqImportClient reqImportClient = new ReqImportClient();
                    //获取电话单元格
                    if (row.getCell(0) != null) {
                        row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                        String longName = row.getCell(0).getStringCellValue();
                        //todo 正则比对
                        boolean matche = Pattern.matches(PHONE_NUMBER_REG, longName);
                        if (!matche) {
                            break;
                        }
                        json.put("phone",longName);
                    }
                    //学号
                    if (row.getCell(1) != null) {
                        row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                        String stuNum = row.getCell(1).getStringCellValue();
                        if (stuNum.replace("", "").length() != StuNumWardLength) {
                            break;
                        }
                        json.put("stuNum",stuNum);
                    }
                    //姓名
                    if (row.getCell(2) != null) {
                        row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                        String userName = row.getCell(2).getStringCellValue();
                        if (null==userName  || ""==userName) {
                            break;
                        }
                        json.put("nickName",userName);
                    }
                    //遍历班级
                    if (row.getCell(3) != null) {
                        row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
                        String gradeClass = row.getCell(3).getStringCellValue();
                        if (gradeClass == null || ""==gradeClass) {
                            break;
                        }
                        json.put("class",gradeClass);
                    }
                    list.add(json);
                }
            }
        }


        logger.info(list.toString());
        userDao.importUser(list);
        return CommonUtil.successJson();


    }

    /**
     * 添加用户
     *
     * @param jsonObject
     * @return
     */
    @Override
    public JSONObject addUser(JSONObject jsonObject) {
        int exist = userDao.queryExistUsername(jsonObject);
        if (exist > 0) {
            return CommonUtil.errorJson(ErrorEnum.E_10009);
        }
        userDao.addUser(jsonObject);
        return CommonUtil.successJson();
    }

    /**
     * 查询所有的角色
     * 在添加/修改用户的时候要使用此方法
     *
     * @return
     */
    @Override
    public JSONObject getAllRoles() {
        List<JSONObject> roles = userDao.getAllRoles();
        return CommonUtil.successPage(roles);
    }

    /**
     * 修改用户
     *
     * @param jsonObject
     * @return
     */
    @Override
    public JSONObject updateUser(JSONObject jsonObject) {
        userDao.updateUser(jsonObject);
        return CommonUtil.successJson();
    }

    /**
     * 角色列表
     *
     * @return
     */
    @Override
    public JSONObject listRole() {
        List<JSONObject> roles = userDao.listRole();
        return CommonUtil.successPage(roles);
    }

    /**
     * 查询所有权限, 给角色分配权限时调用
     *
     * @return
     */
    @Override
    public JSONObject listAllPermission() {
        List<JSONObject> permissions = userDao.listAllPermission();
        return CommonUtil.successPage(permissions);
    }

    /**
     * 添加角色
     *
     * @param jsonObject
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public JSONObject addRole(JSONObject jsonObject) {
        userDao.insertRole(jsonObject);
        userDao.insertRolePermission(jsonObject.getString("roleId"), (List<Integer>) jsonObject.get("permissions"));
        return CommonUtil.successJson();
    }

    /**
     * 修改角色
     *
     * @param jsonObject
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public JSONObject updateRole(JSONObject jsonObject) {
        String roleId = jsonObject.getString("roleId");
        List<Integer> newPerms = (List<Integer>) jsonObject.get("permissions");
        JSONObject roleInfo = userDao.getRoleAllInfo(jsonObject);
        Set<Integer> oldPerms = (Set<Integer>) roleInfo.get("permissionIds");
        //修改角色名称
        dealRoleName(jsonObject, roleInfo);
        //添加新权限
        saveNewPermission(roleId, newPerms, oldPerms);
        //移除旧的不再拥有的权限
        removeOldPermission(roleId, newPerms, oldPerms);
        return CommonUtil.successJson();
    }

    /**
     * 修改角色名称
     *
     * @param paramJson
     * @param roleInfo
     */
    private void dealRoleName(JSONObject paramJson, JSONObject roleInfo) {
        String roleName = paramJson.getString("roleName");
        if (!roleName.equals(roleInfo.getString("roleName"))) {
            userDao.updateRoleName(paramJson);
        }
    }

    /**
     * 为角色添加新权限
     *
     * @param newPerms
     * @param oldPerms
     */
    private void saveNewPermission(String roleId, Collection<Integer> newPerms, Collection<Integer> oldPerms) {
        List<Integer> waitInsert = new ArrayList<>();
        for (Integer newPerm : newPerms) {
            if (!oldPerms.contains(newPerm)) {
                waitInsert.add(newPerm);
            }
        }
        if (waitInsert.size() > 0) {
            userDao.insertRolePermission(roleId, waitInsert);
        }
    }

    /**
     * 删除角色 旧的 不再拥有的权限
     *
     * @param roleId
     * @param newPerms
     * @param oldPerms
     */
    private void removeOldPermission(String roleId, Collection<Integer> newPerms, Collection<Integer> oldPerms) {
        List<Integer> waitRemove = new ArrayList<>();
        for (Integer oldPerm : oldPerms) {
            if (!newPerms.contains(oldPerm)) {
                waitRemove.add(oldPerm);
            }
        }
        if (waitRemove.size() > 0) {
            userDao.removeOldPermission(roleId, waitRemove);
        }
    }

    /**
     * 删除角色
     *
     * @param jsonObject
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public JSONObject deleteRole(JSONObject jsonObject) {
        JSONObject roleInfo = userDao.getRoleAllInfo(jsonObject);
        List<JSONObject> users = (List<JSONObject>) roleInfo.get("users");
        if (users != null && users.size() > 0) {
            return CommonUtil.errorJson(ErrorEnum.E_10008);
        }
        userDao.removeRole(jsonObject);
        userDao.removeRoleAllPermission(jsonObject);
        return CommonUtil.successJson();
    }
}
