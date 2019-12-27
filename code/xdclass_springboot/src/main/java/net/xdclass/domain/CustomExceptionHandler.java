package net.xdclass.domain;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//使用 @ControllerAdvice时，要返回json格式，方法上加@ResponseBody
//@RestControllerAdvice
@ControllerAdvice
public class CustomExceptionHandler {

    private static Logger LOG = LoggerFactory.getLogger(CustomExceptionHandler.class);

    /**
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(value = IOException.class)
    public Object handleIOException(IOException ex, HttpServletRequest request){

        //在不用写{}中间的序号
        LOG.error("url:{} error, msg:{}",request.getRequestURL(),ex.getMessage());

        Map<String,Object> map = new HashMap<>();

        map.put("code",1);
        map.put("msg",ex.getMessage());
        map.put("url",request.getRequestURL());
        return map;
    }

    /**
     * 处理自定义异常，有两种方式
     * 1、页面跳转（传统MVC模式）
     * 2、返回json数据，由前端处理（适合大型应用）
     * 坑：特别注意几点
     * 1、在pom文件中，要有thymeleaf模板引擎
     * 2、默认thymeleaf模板目录classpath:/templates/
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(value = CustomException.class)
    public Object handleCustomEx(CustomException ex,HttpServletRequest request){

        //返回视图
        ModelAndView model = new ModelAndView();
        model.setViewName("error/error.html");

        model.addObject("msg",ex.getMsg());
        model.addObject("url",request.getRequestURL());
        model.addObject("code",ex.getCode());
        return model;

        //返回JSON格式数据
//        Map<String,Object> map = new HashMap<>();
//        map.put("code",ex.getCode());
//        map.put("msg",ex.getMsg());
//        map.put("url",request.getRequestURL());
//        return map;
    }


    /**
     *
     * @param ex
     * @param request
     * @return
     */
    public Object handleArithmeticEx(ArithmeticException ex,HttpServletRequest request){
        return "除数为零";
    }


}
