package com.hanson.service.impl;

import com.hanson.dao.AuthorityMapper;
import com.hanson.dao.PermissionMapper;
import com.hanson.dao.SystemUserMapper;
import com.hanson.dto.Message;
import com.hanson.dto.PageBean;
import com.hanson.dto.SystemUser;
import com.hanson.pojo.Authority;
import com.hanson.pojo.User;
import com.hanson.service.SystemUserService;
import com.hanson.util.HandleStringUtils;
import com.hanson.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @program: DreamMall
 * @description: 管理员接口实现类
 * @param:
 * @author: Hanson
 * @create: 2020-04-23 14:21
 **/
@Service
public class SystemUserServiceImpl implements SystemUserService {
    private SystemUserMapper systemUserMapper;
    @Autowired
    public void setSystemUserMapper(SystemUserMapper systemUserMapper) {
        this.systemUserMapper = systemUserMapper;
    }

    private PermissionMapper permissionMapper;
    @Autowired
    public void setPermissionMapper(PermissionMapper permissionMapper) {
        this.permissionMapper = permissionMapper;
    }

    private AuthorityMapper authorityMapper;
    @Autowired
    public void setAuthorityMapper(AuthorityMapper authorityMapper) {
        this.authorityMapper = authorityMapper;
    }

    @Override
    public SystemUser getSystemUser(String id) {
        User user = systemUserMapper.doGetSystemUserById(id);
        return new SystemUser(user.getUserId(),user.getUserName(),"","",user.getUserState());
    }

    @Override
    public PageBean<SystemUser> getSystemUsersSplit(Integer pageSize, Integer currentPage, String key) {
        String newKey;//保存格式key
        Long total;
        Integer pages;
        if ("null".equals(key)){
            newKey = null;
        }else {
            newKey = key.replaceAll(" ","");
        }
        Integer startIndex = (currentPage - 1) * pageSize;//分页查询开始索引
        total =(long) systemUserMapper.doSearchMemberByName(newKey).size();//总数据量
        if (total % pageSize != 0){
            pages = (int)(((double)total/pageSize) + 1);//总页数,有余数，+1再取整
        }else {
            pages = (int)(((double)total/pageSize));//总页数,没有余数，直接取整
        }
        List<User> users = systemUserMapper.doSystemUserSplit(startIndex,pageSize,newKey);
        Iterator<User> userIterator = users.iterator();
        List<SystemUser> userList = new ArrayList<>();//保存systemUser对象
        while (userIterator.hasNext()){
            StringBuffer stringBuffer = new StringBuffer();//拼接权限字符串
            String permission;//保存所有权限
            User next = userIterator.next();
            String userId = next.getUserId();
            Set<Integer> authIds = permissionMapper.doGetPermissionByUserId(userId);
            if (authIds.size() > 0){
                Set<Authority> authorities = authorityMapper.doGetAuthoritiesByIds(authIds);
                if (authorities.size() > 0){
                    for (Authority authority : authorities) {
                        String authorityName = authority.getAuthorityDetails();
                        stringBuffer.append(authorityName).append("|");
                    }
                    permission = stringBuffer.substring(0,stringBuffer.length() - 1);
                }else {
                    permission = "没有权限";
                }
            }else {
                permission = "没有权限";;
            }
            //转换问SystemUser对象
            SystemUser systemUser = new SystemUser(
                    userId,
                    next.getUserName(),
                    "",
                    permission,
                    next.getUserState()
            );
            userList.add(systemUser);
        }
        return new PageBean<>(total,currentPage,pageSize,pages,users.size(),userList);
    }

    @Override
    public Message createSystemUser(SystemUser systemUser) {
        Message message;
        //转换为user对象
        Integer state;
        String userState = systemUser.getUserState().replaceAll(" ", "");
        //数据验证
        if ("超级管理员".equals(userState)){
            return new Message("错误","超级管理员只允许设置一个！","error");
        }else if ("管理员".equals(userState)){
            state = 4;
        }else if ("游客".equals(userState)){
            state = 3;
        }else if ("测试员".equals(userState)){
            state = 2;
        }else if ("账号已封停".equals(userState)){
            state = 1;
        }else {
            return new Message("错误","类别设置有误！类别有：超级管理员/管理员/测试员/游客","error");
        }
        //自定义加密操作
        Map<String, String> map = MD5Utils.encryptionToMD5(systemUser.getUserPwd().replaceAll(" ", ""));
        String pwd = map.get("userPwd");
        String salt = map.get("encryptionSalt");
        UUID uuid = UUID.randomUUID();      //生成账号ID
        String id = HandleStringUtils.formatUUID(uuid);     //格式账号ID
        User user = new User(
                id,
                systemUser.getUserName().replaceAll(" ",""),
                pwd,
                state,
                salt);
        try {
            Integer result = systemUserMapper.doCreateSystemUser(user);//新增管理员信息
            if (result == 1){
                message = new Message("系统消息","新增管理员成功！","success");
            }else {
                message = new Message("未知错误","系统出现未知错误，请联系系统人员！","error");
            }
            return message;
        }catch (Exception e){
            System.err.println("error：" + e);
            return new Message("错误","新增失败，管理员昵称已存在！","error");
        }
    }

