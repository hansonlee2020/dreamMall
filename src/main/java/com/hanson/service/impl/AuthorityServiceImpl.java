package com.hanson.service.impl;

import com.hanson.dao.AuthorityMapper;
import com.hanson.dao.PermissionMapper;
import com.hanson.dto.GoodsTable;
import com.hanson.dto.Message;
import com.hanson.dto.PageBean;
import com.hanson.pojo.Authority;
import com.hanson.pojo.Goods;
import com.hanson.pojo.Permission;
import com.hanson.pojo.ProductCategory;
import com.hanson.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.rmi.MarshalException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @program: DreamMall
 * @description: 权限接口实现类
 * @param:
 * @author: Hanson
 * @create: 2020-03-30 16:03
 **/
@Service
public class AuthorityServiceImpl implements AuthorityService {
    private AuthorityMapper authorityMapper;
    @Autowired
    public void setAuthorityMapper(AuthorityMapper authorityMapper) {
        this.authorityMapper = authorityMapper;
    }

    private PermissionMapper permissionMapper;
    @Autowired
    public void setPermissionMapper(PermissionMapper permissionMapper) {
        this.permissionMapper = permissionMapper;
    }

    @Override
    public List<Authority> getAuthorities() {
        return authorityMapper.doGetAuthorities();
    }

    @Override
    public Authority getAuthorityById(Integer authorityId) {
        return authorityMapper.doGetAuthorityById(authorityId);
    }

    @Override
    public Authority getAuthorityByName(String name) {
        return authorityMapper.doGetAuthorityByName(name);
    }

    @Override
    public Authority getAuthorityByField(String field) {
        return authorityMapper.doGetAuthorityByField(field);
    }

    @Override
    public Set<Authority> getAuthorityByIds(Set<Integer> authorityIds) {
        return authorityMapper.doGetAuthoritiesByIds(authorityIds);
    }

    @Override
    public PageBean<Authority> splitAuthority(Integer pageSize, Integer currentPage) {
        try{
            Integer startIndex = (currentPage - 1) * pageSize;//分页查询开始索引
            Long total =(long) authorityMapper.doCounts();//总数据量
            Integer pages;
            if (total%pageSize != 0){
                pages = (int)(((double)total/pageSize) + 1);//总页数,有余数，+1再取整
            }else {
                pages = (int)(((double)total/pageSize));//总页数,没有余数，直接取整
            }
            List<Authority> authoritiesList = authorityMapper.doSplitAuthority(startIndex, pageSize);//分页查询结果集
            Iterator<Authority> iter = authoritiesList.iterator();
            while(iter.hasNext()){
                Authority next = iter.next();
                if (next.getAuthorityGroupId() == null){
                    next.setAuthorityGroupId(0);
                }
            }
            Integer size = authoritiesList.size();//当前页数据量
            PageBean<Authority> pageBean = new PageBean<>(total,currentPage,pageSize,pages,size,authoritiesList);
            return pageBean;
        }catch (Exception e){
            System.out.println("error:" + e);
            return new PageBean<Authority>((long)0,1,pageSize,0,0,new ArrayList<Authority>());
        }
    }

    @Override
    public Message createAuthority(Authority authority) {
        Message message;
        Authority newAuthority = new Authority(
                authority.getAuthorityId(),
                authority.getAuthorityName().replaceAll(" ", ""),
                authority.getAuthorityField().replaceAll(" ", ""),
                authority.getAuthorityGroupId(),
                authority.getAuthorityDetails().replaceAll(" ", "")
        );
        if (isAuthorityName(newAuthority) && isAuthorityField(newAuthority)){
            Integer maxId = authorityMapper.doCountsMaxId();
            Integer newId = maxId + 1;
                if (newAuthority.getAuthorityGroupId() == 0 && newAuthority.getAuthorityDetails().equals("0")){
                    newAuthority.setAuthorityId(newId);
                    newAuthority.setAuthorityGroupId(null);
                    newAuthority.setAuthorityDetails(null);
                }else if (authority.getAuthorityGroupId() == 0){
                    newAuthority.setAuthorityId(newId);
                    newAuthority.setAuthorityGroupId(null);
                }else if (authority.getAuthorityDetails().equals("0")){
                    newAuthority.setAuthorityId(newId);
                    newAuthority.setAuthorityDetails(null);
                }
            try {
                Integer result = authorityMapper.doCreateAuthority(newAuthority);
                if (result == 1){
                    message = new Message("系统消息","权限添加成功！","success");
                }else {
                    message = new Message("未知错误","权限添加失败，请联系管理员！","error");
                }
                return message;
            }catch (Exception e){
                System.err.println("error：" + e);
                 return new Message("错误","权限添加失败，权限名或权限资源字段名已存在！","error");
            }
        }
        return new Message("警告","添加失败！权限名或权限资源字段名不符合规则！","warnming");
    }

