package net.xdclass.controller;

import net.xdclass.domain.JsonData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.UUID;

@Controller
@PropertySource({"classpath:application.properties"})
public class FileController {

    @RequestMapping("v1/index")
    public Object index(){
        //在pom中加上spring-boot-starter-thymeleaf
        //作用等同mvc的视图，会到templates下，找index
        return "index";
    }



    //不能硬编码在这里，要使用配置文件（注释，注入）
    //1-现在配置文件中，添加 web.image-path=D:/New2020/userupload
    //2-并添加到 spring.resources.static-locations=...file:${web.image-path} 资料目录
    //3-因目录在springboot的资源目录，url可以直接访问上传的文件 http://localhost:8080/上传新文件名
    //private static final String UPLOAD_PATH = "D:\\New2020\\userupload\\";

    @Value("${user.loadfile.path}")
    private String UPLOAD_PATH;

    @RequestMapping("/upload")
    @ResponseBody
    public Object saveUpload(
            //与表单里的name要对应
            @RequestParam("up_name") MultipartFile file, HttpServletRequest request){

        System.out.println("配置的用户文件上传位置："+UPLOAD_PATH);

        //获取普通http参数
        String name = request.getParameter("name");
        System.out.println("name = "+name);
        //获得原始文件名
        String fileName = file.getOriginalFilename();
        System.out.println("orignal = "+fileName);
        //获取文件扩展名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        System.out.println("suffix = "+suffixName);
        //生产UUID保存文件名
        String saveName = UUID.randomUUID().toString() + suffixName;
        System.out.println("save ="+saveName);

        //
        File destFile = new File(UPLOAD_PATH+saveName);
        try {
            //这个方法spring进行了效率优化，比自己写高效
            file.transferTo(destFile);
            return new JsonData(1,"上传文件成功",destFile.getAbsolutePath());
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return new JsonData(-1,"文件上传失败了");
    }


}
