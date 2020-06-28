package com.hanson.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @program: DreamMall
 * @description: 管理员用户类，用于返回管理员信息给数据展示页面
 * @param:
 * @author: Hanson
 * @create: 2020-04-23 15:29
 **/
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemUser {
    private final static String SYS = "超级管理员";  //管理员用户状态为5
    private final static String ADMIN = "管理员";  //管理员用户状态为4
    private final static String TEST = "测试员";   //管理员用户状态为3
    private final static String PLAYER = "游客";  //管理员用户状态为2
    private final static String DEFAULT = "账号已封停";//用户状态为1
    private String userId;                      //管理员用户id
    private String userName;                    //管理员用户名
    private String userPwd;                     //管理员用户密码
    private String userPermission;              //管理员用户拥有的权限
    private String userState;                   //管理员用户状态
    public SystemUser(
            String userId,
            String userName,
            String userPwd,
            String userPermission,
            Integer userState){
        this.userId = userId;
        this.userName = userName;
        this.userPwd = userPwd;
        this.userPermission = userPermission;
        if (userState == 5){
            this.userState = SYS;
        }else if (userState == 4){
            this.userState = ADMIN;
        }else if (userState == 3){
            this.userState = TEST;
        }else if (userState == 2){
            this.userState = PLAYER;
        }else {
            this.userState = DEFAULT;
        }
    }
    public SystemUser(String userId, String userName, String userPwd, String userState){
        this(userId,userName,userPwd,"",userState);
    }
}