    @Override
    public Message updateAuthority(Authority authority) {
        Message message;
        Authority newAuthority = new Authority(
                authority.getAuthorityId(),
                authority.getAuthorityName().replaceAll(" ", ""),
                authority.getAuthorityField().replaceAll(" ", ""),
                authority.getAuthorityGroupId(),
                authority.getAuthorityDetails().replaceAll(" ", "")
        );
        if (isAuthorityName(newAuthority) && isAuthorityField(newAuthority)){
            if (authority.getAuthorityGroupId() == 0){
                newAuthority.setAuthorityGroupId(null);
            }
            try {
                Integer result = authorityMapper.doUpdateAuthority(newAuthority);
                if (result == 1){
                    message = new Message("系统消息","权限修改成功！","success");
                }else {
                    message = new Message("系统消息","权限id" + newAuthority.getAuthorityId() + "不存在，权限信息未修改！","error");
                }
                return message;
            }catch (Exception e){
                System.err.println("error：" + e);
                return new Message("错误","权限修改失败，权限名或权限资源字段名已存在！","error");
            }
        }
        return new Message("警告","修改失败！权限名或权限资源字段名不符合规则！","warnming");
    }

    @Override
    public Message removeAuthority(Integer id) {
        Message message;
        try {
            Integer result = authorityMapper.doDeleteAuthorityById(id);
            if (result == 1){
                message = new Message("系统消息","删除权限成功！","success");
            }else {
                message = new Message("错误","不存在id：" + id + "的权限，删除权限失败！","error");
            }
            return message;
        }catch (Exception e){//外键在使用异常
            System.err.println("error：" + e);
            Set<String> userIds = permissionMapper.doGetPermissionByAuthorityId(id);//统计使用该权限的用户
            return new Message("警告","删除失败，" + userIds.size() + "个用户分配了该权限，请先撤销用户权限后再删除！","warnming");
        }
    }

    @Override
    public PageBean<Authority> searchAuthoritySplit(Integer pageSize, Integer currentPage, String searchKey) {
        try{
            Integer startIndex = (currentPage - 1) * pageSize;//分页查询开始索引
            Long total =(long) authorityMapper.doSearchAuthority(searchKey).size();//总数据量
            Integer pages;
            if (total%pageSize != 0){
                pages = (int)(((double)total/pageSize) + 1);//总页数,有余数，+1再取整
            }else {
                pages = (int)(((double)total/pageSize));//总页数,没有余数，直接取整
            }
            List<Authority> authoritiesList = authorityMapper.doSearchAuthoritySplit(startIndex, pageSize,searchKey);//分页查询结果集
            Iterator<Authority> iter = authoritiesList.iterator();
            while(iter.hasNext()){
                Authority next = iter.next();
                if (next.getAuthorityGroupId() == null){
                    next.setAuthorityGroupId(0);
                }
            }
            Integer size = authoritiesList.size();//当前页数据量
            PageBean<Authority> pageBean = new PageBean<>(total,currentPage,pageSize,pages,size,authoritiesList);
            return pageBean;
        }catch (Exception e){
            System.out.println("error:" + e);
            return new PageBean<Authority>((long)0,1,pageSize,0,0,new ArrayList<Authority>());
        }
    }


    /*
    * @description: 验证添加的权限名是否合法
    * @params: [authority] 需要验证的权限
    * @return: boolean 合法返回true，非法返回false
    * @Date: 2020/4/19
    */
    public boolean isAuthorityName(Authority authority){
        if ("authc".equals(authority.getAuthorityName()) | "anon".equals(authority.getAuthorityName())){
            return true;
        }else {
            if (authority.getAuthorityName().startsWith("perms[") && authority.getAuthorityName().endsWith("]")){
                String name = authority.getAuthorityName();
                String str = name.substring(name.indexOf('[', 4) + 1, name.lastIndexOf(']'));
                String regex = "\\w+(\\:\\w+)*?";
                return str.matches(regex);
            }
            return false;
        }
    }
    /*
    * @description: 验证添加的权限资源字段名是否合法
    * @params: [authority] 需要验证的权限
    * @return: boolean 合法返回true，非法返回false
    * @Date: 2020/4/19
    */
    public boolean isAuthorityField(Authority authority){
        String regex = "(/\\w+)+?(/\\*\\*)*";
        return authority.getAuthorityField().matches(regex);
    }
}
