package com.heeexy.example.service;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.response.BusinessException;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    /**
     * 用户列表
     *
     * @param jsonObject
     * @return
     */
    JSONObject listUser(JSONObject jsonObject);
    /**
     * 查询可以被选择为社长的列表
     *
     * @param jsonObject
     * @return
     */
    JSONObject assListUser(JSONObject jsonObject);
    /**
     * 解析Excel
     * 通过excel导入用户
     * @param file 文件
     * @return 得到的结果
     *
     */
    JSONObject importUser(MultipartFile file);


    /**
     * 查询所有的角色
     * 在添加/修改用户的时候要使用此方法
     *
     * @return
     */
    JSONObject getAllRoles();

    /**
     * 添加用户
     *
     * @param jsonObject
     * @return
     */
    JSONObject addUser(JSONObject jsonObject);

    /**
     * 修改用户
     *
     * @param jsonObject
     * @return
     */
    JSONObject updateUser(JSONObject jsonObject);

    /**
     * 角色列表
     *
     * @return
     */
    JSONObject listRole();

    /**
     * 查询所有权限, 给角色分配权限时调用
     *
     * @return
     */
    JSONObject listAllPermission();

    /**
     * 添加角色
     *
     * @param jsonObject
     * @return
     */
    JSONObject addRole(JSONObject jsonObject);

    /**
     * 修改角色
     *
     * @param jsonObject
     * @return
     */
    JSONObject updateRole(JSONObject jsonObject);

    /**
     * 删除角色
     *
     * @param jsonObject
     * @return
     */
    JSONObject deleteRole(JSONObject jsonObject);

    JSONObject getStuRole(JSONObject jsonObject);

}
