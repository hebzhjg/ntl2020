package net.xdclass.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {


    @GetMapping("v2/test_login")
    public Object testLogin(){
        System.out.println("== 2 LoginController--->testLogin");

        return "login ok";
    }

}
