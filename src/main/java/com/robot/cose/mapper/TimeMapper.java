package com.robot.cose.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TimeMapper {
    public String getTime(); //쿼리문과 1대1로 매치가 됨
}
