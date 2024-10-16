package com.robot.cose.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;





@Controller
public class MemberController {
    
    @RequestMapping("login")
    public String login(@RequestParam String email, String pwd) {
        
        return new String();
    }
    
    
    
}
