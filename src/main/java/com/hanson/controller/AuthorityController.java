package com.hanson.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hanson.dto.Message;
import com.hanson.dto.PageBean;
import com.hanson.pojo.Authority;
import com.hanson.service.impl.AuthorityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: DreamMall
 * @description: 权限配置，处理权限业务的控制层接口，后台系统的权限页面中进行的操作递交到本接口
 * 可以处理权限的查看、搜索、新增、更新、删除等业务
 * @param:
 * @author: Hanson
 * @create: 2020-04-19 13:37
 **/
@Controller
@RequestMapping("/authority")
public class AuthorityController {
    private AuthorityServiceImpl authorityService;
    @Autowired
    public void setAuthorityService(AuthorityServiceImpl authorityService) {
        this.authorityService = authorityService;
    }

    //跳转到权限页面
    @RequestMapping("/toList")
    public String toAuthorityList(){
        return "authority_list";
    }
    //权限列表
    @RequestMapping("/list")
    @ResponseBody
    public JSONObject authorityList(@RequestParam("pageSize") Integer pageSize , @RequestParam("currentPage") Integer currentPage){
        PageBean<Authority> pageBean = authorityService.splitAuthority(pageSize, currentPage);
        return (JSONObject) JSON.toJSON(pageBean);
    }
    //新增权限
    @RequestMapping("/increase")
    @ResponseBody
    public JSONObject authorityIncrease(Authority authority){
//        Message message = new Message("test", "test", "success");
        Message message = authorityService.createAuthority(authority);
        return (JSONObject) JSON.toJSON(message);
    }
    //查询某权限信息
    @RequestMapping("/query")
    @ResponseBody
    public JSONObject getAuthority(@RequestParam("authorityId") Integer id){
//        Authority authority = new Authority(1, "perms[d]", "/d", 10, "f");
        Authority authority = authorityService.getAuthorityById(id);
        return (JSONObject) JSON.toJSON(authority);
    }
    //编辑权限
    @RequestMapping("/update")
    @ResponseBody
    public JSONObject authorityUpdate(Authority authority){
//        Message message = new Message("test", "test", "success");
        Message message = authorityService.updateAuthority(authority);
        return (JSONObject) JSON.toJSON(message);
    }
    //删除权限
    @RequestMapping("/delete")
    @ResponseBody
    public JSONObject authorityDelete(@RequestParam("authorityId") Integer id){
        Message message = authorityService.removeAuthority(id);
        return (JSONObject) JSON.toJSON(message);
    }
    //搜索权限
    @RequestMapping("/search")
    @ResponseBody
    public JSONObject authoritySearch(@RequestParam("pageSize") Integer pageSize , @RequestParam("currentPage") Integer currentPage,@RequestParam("searchKey")String key){
        PageBean<Authority> pageBean = authorityService.searchAuthoritySplit(pageSize, currentPage, key);
        return (JSONObject) JSON.toJSON(pageBean);
    }
}
