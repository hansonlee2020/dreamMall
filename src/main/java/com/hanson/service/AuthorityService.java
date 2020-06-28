package com.hanson.service;

import com.hanson.dto.Message;
import com.hanson.dto.PageBean;
import com.hanson.pojo.Authority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @program: DreamMall
 * @description: 权限service层接口
 * @param:
 * @author: Hanson
 * @create: 2020-03-30 16:01
 **/
@Service
public interface AuthorityService {


    /*
    * @description: 获取所有的权限信息
    * @params: []
    * @return: java.util.List<com.hanson.pojo.Authority>    List集合形式返回所有的权限
    * @Date: 2020/3/30
    */
    public List<Authority> getAuthorities();



    /*
     * @description: 根据权限id查找权限信息
     * @params: []
     * @return: com.hanson.pojo.Authority 查询到的权限
     * @Date: 2020/3/30
     */
    public Authority getAuthorityById(Integer authorityId);


    /*
    * @description: 根据权限名查询权限信息
    * @params: [name] 要查询的权限名
    * @return: com.hanson.pojo.Authority 该权限名的权限信息
    * @Date: 2020/4/19
    */
    public Authority getAuthorityByName(String name);


    /*
    * @description: 根据资源字段名查询权限信息
    * @params: [field] 要查询的资源字段
    * @return: com.hanson.pojo.Authority 该资源字段的权限信息
    * @Date: 2020/4/19
    */
    public Authority getAuthorityByField(String field);



    /*
    * @description: 根据权限id批量查询权限信息
    * @params: [authorityIds] 要查询的权限id，保存在set集合里
    * @return: java.util.Set<com.hanson.pojo.Authority> 查询到的所有权限信息，以set集合返回
    * @Date: 2020/3/30
    */
    public Set<Authority> getAuthorityByIds(Set<Integer> authorityIds);



    /*
    * @description:  分页查询所有权限信息
    * @params: [pageSize, currentPage] index:开始索引，currentPage:当前页
    * @return: com.hanson.dto.PageBean<com.hanson.pojo.Authority> 权限信息，,封装为页面对象
    * @Date: 2020/4/19
    */
    public PageBean<Authority> splitAuthority(Integer pageSize, Integer currentPage);


    /*
    * @description: 新增权限信息
    * @params: [authority] 需要新增的权限信息
    * @return: com.hanson.dto.Message 新增成功返回处理成功信息，否则返回异常信息，需要捕获异常
    * @Date: 2020/4/19
    */
    public Message createAuthority(Authority authority);

    /*
    * @description: 修改权限信息
    * @params: [authority] 需要修改的权限
    * @return: com.hanson.dto.Message 修改成功返回处理成功信息，否则返回异常信息，需要捕获异常
    * @Date: 2020/4/19
    */
    public Message updateAuthority(Authority authority);


    /*
    * @description: 根据权限id删除权限
    * @params: [id] 权限id
    * @return: com.hanson.dto.Message 删除成功返回处理成功信息，否则返回异常信息，需要捕获异常
    * @Date: 2020/4/19
    */
    public Message removeAuthority(Integer id);



    /*
    * @description: 分页模糊查询
    * @params: [pageSize, currentPage, searchKey] pageSize：分页大小, currentPage：当前页, searchKey：关键字
    * @return: com.hanson.dto.PageBean<com.hanson.pojo.Authority>
    * @throws: Exception
    * @Date: 2020/5/12
    */
    public PageBean<Authority> searchAuthoritySplit(Integer pageSize,Integer currentPage,String searchKey);
}
