package com.robot.cose.controller;

import com.robot.cose.service.GoogleLoginService;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/google")
public class GoogleLoginController {
    private final GoogleLoginService googleLoginService;

    public GoogleLoginController(GoogleLoginService googleLoginService) {
        this.googleLoginService = googleLoginService;
    }

    @GetMapping("/login")
    public ResponseEntity<Void> login() {
        String googleAuthUrl = googleLoginService.getGoogleAuthUrl();
        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, googleAuthUrl)
                .build();
    }

    @GetMapping("/callback")
    public String callback(@RequestParam("code") String code) {
        String email = googleLoginService.handleGoogleLogin(code);
        System.out.println(email);
        return "mobile/mb_home/google_login";
    }
}
