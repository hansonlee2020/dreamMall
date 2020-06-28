package com.hanson.pojo;

import com.hanson.util.HandleStringUtils;
import com.hanson.util.MD5Utils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @program: DreamMall
 * @description: 定义一个用户类，用于后台系统用户登陆和新增用户操作
 * @param:
 * @author: Hanson
 * @create: 2020-03-19 11:58
 **/
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String userId;              //用户id
    private String userName;            //用户名
    private String userPwd;             //用户密码
    private Integer userState;          //用户状态
    private String encryptionSalt;      //加密盐值
    public User(String userName,String userPwd){
        this("0",userName,userPwd,4,"");
    }
    public User(String userName,String userPwd,String encryptionSalt){
        this("0",userName,userPwd,4,encryptionSalt);
    }
}
