package com.robot.cose.service;

import com.robot.cose.dto.MemberDTO;
import com.robot.cose.dto.SignUpRequestDTO;
import com.robot.cose.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public boolean checkMemberNeedMoreInfo(String email) {
        return memberMapper.checkMemberNeedMoreinfo(email);
    }

    public void more_register(MemberDTO memberDTO) {
        //로직 구현해야함
    }

    public String getMemberUuid(String email) {
        return memberMapper.getMemberUuid(email);
    }

    public boolean checkMemberNickname(String nickname) {
        return memberMapper.checkMemberNickname(nickname);
    }

    public boolean checkMoreInfoForUuid(String uuid){
        return memberMapper.checkMoreInfoForUuid(uuid);
    }

    public void saveFavorites(String memberUuid, Map<String, List<String>> favoriteMap) {
        List<Map<String, String>> favorites = new ArrayList<>();

        // favoriteMap을 각 카테고리별로 처리
        favoriteMap.forEach((category, values) -> {
            for (String value : values) {
                if(value.isBlank()) continue;
                Map<String, String> favorite = new HashMap<>();
                favorite.put("category", category);
                favorite.put("value", value);
                favorites.add(favorite);
            }
        });

        //취향 미 선택시 돌리기
        if(favorites.isEmpty()) return;

        // Mapper 호출
        memberMapper.insertFavorites(memberUuid, favorites);
    }

    public void saveMoreInfo(String memberUuid, SignUpRequestDTO signUpRequestDTO) {
        memberMapper.insertMoreInfo(memberUuid, signUpRequestDTO);
    }
}
