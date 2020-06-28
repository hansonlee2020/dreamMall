package com.hanson.service;

import com.hanson.pojo.User;
import org.springframework.stereotype.Service;

/**
 * @program: DreamMall
 * @description: 定义service层的用户登陆和用户注册接口
 * @param:
 * @author: Hanson
 * @create: 2020-03-19 12:06
 **/
@Service
public interface LoginOrRegisterUserService {
    /*
     * @description: 根据需要登陆的用户对象的用户名查找，验证该用户的合法性 ，并获取该用户的信息
     * @params: [login] 需要登陆的用户对象
     * @return: com.hanson.pojo.User 如果用户存在，以对象方式返回这个用户的信息，否则返回null
     * @Date: 2020/3/20
     */
    public User getLoginUser(User login);
    /*
    * @description: 根据用户名查找用户信息
    * @params: [userName] 用户名
    * @return: com.hanson.pojo.User 返回的用户信息
    * @Date: 2020/3/30
    */
    public User getLoginUserByName(String userName);

    /*
     * @description: 注册新用户
     * @params: [registerUser] 需要注册的用户对象
     * @return: java.lang.Integer 注册成功，返回1，注册失败返回0
     * @Date: 2020/3/20
     */
    public int registerUser(User registerUser);
}
