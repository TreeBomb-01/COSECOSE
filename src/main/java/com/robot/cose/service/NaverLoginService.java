package com.robot.cose.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;


@Service
public class NaverLoginService {

    private final MemberService memberService;

    public NaverLoginService(MemberService memberService) {
        this.memberService = memberService;
    }

    @Value("${naver.client-id}")
    private String clientId;

    @Value("${naver.client-secret}")
    private String clientSecret;

    @Value("${naver.redirect-uri}")
    private String redirectUri;

    @Value("${naver.token-url}")
    private String tokenUrl;

    @Value("${naver.user-info-url}")
    private String userInfoUrl;

    public String getNaverAuthUrl() {
        return "https://nid.naver.com/oauth2.0/authorize"
                + "?response_type=code"
                + "&client_id=" + clientId
                + "&redirect_uri=" + redirectUri
                + "&state=" + UUID.randomUUID(); // CSRF 방지를 위한 state 값
    }

    public String getAccessToken(String code, String state) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("grant_type", "authorization_code");
            params.add("client_id", clientId);
            params.add("client_secret", clientSecret);
            params.add("code", code);
            params.add("state", state);

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(tokenUrl, request, String.class);
            JSONObject json = new JSONObject(response.getBody());

            return json.getString("access_token");
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve access token from Naver.", e);
        }
    }

    public JSONObject getUserInfo(String accessToken) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + accessToken);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(userInfoUrl, HttpMethod.GET, entity, String.class);
            return new JSONObject(response.getBody());
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve user info from Naver.", e);
        }
    }

    public String handleGoogleLogin(String code, String state) {
        // Access Token 요청
        String accessToken = getAccessToken(code, state);

        if (accessToken == null) {
            throw new RuntimeException("Failed to retrieve access token.");
        }

        // 사용자 정보 요청
        JSONObject userInfo = getUserInfo(accessToken);
        if (userInfo == null) {
            throw new RuntimeException("Failed to retrieve user info.");
        }

        // 이메일 추출
        JSONObject email = userInfo.getJSONObject("response");
        if (!email.has("email")) { //email 정보가 있는지 없는지 확인
            throw new RuntimeException("Email not provided by Kakao.");
        }

        String emailstr = email.getString("email");
        // 신규/기존 회원 확인
        if (memberService.checkMemberByEmail(emailstr)) {
            return "Welcome back! Your email: " + emailstr;
        } else {
            memberService.registerNewMember(emailstr,"GOOGLE");
            return "New user registered with email: " + emailstr;
        }
    }
}
