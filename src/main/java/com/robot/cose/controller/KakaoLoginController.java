package com.robot.cose.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
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

    @Value("${kakao.client_id}") //카카오로그인 키값
    private String client_id;

    @Value("${kakao.redirect_uri}") //반환 URI
    private String redirect_uri;

    //로그인 페이지 요청
    @GetMapping("/login")
    public void loginPage(HttpServletResponse response) throws IOException {
        String kakaoLoginUrl = "https://kauth.kakao.com/oauth/authorize?response_type=code"
                + "&client_id=" + client_id
                + "&redirect_uri=" + redirect_uri;

        response.sendRedirect(kakaoLoginUrl);
    }

    //콜백 함수 access 토큰, 사용자 정보 받아오고 반환
    @GetMapping("/callback")
    public ResponseEntity<String> kakaoCallback(@RequestParam("code") String code) {
        String accessToken = getAccessToken(code);

        if (accessToken == null) { //토큰이 없을경우 처리
            return ResponseEntity.status(500).body("Failed to retrieve access token.");
        }
        JSONObject userInfo = getUserInfo(accessToken);

        if (userInfo == null) {
            return ResponseEntity.status(500).body("Failed to retrieve user info.");
        }
        System.out.println(userInfo);
        return ResponseEntity.ok("<h1>" + userInfo + "<h1>");

        //return ResponseEntity.ok("Access Token:" + accessToken);
    }

    //access토큰 가져오는 메서드
    private String getAccessToken(String code) {
        String tokenUrl = "https://kauth.kakao.com/oauth/token";

        try{
            //HTTP 요청을 보낼 StringBuffer
            StringBuilder postData = new StringBuilder();
            postData.append("grant_type=authorization_code");
            postData.append("&client_id=" + client_id);
            postData.append("&redirect_uri=" + redirect_uri);
            postData.append("&code=" + code);

            //HTTP 연결 설정
            URL url = new URL(tokenUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.getOutputStream().write(postData.toString().getBytes());

            //응답 데이터 읽기
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while( (inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //JSON 파싱(Access Tokken 추출)
            JSONObject json = new JSONObject(response.toString());
            return json.getString("access_token");

        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    //유저 정보 가져오는 메서드
    public JSONObject getUserInfo(String accessToken) {
        String userInfoUrl = "https://kapi.kakao.com/v2/user/me";

       try{
           URL url = new URL(userInfoUrl);
           HttpURLConnection con = (HttpURLConnection) url.openConnection();
           con.setRequestMethod("GET");

           con.setRequestProperty("Authorization", "Bearer " + accessToken);

           int responseCode = con.getResponseCode();
           if(responseCode != HttpURLConnection.HTTP_OK) {
               System.out.println("Failed to retrieve user info. Response Code: " + responseCode);
               return null;
           }

           BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
           StringBuilder response = new StringBuilder();
           String inputLine;
           while( (inputLine = in.readLine()) != null) {
               response.append(inputLine);
           }
           in.close();
           return new JSONObject(response.toString());
       } catch (Exception e){
           e.printStackTrace();
           return null;
       }

    }


//    @GetMapping("kakao")
//    public String login() {
//        return "test/kakao";
//    }
}
