package net.xdclass.listener;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class CustomSessionListenner implements HttpSessionListener,ServletRequestListener {

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        System.out.println("$$$$$ requestDestroyed $$$$$$$");
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        System.out.println("$$$$$ requestInitialized $$$$$$$");
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        //如果代码中，没有使用httpSeesion对象，不会触发
        System.out.println("====== sessionCreated ================");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        //回话超时后，会触发
        System.out.println("====== sessionDestroyed ================");
    }
}
