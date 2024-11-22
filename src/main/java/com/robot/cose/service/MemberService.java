package com.robot.cose.service;

import com.robot.cose.dto.MemberDTO;
import com.robot.cose.mapper.MemberMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MemberService{
    public final MemberMapper memberMapper;

    public MemberService(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    public String generateUuid(){//UUID 발급 메서드 (사용자 고유 ID)
        return UUID.randomUUID().toString();
    }

    public void registerNewMember(String email, String loginType){
        String uuid = generateUuid();
        MemberDTO memberDTO = new MemberDTO(email, uuid, loginType);
        this.memberMapper.registerNewMember(memberDTO);
        //return memberDTO;
    }

    public boolean checkMemberByEmail(String email) {
        return memberMapper.isNewMember(email);
    }
}
