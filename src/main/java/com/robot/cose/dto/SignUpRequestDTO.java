package com.robot.cose.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class SignUpRequestDTO {
    private String name;
    private String phone;
    private String nickname;
    private String mbti;
    private Map<String, List<String>> favorite;
    private String dob;
    private String gender;
}
