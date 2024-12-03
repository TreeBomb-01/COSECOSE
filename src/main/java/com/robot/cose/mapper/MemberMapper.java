package com.robot.cose.mapper;

import com.robot.cose.dto.SignUpRequestDTO;
import org.apache.ibatis.annotations.Mapper;

import com.robot.cose.dto.MemberDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface MemberMapper {
    List<MemberDTO> selectAll();

    void registerNewMember(MemberDTO memberDTO);

    boolean isNewMember(String email);

    boolean checkMemberNeedMoreinfo(String email);

    String getMemberUuid(String email);

    boolean checkMemberNickname(String nickname);

    boolean checkMoreInfoForUuid(String uuid);

    void insertFavorites(String memberUuid, List<Map<String, String>> favorites);

    //void insertMoreInfo(String memberUuid, SignUpRequestDTO signUpRequestDTO);
    void insertMoreInfo(@Param("memberUuid") String memberUuid, @Param("signUpRequestDTO") SignUpRequestDTO signUpRequestDTO);

}
