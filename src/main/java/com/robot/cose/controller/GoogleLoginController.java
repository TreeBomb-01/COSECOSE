package com.robot.cose.controller;

import com.robot.cose.service.GoogleLoginService;
import com.robot.cose.service.MemberService;
import com.robot.cose.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

@Controller
@RequestMapping("/google")
@RequiredArgsConstructor
public class GoogleLoginController {
    private final GoogleLoginService googleLoginService;
    private final MemberService memberService;

    @GetMapping("/login")
    public ResponseEntity<Void> login() {
        String googleAuthUrl = googleLoginService.getGoogleAuthUrl();
        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, googleAuthUrl)
                .build();
    }

    @GetMapping("/callback")
    public String callback(@RequestParam("code") String code) {
        HashMap<String,String> result = googleLoginService.handleGoogleLogin(code);
        System.out.println(result);
        String uuid = memberService.getMemberUuid(result.get("email"));
        String jwt = JwtUtil.generateToken(uuid);
        System.out.println(jwt);
        if (result.get("result").equals("Welcome back")) {
            return "mobile/mb_home/main";
        }
        return "mobile/mb_home/more_register.js";
    }
}
