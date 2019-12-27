package net.xdclass.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 */
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * 在进入Controller方法之前，触发
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("== 1 LoginInterceptor ---> preHandle");
        //
        return HandlerInterceptor.super.preHandle(request,response,handler);
    }

    /**
     * 在调用Controller方法之后，返回（渲染）视图之前，触发
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("== 3 LoginInterceptor ---> postHandle");
        //
        HandlerInterceptor.super.postHandle(request,response,handler,modelAndView);
    }

    /**
     * 在整个请求刘处理完后，通常用于资源清理
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        System.out.println("== 4 LoginInterceptor ---> afterCompletion");

        //
        HandlerInterceptor.super.afterCompletion(request,response,handler,ex);
    }

}
