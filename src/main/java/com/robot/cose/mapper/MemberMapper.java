package com.robot.cose.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.robot.cose.dto.MemberDTO;
import java.util.List;

@Mapper
public interface MemberMapper {
    public List<MemberDTO> selectAll();

    public void insert(MemberDTO memberDTO);
}
