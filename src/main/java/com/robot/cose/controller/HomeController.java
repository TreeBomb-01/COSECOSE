package com.robot.cose.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;





@Controller
public class HomeController {
    
    @GetMapping(value = "/")
    public String home(Model model) {
        return "mobile/mb_home/login";
    }
}
