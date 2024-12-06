package com.robot.cose.dto;

import lombok.Data;

import java.util.List;

@Data
public class DateSpotDTO {
    public int dateSpotIdx;       // 고유 식별자
    public String spotName;       // 장소 이름
    public String locate;         // 위치 정보
    public String info;           // 장소 설명
    public String imageURL;       // 이미지 URL
    public List<OperatingHoursDTO> operatingHoursList; // 영업시간 정보
}

