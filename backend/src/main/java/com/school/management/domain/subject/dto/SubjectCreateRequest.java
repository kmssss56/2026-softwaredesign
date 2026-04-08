package com.school.management.domain.subject.dto;

import lombok.Getter;

@Getter
public class SubjectCreateRequest {
    private String category; // 과목 분류
    private String subjectName; // 과목명
    private Integer credits; //단위수(학점)
    private boolean isCareerElective; //진로선택 여부(true/false)
    private String schoolType; //학교 구분 : "MIDDLE" OR "HIGH"
    private String gradeType; //평가방식 :"RELATIVE" / "ABSOLUTE" / "PASS_FAIL"

}
