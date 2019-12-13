package net.xdclass.controller;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class OtherController {

    private Map<String,Object> params = new HashMap<>();


    /**
     * 测试 @PostMapping
     * @param id
     * @param pwd
     * @return
     */
    @PostMapping(path = "/v1/login")
    public Object login(String id,String pwd){

        params.clear();
        params.put("id",id);
        params.put("pwd",pwd);
        return params;
    }

    @PutMapping(path = "/v1/put")
    public Object doput(String id){
        params.clear();
        params.put("id",id);
        return params;
    }

    @DeleteMapping(path = "/v1/del")
    public  Object dodel(String id){
        params.clear();
        params.put("id",id);
        return params;
    }

}
