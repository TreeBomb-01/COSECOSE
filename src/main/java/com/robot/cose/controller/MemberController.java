package com.robot.cose.controller;

import java.util.Map;

import com.robot.cose.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import com.robot.cose.dto.MemberDTO;

import lombok.RequiredArgsConstructor;





@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;


    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody MemberDTO memberDTO) {
        try {
            memberService.more_register(memberDTO);
            return ResponseEntity.ok().body(Map.of("message", "회원가입이 완료되었습니다."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    @RequestMapping("login")
    public String login(@RequestParam String email, String pwd) {

        return new String();
    }

    @GetMapping("register")
    public String register(Model model){
        model.addAttribute("memberDTO", new MemberDTO());
        return "home/member-insert";
    }

    @PostMapping("register")
    public RedirectView register(MemberDTO memberDTO) {
        //memberMapper.insert(memberDTO);
        return new RedirectView("memberList");
    }
    
}
