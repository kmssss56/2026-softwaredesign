package com.school.management.domain.student.entity;

import com.school.management.domain.auth.entity.User;
import com.school.management.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "student")
public class Student extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer grade;

    @Column(nullable = false)
    private Integer classNum;

    @Column(nullable = false)
    private Integer number;

    // MIDDLE / HIGH (V9: ALTER TABLE student ADD COLUMN school_type)
    @Column(nullable = false, length = 10)
    private String schoolType;

    public Student(User user, String name, Integer grade, Integer classNum, Integer number, String schoolType) {
        this.user = user;
        this.name = name;
        this.grade = grade;
        this.classNum = classNum;
        this.number = number;
        this.schoolType = schoolType;
    }

    public void update(String name, Integer grade, Integer classNum, Integer number, String schoolType) {
        this.name = name;
        this.grade = grade;
        this.classNum = classNum;
        this.number = number;
        this.schoolType = schoolType;
    }

    public void delete() {
        this.isDeleted = true;
    }
}
