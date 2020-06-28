package com.hanson.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @program: DreamMall
 * @description: 定义一个用户基本信息表
 * @param:
 * @author: Hanson
 * @create: 2020-03-20 20:34
 **/
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBase {
    private String userId;      //用户id
    private String userName;    //用户名称
    private String sex;         //用户性别
    private Integer age;        //用户年龄
    private String mobile;      //用户手机号
    private String Email;       //用户邮箱
    private String roleId;      //用户角色id
    public UserBase(String userName,String sex,Integer age,String mobile,String Email){
        this("0",userName,sex,age,mobile,Email,"0");
    }
}
