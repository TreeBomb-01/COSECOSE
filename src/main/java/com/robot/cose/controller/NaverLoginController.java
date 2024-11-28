package com.robot.cose.controller;

import com.robot.cose.service.MemberService;
import com.robot.cose.service.NaverLoginService;
import com.robot.cose.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

@Controller
@RequestMapping("/naver")
@RequiredArgsConstructor
public class NaverLoginController {
    private final NaverLoginService naverLoginService;
    private final MemberService memberService;

    @GetMapping("/login")
    public ResponseEntity<Void> login() {
        String naverAuthUrl = naverLoginService.getNaverAuthUrl();
        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, naverAuthUrl)
                .build();
    }

    @GetMapping("/callback")
    public String callback(@RequestParam("code") String code, @RequestParam("state") String state) {
        HashMap<String, String> result = naverLoginService.handleNaverLogin(code, state);
        System.out.println(result);
        String uuid = memberService.getMemberUuid(result.get("email"));
        String jwt = JwtUtil.generateToken(uuid);
        System.out.println(jwt);
        if (result.get("result").equals("Welcome back")) {
            return "mobile/mb_home/main";
        }
        return "mobile/mb_home/more_register";
    }
}

