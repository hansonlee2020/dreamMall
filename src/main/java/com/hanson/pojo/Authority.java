package com.hanson.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @program: DreamMall
 * @description: 权限类，保存权限的所有信息,实现Comparable接口，用于排序
 * @param:
 * @author: Hanson
 * @create: 2020-03-30 15:30
 **/
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Authority implements Comparable<Authority> {
    private Integer authorityId;    //权限id
    private String authorityName;   //权限名
    private String authorityField;  //权限资源段名
    private Integer authorityGroupId;   //所在权限组id
    private String authorityDetails;    //权限描述

    public Authority(Integer authorityId,String authorityName,String authorityField){
        this(authorityId,authorityName,authorityField,null,null);
    }

    public Authority(Integer authorityId,String authorityName,String authorityField,Integer authorityGroupId){
        this(authorityId,authorityName,authorityField,authorityGroupId,null);
    }

    public Authority(Integer authorityId,String authorityName,String authorityField,String authorityDetails){
        this(authorityId,authorityName,authorityField,null,authorityDetails);
    }

    /**用于权限信息排序，排序规则为权限id首位升序
     * @param o 比较的权限对象
     * @return  返回结果为正数表示当前对象排靠后，返回结果为负数，表示比较的权限对象排靠后，
     * 返回结果为0，表示两对象并排
     */
    @Override
    public int compareTo(Authority o) {
        return this.authorityId - o.authorityId ;
    }
}
