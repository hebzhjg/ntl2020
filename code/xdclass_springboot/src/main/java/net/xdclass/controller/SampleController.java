package net.xdclass.controller;


import net.xdclass.domain.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class SampleController {

    @GetMapping("/v1/test_json")
    public Object testJson(){

        return new User("zhang san",40,"13712341234","pwd123",new Date());
    }

    @RequestMapping("/tm")
    public Map<String,String> testMap(){
        Map<String,String> map = new HashMap<>();
        map.put("name","hello word");
        //测试触发热部署，手工方法-保存java代码后，不会热部署
        //修改触发文件，启动热部署
        return map;
    }

}
