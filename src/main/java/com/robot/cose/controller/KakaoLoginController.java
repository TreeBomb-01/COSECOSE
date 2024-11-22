package com.robot.cose.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.robot.cose.service.KakaoLoginService;
import groovy.lang.GString;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Controller
@RequestMapping("/kakao")
public class KakaoLoginController {

    private final KakaoLoginService kakaoLoginService;

    public KakaoLoginController(KakaoLoginService kakaoLoginService) {
        this.kakaoLoginService = kakaoLoginService;
    }

    @GetMapping("/login")
    public ResponseEntity<Void> loginPage(){
        String kakaoLoginUrl = kakaoLoginService.loginPage();
        return ResponseEntity.status(302) // HTTP 리다이렉트 상태코드
                .header("Location", kakaoLoginUrl) // 리다이렉트 URL 설정
                .build();
    }

    //콜백 함수 access 토큰, 사용자 정보 받아오고 반환
    @GetMapping("/callback")
    public String checkUser(@RequestParam("code") String code){
        String email = kakaoLoginService.handleKakaoLogin(code);
        System.out.println(email);
        return "mobile/mb_home/kakao_login";
    }
}
