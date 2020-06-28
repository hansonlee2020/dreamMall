package com.hanson.config;

import com.hanson.pojo.Authority;
import com.hanson.pojo.User;
import com.hanson.service.PermissionService;
import com.hanson.service.impl.AuthorityServiceImpl;
import com.hanson.service.impl.LoginOrRegisterUserServiceImpl;
import com.hanson.service.impl.PermissionServiceImpl;
import com.hanson.util.HandleStringUtils;
import org.apache.ibatis.io.Resources;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.*;

/**
 * @program: DreamMall
 * @description: 配置shiro的realm，实现登陆业务的认证和授权操作
 * @param:
 * @author: Hanson
 * @create: 2020-03-30 13:13
 **/
public class UserRealm extends AuthorizingRealm {
    //自动装配用户登录service层接口实现类对象
    @Autowired
    private LoginOrRegisterUserServiceImpl loginOrRegisterUserService;
    //自动装配权限service层接口实现类对象
    @Autowired
    private AuthorityServiceImpl authorityService;
    //自动装配分配权限service层接口实现类对象
    @Autowired
    private PermissionServiceImpl permissionService;
    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行授权=>doGetAuthorizationInfo");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();//创建授权对象
        Subject subject = SecurityUtils.getSubject();   //获取当前用户
        User user = (User) subject.getPrincipal();
        //查询当前用户的所有权限信息，如果有数据返回为权限对象，否则集合size为0
        Set<Integer> authorityIds = permissionService.getPermissionByUserId(user.getUserId());
        if(0 != authorityIds.size()){    //权限id集合ids的size不为0
            Set<Authority> authorities = authorityService.getAuthorityByIds(authorityIds);//查询到权限，返回集合，否则集合size为0
            if(0 != authorities.size()){    //权限集合size不为0
                Set<String> permissions = new TreeSet<>();  //保存授权信息
                Iterator iterator = authorities.iterator();
                while (iterator.hasNext()){
                    Authority iter = (Authority) iterator.next();   //从权限对象Authority里取出授权信息并添加到授权信息集合permissions里
                    String permission = HandleStringUtils.subStringBySymbol(iter.getAuthorityName());   //权限字符串处理
                    permissions.add(permission);    //  添加权限到授权集合
                }
                info.addStringPermissions(permissions); //添加授权信息
            }
            return info;    //返回授权信息
        }else {     //查询不到权限id
            return null;    //返回空授权交由shiro去处理
        }
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("执行了认证=>doGetAuthorizationInfo");
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        User loginUser = loginOrRegisterUserService.getLoginUserByName(token.getUsername());
        if(null == loginUser){
            return null;    //抛出异常
        }
        ByteSource credentialsSalt = ByteSource.Util.bytes(loginUser.getEncryptionSalt());//加密盐
        //最后的比对需要交给安全管理器
        //三个参数进行初步的简单认证信息对象的包装
        //shiro加盐加密
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                loginUser, //用户
                loginUser.getUserPwd(), //密码
                credentialsSalt,
                this.getName()  //本对象的realm name
        );

        return authenticationInfo;
    }
}
