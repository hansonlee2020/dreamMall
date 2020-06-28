package com.hanson.dao;

import com.hanson.pojo.Permission;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @program: DreamMall
 * @description: 分配的权限的dao接口
 * @param:
 * @author: Hanson
 * @create: 2020-03-30 16:12
 **/
@Repository
public interface PermissionMapper {



    /*
    * @description: 获取分配的权限信息
    * @params: []
    * @return: java.util.List<com.hanson.pojo.Permission> list集合返回分配的权限对象信息
    * @Date: 2020/3/30
    */
    public List<Permission> doGetPermissions();



    /*
    * @description: 根据用户id查询分配权限的权限id
    * @params: [userId] 用户id
    * @return: java.util.Set<java.lang.Integer> 返回权限id的set集合
    * @Date: 2020/3/30
    */
    public Set<Integer> doGetPermissionByUserId(@Param("id")String userId);


    /*
    * @description: 根据权限id查询权限分配的信息
    * @params: [id] 权限id
    * @return: java.util.Set<java.lang.String> 分配了该权限的用户
    * @throws: Exception
    * @Date: 2020/4/20
    */
    public Set<String> doGetPermissionByAuthorityId(@Param("authorityId") Integer id);


    /*
    * @description: 批量授权用户
    * @params: [ids, userId] ids：权限id集合，userId：要授权的管理员用户id
    * @return: java.lang.Integer  返回授权成功的数量，否则抛出异常
    * @throws: Exception SQL执行异常，违反唯一约束
    * @Date: 2020/4/24
    */
    public Integer doBatchCreatePermissions(@Param("ids") Set<Integer> ids ,@Param("userId") String userId);


    /*
     * @description: 批量撤销用户授权
     * @params: [ids, userId] ids：权限id集合，userId：要撤销授权的管理员用户id
     * @return: java.lang.Integer  返回撤销授权成功的数量，否则抛出异常
     * @throws: Exception SQL执行异常，外键使用异常
     * @Date: 2020/4/24
     */
    public Integer doBatchRevokePermissions(@Param("ids") Set<Integer> ids ,@Param("userId") String userId);
}
