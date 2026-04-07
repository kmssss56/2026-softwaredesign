package com.school.management.domain.student.repository;

import com.school.management.domain.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findAllByIsDeletedFalse();

    Optional<Student> findByIdAndIsDeletedFalse(Long Id);

    List<Student> findAllByGradeAndClassNumAndIsDeletedFalse(Integer grade, Integer classNum);
}
