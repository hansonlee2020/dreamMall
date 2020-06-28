package com.hanson.controller;

import com.hanson.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: DreamMall
 * @description: 后台用户登陆业务控制层接口，可以处理后台系统用户的登陆认证和授权业务
 * @param:
 * @author: Hanson
 * @create: 2020-03-19 12:19
 **/
@Controller
public class LoginOrRegisterUserController {
    //手动跳转进去系统首页，需要登陆，没有登陆会被shiro拦截并跳转到登陆页面
    @RequestMapping("/toHome")
    public String toHome(){
        return "home";
    }
    //自动跳转到系统首页
    @GetMapping("/")
    public String autoToHome(){
        return "home";
    }
    //手动跳转到登录页面
    @RequestMapping("/toLogin")
    public String toLogin(){
        return "login";
    }
   //自动跳转本地登录页面,GET请求
    @GetMapping(value="/login")
    public String login(HttpServletRequest request){
        return "login";
    }
    //用户登陆,POST请求
    @PostMapping("/login")
    public String UserLogin(User login, Model model){
        Subject subject = SecurityUtils.getSubject();//获取当前对象
        String name = login.getUserName();
        String pwd = login.getUserPwd();
        UsernamePasswordToken token = new UsernamePasswordToken(login.getUserName(),login.getUserPwd());
        try {
            subject.login(token);
            String userName = login.getUserName();
            model.addAttribute("wellcomeMsg",userName);
            return "home";
        }catch (UnknownAccountException uae){
            model.addAttribute("errorMsg","用户未注册，请先联系管理员完成注册！");
            return "login";
        }catch (IncorrectCredentialsException ice){
            model.addAttribute("errorMsg","账号密码错误");
            return "login";
        }
    }
}
