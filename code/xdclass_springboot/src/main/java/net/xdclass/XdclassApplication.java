package net.xdclass;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.util.unit.DataSize;
import javax.servlet.MultipartConfigElement;

@SpringBootApplication  //一个顶下面三个
//@SpringBootConfiguration
//@EnableAutoConfiguration
//@ComponentScan
@ServletComponentScan   //支持扫描servlet3.0注解，如filter等使用
public class XdclassApplication
        //打包war，在容器中运行，要使用 SpringBootServletInitializer 入口
        extends SpringBootServletInitializer {


    /**
     * 以Web方式启动springBoot项目
     * @param builder
     * @return
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(XdclassApplication.class);
    }

    /**
     * 入口方法，jar方式运行
     * @param args
     */
    public static void main(String[] args) {
        //
        SpringApplication.run(XdclassApplication.class, args);
    }


    /**
     * 在启动类里，添加一个控制文件上传的配置
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement(){
        MultipartConfigFactory factory = new MultipartConfigFactory();

        //设置单个上传文件大小
        factory.setMaxFileSize(DataSize.parse("10240KB"));

        //设置总共允许上传文件大小
        factory.setMaxRequestSize(DataSize.parse("10240KB"));

        return factory.createMultipartConfig();
    }


}
