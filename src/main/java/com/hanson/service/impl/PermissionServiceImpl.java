package com.hanson.service.impl;

import com.hanson.dao.PermissionMapper;
import com.hanson.pojo.Permission;
import com.hanson.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @program: DreamMall
 * @description: 分配权限的service接口实现类
 * @param:
 * @author: Hanson
 * @create: 2020-03-30 16:18
 **/
@Service
public class PermissionServiceImpl implements PermissionService {
    private PermissionMapper permissionMapper;
    @Autowired
    public void setPermissionMapper(PermissionMapper permissionMapper) {
        this.permissionMapper = permissionMapper;
    }

    @Override
    public List<Permission> getPermissions() {
        return permissionMapper.doGetPermissions();
    }

    @Override
    public Set<Integer> getPermissionByUserId(String userId) {
        return permissionMapper.doGetPermissionByUserId(userId);
    }
}
