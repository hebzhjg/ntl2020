package net.xdclass.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 注释的作用，定义过滤器，指定url（正则）规则，定义名称
 之前都是在web.xml中定义,urlPatterns支持正则表达式
 坑：urlPatterns = "/**" 没有报错，filter注册了，但没有拦截到请求
**/
//@WebFilter(urlPatterns = "/v1/*",filterName = "customFilter")
public class CustomFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("== CustomFilter == init 初始化");
    }

    /**
     * 模拟一个用户登录验证，获得用户名
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
         String username = servletRequest.getParameter("username");

         System.out.println("== CustomFilter == doFilter 执行");
         //System.out.println("==== 用户："+username);

         if("hello".equals(username)){
             //如果验证成功，继续执行其他过滤器
             //所有过滤器构成一个filterChain链，特定的顺序执行
             filterChain.doFilter(servletRequest,servletResponse);
         }else{
             //服务器拒绝请求，什么也不返回
             //或这里，重定向到其他url
             //return;
             //测试其他api，先放过
             filterChain.doFilter(servletRequest,servletResponse);
         }
    }

    @Override
    public void destroy() {
        System.out.println("== CustomFilter == destroy 销毁");
    }
    
}
