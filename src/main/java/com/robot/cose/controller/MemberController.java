package com.robot.cose.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.robot.cose.dto.MemberDTO;
import com.robot.cose.mapper.MemberMapper;

import lombok.RequiredArgsConstructor;





@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberMapper memberMapper;

    
    
    @RequestMapping("login")
    public String login(@RequestParam String email, String pwd) {
        
        return new String();
    }
    
    @GetMapping("memberList")
    public String listString(Model model) {
        List<MemberDTO> list = memberMapper.selectAll();
        model.addAttribute("list", list);
        System.out.println(list.toString());
        return "admin/member-list";
    }

    @GetMapping("register")
    public String register(Model model){
        model.addAttribute("memberDTO", new MemberDTO());
        return "home/member-insert";
    }

    @PostMapping("register")
    public RedirectView register(MemberDTO memberDTO) {
        memberMapper.insert(memberDTO);        
        return new RedirectView("memberList");
    }

    
    
    
    
    
}
