package net.xdclass.controller;

import net.xdclass.domain.CustomException;
import net.xdclass.domain.JsonData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExceptionController {

    @GetMapping("v1/test_exception")
    public Object testException(){

        try {
            int a = 1 / 0;
        }catch (Exception ex){
            throw new CustomException("-1","除数不能为零");
        }

        return new JsonData(1,"OK");
    }

}
