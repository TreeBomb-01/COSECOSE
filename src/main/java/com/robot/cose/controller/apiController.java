package com.robot.cose.controller;

import com.robot.cose.dto.SignUpRequestDTO;
import com.robot.cose.service.DateSpotService;
import com.robot.cose.service.MemberService;
import com.robot.cose.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4000")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class apiController {

    private final MemberService memberService;
    private final DateSpotService dateSpotService;

    @GetMapping("/check-nickname")
    public ResponseEntity<Map<String, Boolean>> checkNickname(@RequestParam String nickname) {
        System.out.println(nickname);
        boolean check = !memberService.checkMemberNickname(nickname);
        Map<String, Boolean> response = new HashMap<>();
        response.put("available", check);
        System.out.println(response);
        return ResponseEntity.ok(response);
    }

    @GetMapping("checkMoreInfoForUuid")
    public ResponseEntity<Map<String, Boolean>> checkMoreInfo(@RequestHeader("Authorization") String authorization) {
        String token = authorization.replace("Bearer ", "");
        String uuid = JwtUtil.validateToken(token);
        boolean needInfo = memberService.checkMoreInfoForUuid(uuid);
        Map<String, Boolean> response = new HashMap<>();
        response.put("needInfo", needInfo);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<Map<String, Boolean>> signup(@RequestHeader("Authorization") String authorizationHeader, @RequestBody SignUpRequestDTO request) {
        System.out.println(request);
        //토큰 추출
        String token = authorizationHeader.replace("Bearer ", "");
        System.out.println(token);

        //토큰에서 uuid 추출
        String uuid = JwtUtil.validateToken(token);

        memberService.saveFavorites(uuid,request.getFavorite());
        System.out.println(request);
        memberService.saveMoreInfo(uuid, request);

        Map<String, Boolean> response = new HashMap<>();
        response.put("success", true);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get_main_datespot")
    public ResponseEntity<Map<String, List<Map<String, Object>>>> getDateSpotsByCategory() {
        return ResponseEntity.ok(dateSpotService.getDateSpotsMainCategory());
    }

    @GetMapping("/get_datespot")
    public ResponseEntity<List<Map<String, Object>>> getDateSpots() {
        return ResponseEntity.ok(dateSpotService.getDateSpots());
    }

    @GetMapping("/get_one_datespt")
    public ResponseEntity<Map<String, Object>> getOneDateSpots(@RequestParam int id) {
        return ResponseEntity.ok(dateSpotService.getDateSpotbyId(id));
    }
}
