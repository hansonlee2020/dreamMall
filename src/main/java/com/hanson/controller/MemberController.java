package com.hanson.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hanson.dto.MemberTable;
import com.hanson.dto.Message;
import com.hanson.dto.PageBean;
import com.hanson.service.MemberService;
import com.hanson.service.impl.MemberServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: DreamMall
 * @description: 用户信息控制层接口，可以处理用户信息的查看、搜索、新增、更改、删除、回收、
 * 回收站用户恢复、回收站用户查看、回收站用户检索业务
 * @param:
 * @author: Hanson
 * @create: 2020-04-20 18:07
 **/
@Controller
@RequestMapping("/member")
public class MemberController {
    private MemberServiceImpl memberService;
    @Autowired
    public void setMemberService(MemberServiceImpl memberService) {
        this.memberService = memberService;
    }
    //跳转到用户列表
    @RequestMapping("/toList")
    public String memberList(){
        return "member_list";
    }
    //分页模糊查询、检索用户列表
    @RequestMapping("/list")
    @ResponseBody
    public JSONObject memberList(@RequestParam("pageSize") Integer pageSize ,
                                 @RequestParam("currentPage") Integer currentPage,
                                 @RequestParam("searchKey") String key){
        PageBean<MemberTable> memberTable = memberService.splitMember(pageSize, currentPage, key);
        System.out.println(memberTable);
        return (JSONObject) JSON.toJSON(memberTable);
    }
    //启用、禁用、回收、恢复用户
    @RequestMapping("/transfer")
    @ResponseBody
    public JSONObject memberOn(@RequestParam("memberId") String id,@RequestParam("state") Integer state){
        Message message = memberService.transferMemberState(id, state);
        return (JSONObject) JSON.toJSON(message);
    }
    //跳转到用户列表
    @RequestMapping("/toTrash")
    public String memberToTrash(){
        return "member_trash";
    }
    //分页模糊查询、检索回收站用户列表
    @RequestMapping("/trash")
    @ResponseBody
    public JSONObject memberTrashList(@RequestParam("pageSize") Integer pageSize ,
                                 @RequestParam("currentPage") Integer currentPage,
                                 @RequestParam("searchKeyTrash") String key){
        PageBean<MemberTable> memberTable = memberService.splitMemberTrash(pageSize, currentPage, key);
        System.out.println(memberTable);
        return (JSONObject) JSON.toJSON(memberTable);
    }
    //删除用户
    @RequestMapping("/delete")
    @ResponseBody
    public JSONObject memberDelete(@RequestParam("deleteId") String id){
        Message message = memberService.deleteMember(id);
        return (JSONObject) JSON.toJSON(message);
    }
    //批量删除用户
    @RequestMapping("/deletes")
    @ResponseBody
    public JSONObject memberBatchDelete(@RequestParam("ids") String ids){
//        Message message = new Message("test","111111","success");
        Message message = memberService.batchDeleteMembers(ids);
        return (JSONObject) JSON.toJSON(message);
    }
    //批量恢复用户
    @RequestMapping("/recoveries")
    @ResponseBody
    public JSONObject memberBatchRecovery(@RequestParam("ids") String ids){
        Message message = memberService.batchRecoveryMembers(ids);
        return (JSONObject) JSON.toJSON(message);
    }
    //批量启用用户
    @RequestMapping("/ons")
    @ResponseBody
    public JSONObject memberBatchOn(@RequestParam("ids") String ids){
        Message message = memberService.batchOnMembers(ids);
        return (JSONObject) JSON.toJSON(message);
    }
    //批量禁用用户
    @RequestMapping("/offs")
    @ResponseBody
    public JSONObject memberBatchOff(@RequestParam("ids") String ids){
        Message message = memberService.batchOffMembers(ids);
        return (JSONObject) JSON.toJSON(message);
    }
    //批量回收用户
    @RequestMapping("/recycles")
    @ResponseBody
    public JSONObject memberBatchRecycle(@RequestParam("ids") String ids){
        Message message = memberService.batchRecycleMembers(ids);
        return (JSONObject) JSON.toJSON(message);
    }
}
