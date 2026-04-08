package com.school.management.domain.subject.dto;

import lombok.Getter;

@Getter
public class SubjectUpdateRequest {
    // 수정할 과목 분류 (예: "국어", "수학", "일반선택")
    // private = 이 클래스 밖에서 직접 접근 불가, getter를 통해서만 접근
    private String category;

    // 수정할 과목명 (예: "수학Ⅰ", "문학")
    private String subjectName;

    // 수정할 단위수(학점) — Integer는 null 가능 (int는 null 불가)
    private Integer credits;

    // 수정할 진로선택 여부
    // boolean(소문자) = 기본형, null 불가, 기본값 false
    private boolean isCareerElective;

    // 수정할 학교 구분 : "MIDDLE" 또는 "HIGH"
    private String schoolType;

    // 수정할 평가 방식 : "RELATIVE" / "ABSOLUTE" / "PASS_FAIL"
    private String gradeType;
}
