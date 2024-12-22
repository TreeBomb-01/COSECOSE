package com.robot.cose.controller;

import com.robot.cose.dto.CoseDTO;
import com.robot.cose.dto.CoseRequestDTO;
import com.robot.cose.dto.SignUpRequestDTO;
import com.robot.cose.service.CoseService;
import com.robot.cose.service.DateSpotService;
import com.robot.cose.service.MemberService;
import com.robot.cose.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    private final CoseService coseService;

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

    @PostMapping("/create_cose")
    public ResponseEntity<?> saveCose(@RequestBody CoseRequestDTO coseRequestDTO,
                                                         @RequestHeader("Authorization") String authorizationHeader) {
        try {
            String token = authorizationHeader.replace("Bearer ", "");
            String uuid = JwtUtil.validateToken(token);
            coseService.create_cose(uuid, coseRequestDTO);
            // 성공 시 간단한 메시지 반환
            return ResponseEntity.ok("코스가 성공적으로 생성되었습니다.");
        } catch (Exception e) {
            // 예외 처리 및 실패 응답
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("코스 생성 중 오류가 발생했습니다.");
        }
    }

    @GetMapping("get_coses")
    public ResponseEntity<List<Map<String, Object>>> getCose(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            String token = authorizationHeader.replace("Bearer ", "");
            List<Map<String, Object>> cose = coseService.get_cose(token);
            return ResponseEntity.ok(coseService.transformData(cose));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}