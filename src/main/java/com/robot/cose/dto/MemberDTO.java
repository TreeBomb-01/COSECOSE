package com.robot.cose.dto;

import lombok.Data;

@Data
public class MemberDTO {
    private String email, uuid, name, nicname, tel, dob, location, favorites, coupleId, status, logintype, role;
    private char gender;

    public MemberDTO() {
        super();
    }

    public MemberDTO(String email, String uuid, String logintype) {
        this.email = email;
        this.uuid = uuid;
        this.logintype = logintype;
    }
}
