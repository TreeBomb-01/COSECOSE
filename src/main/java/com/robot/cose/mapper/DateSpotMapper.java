package com.robot.cose.mapper;

import com.robot.cose.dto.DateSpotDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface DateSpotMapper {

    List<DateSpotDTO> getAllDateSpotById();
    List<Map<String, Object>> findMainCategory();
}
