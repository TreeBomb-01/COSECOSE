package com.robot.cose.dto;

import lombok.Data;

@Data
public class OperatingHoursDTO {
    public int operatingHoursIdx; // 고유 식별자
    public String dayOfWeek;      // 요일 (월요일, 화요일 등)
    public String openTime;       // 오픈 시간 (예: "09:00")
    public String closeTime;      // 마감 시간 (예: "22:00")
    public String breakTime;      // 휴식 시간 (예: "12:00~13:00")
}
