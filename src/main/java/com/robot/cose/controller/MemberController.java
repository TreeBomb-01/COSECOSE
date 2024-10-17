package com.robot.cose.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;






@Controller
public class MemberController {
    
    @RequestMapping("login")
    public String login(@RequestParam String email, String pwd) {
        
        return new String();
    }
    
    @GetMapping
    public List<MemberEntity> getAllMember() {
        return new List<MemberEntity>();
    }
    
    
    
}
