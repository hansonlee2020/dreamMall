package com.hanson.dao;

import com.hanson.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: DreamMall
 * @description: 系统管理员用户接口
 * @param:
 * @author: Hanson
 * @create: 2020-04-23 13:29
 **/
@Repository
public interface SystemUserMapper {
    /*
    * @description: 查询全部系统管理员
    * @params: []
    * @return: java.util.List<com.hanson.pojo.User> 系统管理员list
    * @Date: 2020/4/23
    */
    public List<User> doGetSystemUsers();

    /*
    * @description: 根据系统管理员用户id查询系统管理员信息
    * @params: [uid] 管理员用户id
    * @return: com.hanson.pojo.User 系统管理员信息
    * @Date: 2020/4/23
    */
    public User doGetSystemUserById(@Param("userId") String uid);


    /*
    * @description: 根据系统管理员用户名查询系统管理员信息
    * @params: [name] 管理员用户名
    * @return: com.hanson.pojo.User 系统管理员信息
    * @Date: 2020/4/23
    */
    public User doGetSystemUserByName(@Param("userName") String name);


    /*
    * @description: 新建管理员用户
    * @params: [user] 管理员信息
    * @return: java.lang.Integer 新建成功返回1，否则返回0或抛出异常信息
    * @throws: Exception  SQL执行异常，违反唯一约束异常
    * @Date: 2020/4/23
    */
    public Integer doCreateSystemUser(User user);


    /*
    * @description: 根据管理员用户id更新系统管理员用户信息
    * @params: [user] 管理员用户
    * @return: java.lang.Integer 修改成功返回1，否则返回0或抛出异常信息
    * @throws: Exception  SQL执行异常，违反唯一约束异常
    * @Date: 2020/4/23
    */
    public Integer doUpdateSystemUser(User user);



    /*
    * @description: 根据管理员用户id删除管理员信息
    * @params: [uid] 管理员用户id
    * @return: java.lang.Integer 删除成功返回1，否则返回0或抛出异常信息
    * @throws: Exception  SQL执行异常，外键使用异常
    * @Date: 2020/4/23
    */
    public Integer doDeleteSystemUser(String uid);



    /*
    * @description: 统计管理员账号
    * @params: []
    * @return: java.lang.Integer
    * @Date: 2020/4/23
    */
    public Integer doCounts();


    /*
    * @description: 分页模糊查询管理员信息
    * @params: [startIndex, pageSize] startIndex：开始索引, pageSize：页面大小,key：模糊查询关键字
    * @return: java.util.List<com.hanson.pojo.User>
    * @Date: 2020/4/23
    */
    public List<User> doSystemUserSplit(@Param("startIndex") Integer startIndex,@Param("pageSize") Integer pageSize,@Param("searchKey") String key);


    /*
    * @description: 模糊查询管理员用户
    * @params: [key] 模糊查询的关键字
    * @return: java.util.List<com.hanson.pojo.User> 管理员list列表
    * @Date: 2020/4/23
    */
    public List<User> doSearchMemberByName(@Param("userName") String key);
}
