package com.hanson.service.impl;

import com.hanson.dao.LoginOrRegisterUserMapper;
import com.hanson.pojo.User;
import com.hanson.service.LoginOrRegisterUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: DreamMall
 * @description: service层登陆用户的实现类
 * @param:
 * @author: Hanson
 * @create: 2020-03-19 12:16
 **/
@Service
public class LoginOrRegisterUserServiceImpl implements LoginOrRegisterUserService {
    //注入dao层用户登陆和注册接口对象
    private LoginOrRegisterUserMapper loginOrRegisterUserMapper;
    @Autowired
    public void setLoginOrRegisterUserMapper(LoginOrRegisterUserMapper loginOrRegisterUserMapper) {
        this.loginOrRegisterUserMapper = loginOrRegisterUserMapper;
    }
    //用户登陆
    @Override
    public User getLoginUser(User login) {
        return loginOrRegisterUserMapper.doGetLoginUser(login);
    }

    @Override
    public User getLoginUserByName(String userName) {
        return loginOrRegisterUserMapper.doGetLoginUserByName(userName);
    }

    //用户注册
    @Override
    public int registerUser(User registerUser) {
        return loginOrRegisterUserMapper.doRegisterUser(registerUser);
    }
}
