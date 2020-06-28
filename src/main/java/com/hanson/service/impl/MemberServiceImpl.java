package com.hanson.service.impl;

import com.hanson.dao.MemberMapper;
import com.hanson.dto.MemberTable;
import com.hanson.dto.Message;
import com.hanson.dto.PageBean;
import com.hanson.pojo.Member;
import com.hanson.service.MemberService;
import com.hanson.util.HandleStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @program: DreamMall
 * @description: 用户接口实现类
 * @param:
 * @author: Hanson
 * @create: 2020-04-21 14:01
 **/
@Service
public class MemberServiceImpl implements MemberService {
    private MemberMapper memberMapper;
    @Autowired
    public void setMemberMapper(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    @Override
    public PageBean<MemberTable> splitMember(Integer pageSize, Integer currentPage, String key) {
        String newKey;//保存格式key
        Integer total;
        Integer pages;
        if ("null".equals(key)){
            newKey = null;
        }else {
            newKey = key.replaceAll(" ","");
        }
        try {
            total = memberMapper.doSearchMember(newKey).size();
            Integer startIndex = (currentPage - 1) * pageSize;
            List<Member> members = memberMapper.doSplitMember(startIndex, pageSize, newKey);
            if (total % pageSize != 0){
                pages = (int)(((double)total/pageSize) + 1);//总页数,有余数，+1再取整
            }else {
                pages = (int)(((double)total/pageSize));//总页数,没有余数，直接取整
            }
            List<MemberTable> memberTables = new ArrayList<>();//转换为前端用户表类
            Iterator<Member> iterator = members.iterator();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//格式化日期时间
            while (iterator.hasNext()){
                Member next = iterator.next();
                Date createTime = next.getCreateTime();
                String format = sdf.format(createTime);
                MemberTable memberTable = new MemberTable(
                        next.getMemberId(),
                        next.getMemberName(),
                        next.getSex(),
                        next.getMemberState(),
                        format
                );
                memberTables.add(memberTable);
            }
            return new PageBean<>(
                    (long) total,
                    currentPage,
                    pageSize,
                    pages,
                    members.size(),
                    memberTables
            );
        }catch (Exception e){
            System.err.println("error：" + e);
            return new PageBean<>((long)0,1,10,0,0,new ArrayList<>());
        }
    }

    @Override
    public PageBean<MemberTable> splitMemberTrash(Integer pageSize, Integer currentPage, String key) {
        String newKey;//保存格式key
        Integer total;
        Integer pages;
        if ("null".equals(key)){
            newKey = null;
        }else {
            newKey = key.replaceAll(" ","");
        }
        try {
            total = memberMapper.doSearchMemberTrash(newKey).size();
            Integer startIndex = (currentPage - 1) * pageSize;
            List<Member> members = memberMapper.doSplitMemberTrash(startIndex, pageSize, newKey);
            if (total % pageSize != 0){
                pages = (int)(((double)total/pageSize) + 1);//总页数,有余数，+1再取整
            }else {
                pages = (int)(((double)total/pageSize));//总页数,没有余数，直接取整
            }
            List<MemberTable> memberTables = new ArrayList<>();//转换为前端用户表类
            Iterator<Member> iterator = members.iterator();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//格式化日期时间
            while (iterator.hasNext()){
                Member next = iterator.next();
                Date createTime = next.getCreateTime();
                String format = sdf.format(createTime);
                MemberTable memberTable = new MemberTable(
                        next.getMemberId(),
                        next.getMemberName(),
                        next.getSex(),
                        next.getMemberState(),
                        format
                );
                memberTables.add(memberTable);
            }
            return new PageBean<>(
                    (long) total,
                    currentPage,
                    pageSize,
                    pages,
                    members.size(),
                    memberTables
            );
        }catch (Exception e){
            System.err.println("error：" + e);
            return new PageBean<>((long)0,1,10,0,0,new ArrayList<>());
        }
    }

    @Override
    public Message transferMemberState(String memberId, Integer state) {
        Message message;
        Integer newState;
        if (state == 20){//20形式解析为2-0，即2 to 0,恢复用户操作并初始化为禁用状态
            newState = 0;
        }else {
            newState = state;
        }
        Integer result = memberMapper.doTransferMemberState(memberId, newState);
        if (result == 1){
            if (state == 20){
                message = new Message("系统消息","用户" + memberId + "已恢复！初始化为禁用！","success");
            }else {
                if (state == 1){//启用用户操作
                    message = new Message("系统消息","用户" + memberId + "已启用！","success");
                }else if (state == 0){//禁用用户操作
                    message = new Message("系统消息","用户" + memberId + "已禁用！","success");
                }else{//回收用户操作
                    message = new Message("系统消息","用户" + memberId + "已回收！","success");
                }
            }
        }else {
            message = new Message("系统消息","操作失败，请重试！","error");
        }
        return message;
    }

    @Override
    public Message deleteMember(String memberId) {
        Message message;
        Integer result = memberMapper.doDeleteMember(memberId);
        if (result == 1){
            message = new Message("系统消息","用户" + memberId + "已彻底删除！","success");
        }else {
            message = new Message("系统消息","用户" + memberId + "删除失败！","error");
        }
        return message;
    }

    @Override
    public Message batchDeleteMembers(String ids) {
        Message message;
        Set<String> newIds = HandleStringUtils.formatToSet(ids);//处理字符串格式
        Integer num = memberMapper.doBatchDeleteMembers(newIds);//执行批量删除
        if (num != 0){
            message = new Message("系统消息","已删除 " + num + " 条数据！","success");
            return message;
        }
        message = new Message("系统消息","用户不存在，删除失败！","error");
        return message;
    }

    @Override
    public Message batchRecoveryMembers(String ids) {
        Message message;
        Set<String> newIds = HandleStringUtils.formatToSet(ids);//处理字符串格式
        Integer num = memberMapper.doBatchRecoveryMembers(newIds);//执行批量恢复
        if (num != 0){
            message = new Message("系统消息","已恢复 " + num + " 条数据！","success");
            return message;
        }
        message = new Message("系统消息","用户不存在，恢复失败！","error");
        return message;
    }

    @Override
    public Message batchOnMembers(String ids) {
        Message message;
        Set<String> newIds = HandleStringUtils.formatToSet(ids);//处理字符串格式
        Integer num = memberMapper.doBatchOnMembers(newIds);//执行批量启用
        if (num != 0){
            message = new Message("系统消息","已启用 " + num + " 个用户！","success");
            return message;
        }
        message = new Message("系统消息","用户不存在，启用失败！","error");
        return message;
    }

    @Override
    public Message batchOffMembers(String ids) {
        Message message;
        Set<String> newIds = HandleStringUtils.formatToSet(ids);//处理字符串格式
        Integer num = memberMapper.doBatchOffMembers(newIds);//执行批量禁用
        if (num != 0){
            message = new Message("系统消息","已禁用 " + num + " 个用户！","success");
            return message;
        }
        message = new Message("系统消息","用户不存在，禁用失败！","error");
        return message;
    }

    @Override
    public Message batchRecycleMembers(String ids) {
        Message message;
        Set<String> newIds = HandleStringUtils.formatToSet(ids);//处理字符串格式
        Integer num = memberMapper.doBatchRecycleMembers(newIds);//执行批量恢复
        if (num != 0){
            message = new Message("系统消息","已回收 " + num + " 个用户！","success");
            return message;
        }
        message = new Message("系统消息","用户不存在，回收失败！","error");
        return message;
    }
}
