package com.hanson.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hanson.dao.PermissionMapper;
import com.hanson.dto.Message;
import com.hanson.dto.PageBean;
import com.hanson.dto.SystemUser;
import com.hanson.pojo.Authority;
import com.hanson.pojo.Permission;
import com.hanson.pojo.User;
import com.hanson.service.impl.SystemUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @program: DreamMall
 * @description: 系统管理员管理控制层接口，后台系统的管理员管理操作请求递交到本接口，
 * 可以处理管理员信息查看、新增更改、删除、授权、销权等业务
 * @param:
 * @author: Hanson
 * @create: 2020-04-23 13:21
 **/
@Controller
@RequestMapping("/sys/user")
public class SystemUserController {
    private SystemUserServiceImpl systemUserService;
    @Autowired
    public void setSystemUserService(SystemUserServiceImpl systemUserService) {
        this.systemUserService = systemUserService;
    }
    //跳转到管理员列表
    @RequestMapping("/toList")
    public String toSystemUserList(){
        return "sys_user_permission";
    }
    //管理员用户列表
    @RequestMapping("/list")
    @ResponseBody
    public JSONObject systemUserList(@RequestParam("pageSize") Integer pageSize ,
                                     @RequestParam("currentPage") Integer currentPage ,
                                     @RequestParam("searchKey") String key){
        PageBean<SystemUser> systemUsersSplit = systemUserService.getSystemUsersSplit(pageSize, currentPage,key);
        return (JSONObject) JSON.toJSON(systemUsersSplit);
    }
    //查询管理员用户
    @RequestMapping("/query")
    @ResponseBody
    public JSONObject systemUserInfo(@RequestParam("userId") String id){
        SystemUser systemUser = systemUserService.getSystemUser(id);
        return (JSONObject) JSON.toJSON(systemUser);
    }
    //编辑管理员信息
    @RequestMapping("/update")
    @ResponseBody
    public JSONObject systemUserEdit(SystemUser systemUser){
//        Message message =  new Message("test","222223","success");
        Message message = systemUserService.updateSystemUser(systemUser);
        return (JSONObject) JSON.toJSON(message);
    }
    //删除管理员信息
    @RequestMapping("/delete")
    @ResponseBody
    public JSONObject systemUserDelete(@RequestParam("userId") String id){
        Message message = systemUserService.removeSystemUser(id);
        return (JSONObject) JSON.toJSON(message);
    }
    //新增管理员信息
    @RequestMapping("/increase")
    @ResponseBody
    public JSONObject systemUserIncrease(SystemUser systemUser){
//        Message message =  new Message("test","222223","success");
        Message message = systemUserService.createSystemUser(systemUser);
        return (JSONObject) JSON.toJSON(message);
    }
    //授权管理员用户
    @RequestMapping("/authority")
    @ResponseBody
    public JSONArray getAuthority(){
        List<Authority> authorities = systemUserService.getAuthority();
        JSONArray array = new JSONArray();
        for (Authority authority : authorities) {
            array.add(authority);
        }
        return array;
    }
    //授权管理员用户
    @RequestMapping("/permission")
    @ResponseBody
    public JSONObject systemUserAuthority(@RequestParam("ids") String ids ,@RequestParam("userId") String id){
//        Message message =  new Message("test","222223","success");
        Message message = systemUserService.batchCreatePermissions(ids, id);
        return (JSONObject) JSON.toJSON(message);
    }
    //销权管理员用户
    @RequestMapping("/revoke/perm")
    @ResponseBody
    public JSONObject systemUserRevokeAuthority(@RequestParam("ids") String ids ,@RequestParam("userId") String id){
//        Message message =  new Message("test","222223","success");
        Message message = systemUserService.batchRevokePermission(ids, id);
        return (JSONObject) JSON.toJSON(message);
    }
}
