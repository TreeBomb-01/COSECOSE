package com.robot.cose.mapper;

import com.robot.cose.dto.CoseDTO;
import com.robot.cose.dto.CoseRequestDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CoseMapper {
    void createCose(String uuid, CoseRequestDTO dto);
    void createCosePlaces(CoseRequestDTO dto);
    List<Map<String, Object>> getCose(String uuid);
}
