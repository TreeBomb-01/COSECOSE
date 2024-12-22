package com.robot.cose.controller;

import com.robot.cose.service.KakaoLoginService;
import com.robot.cose.service.MemberService;
import com.robot.cose.util.JwtUtil;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4000")
@RestController
@RequestMapping("/kakao")
@RequiredArgsConstructor
public class KakaoLoginController {

    private final KakaoLoginService kakaoLoginService;
    private final MemberService memberService;

    @GetMapping("/login")
    public ResponseEntity<String> loginPage(){
        String kakaoLoginUrl = kakaoLoginService.loginPage();
        return ResponseEntity.ok(kakaoLoginUrl);
    }

    @PostMapping("/callback")
    public ResponseEntity<Map<String, String>> checkUser(@RequestBody Map<String, String> request) {
        HashMap<String, String> result = kakaoLoginService.handleKakaoLogin(request.get("code"));
        String uuid = memberService.getMemberUuid(result.get("email"));
        String jwt = JwtUtil.generateToken(uuid);
        
        // JSON 형태로 JWT 반환
        Map<String, String> response = new HashMap<>();
        response.put("token", jwt);
        return ResponseEntity.ok(response);
    }
}
