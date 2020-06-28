package com.hanson.config;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/**
 * @program: DreamMall
 * @description: css文件拦截器
 * @param:
 * @author: Hanson
 * @create: 2020-05-12 17:30
 **/
@Component
public class FilterCss implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    //新java配置
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        //        设置请求编码格式
        req.setCharacterEncoding("utf-8");  //post 改变(请求实体)
        //        设置响应编码格式
        resp.setContentType("text/css;charset=utf-8");//修改响应编码
        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {

    }

}
