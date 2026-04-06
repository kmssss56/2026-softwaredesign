package com.school.management.domain.student.dto;

import com.school.management.domain.student.entity.Student;
import lombok.Getter;

@Getter
public class StudentResponse {
    private Long id;
    private String name;
    private Integer grade;
    private Integer classNum;
    private Integer number;
    private String email;

    public static StudentResponse from(Student student) { // Entity -> DTO 변환
        StudentResponse response = new StudentResponse();
        response.id = student.getId();
        response.name = student.getName();
        response.grade = student.getGrade();
        response.classNum = student.getClassNum();
        response.number = student.getNumber();
        response.email = student.getUser().getEmail();
        return response;
    }
}
