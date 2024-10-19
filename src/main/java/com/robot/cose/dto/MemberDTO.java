package com.robot.cose.dto;

import lombok.Data;

@Data
public class MemberDTO {
    private String email, password, name, tel, birthday, nicname, place, taste, coupleId;
    private char gender;
}
