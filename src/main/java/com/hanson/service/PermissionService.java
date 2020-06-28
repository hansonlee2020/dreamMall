package com.hanson.service;

import com.hanson.pojo.Permission;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @program: DreamMall
 * @description: 分配的权限Service层接口
 * @param:
 * @author: Hanson
 * @create: 2020-03-30 16:16
 **/
@Service
public interface PermissionService {
    /*
    * @description: 获取分配的权限信息
    * @params: []
    * @return: java.util.List<com.hanson.pojo.Permission> list集合形式返回所有的分配权限信息
    * @Date: 2020/3/30
    */
    public List<Permission> getPermissions();
    /*
    * @description: 根据用户id查询用户分配到的所有权限id
    * @params: [userId] 要查询的用户id
    * @return: java.util.Set<java.lang.Integer>    查询到的所有权限id，以set集合保存
    * @Date: 2020/3/30
    */
    public Set<Integer> getPermissionByUserId(String userId);
}
