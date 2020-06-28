package com.hanson.service;

import com.hanson.dto.Message;
import com.hanson.dto.PageBean;
import com.hanson.dto.SystemUser;
import com.hanson.pojo.Authority;
import com.hanson.pojo.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: DreamMall
 * @description: 管理员用户service接口
 * @param:
 * @author: Hanson
 * @create: 2020-04-23 14:13
 **/
@Service
public interface SystemUserService {

    /*
    * @description: 根据管理员用户id查询用户
    * @params: [id] 管理员用户id
    * @return: com.hanson.pojo.User
    * @Date: 2020/4/23
    */
    public SystemUser getSystemUser(String id);


    /*
    * @description:  分页获取全部管理员信息
    * @params: [pageSize,currentPage] pageSize：分页大小,currentPage：当前页,key：搜索关键字
    * @return: com.hanson.dto.PageBean<com.hanson.dto.SystemUser> 管理员对象封装为pageBean对象返回
    * @Date: 2020/4/23
    */
    public PageBean<SystemUser> getSystemUsersSplit(Integer pageSize, Integer currentPage, String key);

    /*
    * @description: 新建管理员信息
    * @params: [systemUser] 管理员对象
    * @return: com.hanson.dto.Message  返回处理消息 要捕获异常信息
    * @throws: Exception SQL执行异常，违反唯一约束异常
    * @Date: 2020/4/23
    */
    public Message createSystemUser(SystemUser systemUser);



    /*
    * @description: 根据管理员用户id修改管理员信息
    * @params: [systemUser] 管理员对象
    * @return: com.hanson.dto.Message  返回处理消息 要捕获异常信息
    * @throws: Exception SQL执行异常，违反唯一约束异常
    * @Date: 2020/4/23
    */
    public Message updateSystemUser(SystemUser systemUser);


    /*
    * @description: 根据管理员用户id删除管理员
    * @params: [id] 管理员用户id
    * @return: com.hanson.dto.Message  返回处理消息 要捕获异常信息
    * @throws: Exception SQL执行异常，外键使用异常
    * @Date: 2020/4/23
    */
    public Message removeSystemUser(String id);
    
    
    /* 
    * @description: 获取全部权限信息 
    * @params: [] 
    * @return: java.util.List<com.hanson.pojo.Authority> 
    * @throws: Exception
    * @Date: 2020/4/24 
    */ 
    public List<Authority> getAuthority();


    /*
    * @description: 批量授权
    * @params: [ids,userId] ids：权限id集合，字符串，需要处理成set集合,userId：授权的用户id
    * @return: com.hanson.dto.Message 返回处理消息，需要捕捉异常
    * @Date: 2020/4/24
    */
    public Message batchCreatePermissions(String ids,String userId);


    /*
    * @description:  批量撤销用户授权
    * @params: [ids, userId] ids：权限id集合，字符串，需要处理成set集合,userId：撤销授权的用户id
    * @return: com.hanson.dto.Message 返回处理消息，需要捕捉异常
    * @throws: Exception
    * @Date: 2020/4/24
    */
    public Message batchRevokePermission(String ids, String userId);
}
