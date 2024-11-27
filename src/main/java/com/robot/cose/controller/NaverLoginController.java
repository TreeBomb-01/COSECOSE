package com.robot.cose.controller;

import com.robot.cose.service.NaverLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/naver")
@RequiredArgsConstructor
public class NaverLoginController {
    private final NaverLoginService naverLoginService;

    @GetMapping("/login")
    public ResponseEntity<Void> login() {
        String naverAuthUrl = naverLoginService.getNaverAuthUrl();
        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, naverAuthUrl)
                .build();
    }

    @GetMapping("/callback")
    public String callback(@RequestParam("code") String code, @RequestParam("state") String state) {
        String result = naverLoginService.handleNaverLogin(code, state);
        System.out.println(result);
        if (result.equals("Welcome back")) {
            return "mobile/mb_home/main";
        }
        return "mobile/mb_home/more_register";
    }
}

