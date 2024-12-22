package com.robot.cose.dto;

import lombok.Data;

import java.util.List;

@Data
public class CoseRequestDTO {
    private Integer id;
    private String coseName;
    private List<Integer> places;
}
