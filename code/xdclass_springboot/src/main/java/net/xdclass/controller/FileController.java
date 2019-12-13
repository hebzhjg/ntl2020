package net.xdclass.controller;

import net.xdclass.domain.JsonData;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.UUID;

@Controller
public class FileController {

    @RequestMapping("v1/index")
    public Object index(){
        //在pom中加上spring-boot-starter-thymeleaf
        //作用等同mvc的视图，会到templates下，找index
        return "index";
    }


    /**
     *
     */
    private static final String PROJECT_ROOT = "D:\\New2020\\ntl2020\\code\\xdclass_springboot\\src\\main\\resources\\";
    private static final String UPLOAD_PATH = PROJECT_ROOT + "static/images/";

    @RequestMapping("/upload")
    @ResponseBody
    public Object saveUpload(
            //与表单里的name要对应
            @RequestParam("up_name") MultipartFile file, HttpServletRequest request){

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
