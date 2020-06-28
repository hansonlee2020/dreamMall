package com.hanson.config;

import com.alibaba.fastjson.JSONObject;
import com.hanson.dto.Message;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.codehaus.plexus.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: DreamMall
 * @description: 拦截页面所有的ajax请求，主要用于拦截未授权的用户的敏感操作，例如拦截test用户删除数据，修改数据等操作，未授权返回指定json消息
 * @param:
 * @author: Hanson
 * @create: 2020-05-08 21:34
 **/
public class ShiroPermsFilter extends PermissionsAuthorizationFilter {
    /*
     * @description: 回调函数，拦截ajax请求后，进行授权，授权失败调用该函数返回未授权json消息
     * @params: [request, response]
     * @return: boolean
     * @throws: Exception IOException
     * @Date: 2020/5/8
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String requestedWith = httpServletRequest.getHeader("X-Requested-With");
        System.out.println(requestedWith);
        if (StringUtils.isNotEmpty(requestedWith) &&
                StringUtils.equals(requestedWith, "XMLHttpRequest")) {//如果是ajax返回指定格式数据
            System.out.println("拦截了ajax请求");
            Message message = new Message("未授权","您未被授权，不能进行该操作！","error");
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setContentType("application/json");
            httpServletResponse.getWriter().write(JSONObject.toJSONString(message));
            System.out.println("拦截了ajax请求");
        } else {//如果是普通请求进行重定向
            httpServletResponse.sendRedirect("/unauthorized.jsp");
            System.out.println("普通请求");
        }
        return false;
    }
}
