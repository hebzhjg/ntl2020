package net.xdclass.controller;

import net.xdclass.domain.JsonData;
import net.xdclass.domain.ServerSettings;
import net.xdclass.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class GetController {

    Map<String,Object> params = new HashMap<>();


    /**
     * 测试 restful 接口协议，从路径中获取字段
     * 约定：
     *      使用小写字母+下划线方式，不用驼峰（因为某些语言不支持驼峰，没有大小写）
     *      @RequestMapping 的 path中定义访问的url地址，花括号{}里表示参数变量
     *      在方法中，使用 @PathVariable 描述参数，当于url里的变量名相同时，可以省略
     *      否则必须定义，如 @PathVariable("user_id") 对于 String userId
     * @param city_id
     * @param userId
     * @return
     */
    @RequestMapping(path="/v1/{city_id}/{user_id}",method = RequestMethod.GET)
    public Object finduer(@PathVariable String city_id,
                                      @PathVariable("user_id") String userId){

        params.clear();
        params.put("city_id",city_id);
        params.put("user_id",userId);

        return params;
    }


    /**
     * 测试 @GetMapping
     * @param from
     * @param size
     * @return
     */
    @GetMapping(path = "/v1/page_user1")
    public Object pageuserV1(int from,int size){
        params.clear();
        params.put("form",from);
        params.put("size",size);
        params.put("msg","看到msg说明，不重启调试ok");
        return params;
    }

    @GetMapping(path = "v1/page_user2")
    public Object pageuserV2(
            //这里定义page参数，默认值0，必须，赋值给from
            @RequestParam(defaultValue = "0",name = "page",required = true) int from,int size){

        params.clear();
        params.put("form",from);
        params.put("size",size);
        return params;
    }


    /**
     * bean 对象传参数，请求格式必须为 json
     * @param user
     * @return
     */
    @RequestMapping(path = "/v1/save_user")
    public Object saveUser1(@RequestBody User user){
        params.clear();
        params.put("user",user);
        return params;
    }


    @GetMapping(path = "/v1/get_header")
    public Object getHeader(
            @RequestHeader("token_access") String token,String id){
        params.clear();
        params.put("token",token);
        params.put("id",id);
        return params;
    }


    /**
     * 让spring管理，并注入对象
     */
    @Autowired
    private ServerSettings serverSettings;

    @GetMapping("/v1/test_properties")
    public Object testProperties(){

        return serverSettings;
    }


    /**
     *
     * @return
     */
    @GetMapping("/v1/get_user")
    public Object testUser(){
        return new User("hebzhjg",28,"119+119-1234","123456",new Date());
    }

}
