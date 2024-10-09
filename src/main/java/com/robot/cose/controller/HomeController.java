package com.robot.cose.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;




@Controller
public class HomeController {
    
    @GetMapping(value = "/")
    public String home(Model model) {
        
        model.addAttribute("test", "안녕하세요~!");
        return "home/index.html";
    }

    @GetMapping("login_form")
    public String login_form(Model model) {
        return "home/login_form";
    }
    
    
}
