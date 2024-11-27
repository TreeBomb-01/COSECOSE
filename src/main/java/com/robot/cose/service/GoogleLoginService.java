package com.robot.cose.service;



import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GoogleLoginService {
    private final MemberService memberService;


    @Value("${google.client-id}")
    private String clientId;

    @Value("${google.client_secret}")
    private String clientSecret;

    @Value("${google.redirect-uri}")
    private String redirectUri;

    @Value("${google.token-url}")
    private String tokenUrl;

    @Value("${google.user-info-url}")
    private String userInfoUrl;

    public String getGoogleAuthUrl() {
        return "https://accounts.google.com/o/oauth2/auth"
                + "?client_id=" + clientId
                + "&redirect_uri=" + redirectUri
                + "&response_type=code"
                + "&scope=https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/userinfo.profile";
    }
    
    //접근코드발급
    public String getAccessToken(String code) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("code", code);
            params.add("client_id", clientId);
            params.add("client_secret", clientSecret);
            params.add("redirect_uri", redirectUri);
            params.add("grant_type", "authorization_code");

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(tokenUrl, request, String.class);
            JSONObject json = new JSONObject(response.getBody());

            return json.getString("access_token");
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve access token from Google.", e);
        }
    }

    //사용자 정보
    public JSONObject getUserInfo(String accessToken) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + accessToken);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(userInfoUrl, HttpMethod.GET, entity, String.class);
            return new JSONObject(response.getBody());
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve user info from Google.", e);
        }
    }

    //구글 로그인 핸들링
    public HashMap<String,String> handleGoogleLogin(String code) {
        HashMap<String,String> result = new HashMap<>();
        // Access Token 요청
        String accessToken = getAccessToken(code);

        if (accessToken == null) {
            throw new RuntimeException("Failed to retrieve access token.");
        }

        // 사용자 정보 요청
        JSONObject userInfo = getUserInfo(accessToken);
        if (userInfo == null) {
            throw new RuntimeException("Failed to retrieve user info.");
        }
        System.out.println(userInfo);
        // 이메일 추출
        if (!userInfo.has("email")) { //email 정보가 있는지 없는지 확인
            throw new RuntimeException("Email not provided by GOOGLE.");
        }
        String emailstr = userInfo.getString("email");
        result.put("email", emailstr);

        // 신규/기존 회원 확인
        if (memberService.checkMemberByEmail(emailstr)) {
            if(memberService.checkMemberNeedMoreInfo(emailstr)) {
                result.put("result","needModeInfo");
                return result;
            }
            result.put("result","Welcome back");
            return result;
        } else {
            memberService.registerNewMember(emailstr,"GOOGLE");
            result.put("result","New Member");
            return result;
        }
    }
}
