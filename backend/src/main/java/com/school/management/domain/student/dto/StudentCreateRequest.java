package com.school.management.domain.student.dto;

import lombok.Getter;

@Getter
public class StudentCreateRequest {
    private Long userId;
    private String name;
    private Integer grade;
    private Integer classNum;
    private Integer number;
    private String schoolType; // MIDDLE / HIGH
}
