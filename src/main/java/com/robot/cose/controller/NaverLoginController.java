package com.robot.cose.controller;

import com.robot.cose.service.NaverLoginService;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/naver")
public class NaverLoginController {
    private final NaverLoginService naverLoginService;

    public NaverLoginController(NaverLoginService naverLoginService) {
        this.naverLoginService = naverLoginService;
    }

    @GetMapping("/login")
    public ResponseEntity<Void> login() {
        String naverAuthUrl = naverLoginService.getNaverAuthUrl();
        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, naverAuthUrl)
                .build();
    }

    @GetMapping("/callback")
    public ResponseEntity<String> callback(@RequestParam("code") String code, @RequestParam("state") String state) {
        try {
            String accessToken = naverLoginService.getAccessToken(code, state);
            JSONObject userInfo = naverLoginService.getUserInfo(accessToken);

            // 사용자 정보 반환
            return ResponseEntity.ok(userInfo.toString());
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}