    @Override
    public Message updateSystemUser(SystemUser systemUser) {
        Message message;
        //转换为user对象
        Integer state;
        String userState = systemUser.getUserState().replaceAll(" ","");
        if ("超级管理员".equals(userState)){
            return new Message("错误","超级管理员只允许设置一个！","error");
        }else if ("管理员".equals(userState)){
            state = 4;
        }else if ("测试员".equals(userState)){
            state = 3;
        }else if ("游客".equals(userState)){
            state = 2;
        }else if ("账号已封停".equals(userState)){
            state = 1;
        }else {
            return new Message("错误","类别设置有误！","error");
        }
        Map<String, String> map = MD5Utils.encryptionToMD5(systemUser.getUserPwd().replaceAll(" ", ""));
        String pwd = map.get("userPwd");
        String salt = map.get("encryptionSalt");
        User user = new User(
                systemUser.getUserId(),
                systemUser.getUserName().replaceAll(" ",""),
                pwd,
                state,
                salt);
        try {
            Integer result = systemUserMapper.doUpdateSystemUser(user);
            if (result == 1){
                message = new Message("系统消息","修改管理员成功！","success");
            }else {
                message = new Message("错误","管理员不存在，修改失败！","error");
            }
            return message;
        }catch (Exception e){
            System.err.println("error：" + e);
            return new Message("错误","修改失败，管理员昵称已存在！","error");
        }
    }

    @Override
    public Message removeSystemUser(String id) {
        Message message;
        try {
            Integer result = systemUserMapper.doDeleteSystemUser(id);
            if (result == 1){
                message = new Message("系统消息","删除管理员成功！","success");
            }else {
                message = new Message("错误","管理员不存在，删除失败！","error");
            }
            return message;
        }catch (Exception e){
            System.err.println("error：" + e);
            Set<Integer> permissions = permissionMapper.doGetPermissionByUserId(id);
            return new Message("错误","删除失败，已授权限 " + permissions.size() + " 个，删除授权管理员需要先撤销其授权！","error");
        }
    }

    @Override
    public List<Authority> getAuthority() {
        return authorityMapper.doGetAuthority();
    }

    @Override
    public Message batchCreatePermissions(String ids, String userId) {
        Set<Integer> idsAuthority = HandleStringUtils.formatToSetInteger(ids);//格式化并转化
        Set<Integer> hasPermissions = permissionMapper.doGetPermissionByUserId(userId);//查询已授权的权限id
        Set<Integer> newIds = new HashSet<>();//保存未授权的权限id
        if (idsAuthority.size() > 0){
            for (Integer id : idsAuthority) {
                if (!hasPermissions.contains(id)){
                    newIds.add(id);
                }
            }
            if (newIds.size() > 0){
                Message message;
                Integer result = permissionMapper.doBatchCreatePermissions(newIds, userId);
                if (result > 0){
                    message = new Message(
                            "授权成功",
                            "新增授权 " + newIds.size() + " 个，已授权 " + (idsAuthority.size() - newIds.size()) + " 个，共授权 " + idsAuthority.size() + " 个。" ,
                            "success");
                }else {
                    message = new Message("错误","授权失败！","error");
                }
                return message;
            }else {
                return new Message("授权失败","用户已授权，不可以对同一个用户进行同一权限重复授权！","error");
            }
        }else {
            return new Message("错误","未选择授权！","error");
        }
    }

    @Override
    public Message batchRevokePermission(String ids, String userId) {
        if ("1".equals(userId)){
            return new Message("错误","无法撤销超级管理员的权限！","error");
        }else {
            Set<Integer> idsAuthority = HandleStringUtils.formatToSetInteger(ids);//格式化并转化
            if (idsAuthority.size() > 0){
                Message message;
                Integer result = permissionMapper.doBatchRevokePermissions(idsAuthority, userId);
                if (result > 0){
                    message = new Message(
                            "成功",
                            "撤销授权 " + result + " 个，未找到授权 " + (idsAuthority.size() - result) + " 个，共撤销授权 " + result + " 个。" ,
                            "success");
                }else {
                    message = new Message("错误","撤销授权失败,未找到授权！","error");
                }
                return message;
            }else {
                return new Message("错误","你未选择授权！","error");
            }
        }
    }
}
