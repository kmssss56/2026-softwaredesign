package com.school.management.domain.subject.dto;

import com.school.management.domain.subject.entity.Subject;
import lombok.Getter;

@Getter
public class SubjectResponse {
    private Long id;              // 과목 고유 번호 (수정/삭제 시 필요)
    private String category;      // 과목 분류
    private String subjectName;   // 과목명
    private Integer credits;      // 단위수
    private boolean isCareerElective; // 진로선택 여부
    private String schoolType;    // 학교 구분
    private String gradeType;     // 평가 방식

    //생성자 -subject 엔티티를 받아서 response 객체로 변환
    public SubjectResponse(Subject subject) {
        this.id = subject.getId();
        this.category = subject.getCategory();
        this.subjectName = subject.getSubjectName();
        this.credits = subject.getCredits();
        this.isCareerElective = subject.isCareerElective();
        this.schoolType = subject.getSchoolType();
        this.gradeType = subject.getGradeType();
    }
}



