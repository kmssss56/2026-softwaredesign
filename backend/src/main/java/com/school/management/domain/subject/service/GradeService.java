package com.school.management.domain.subject.service;

import com.school.management.domain.student.entity.Student;
import com.school.management.domain.student.repository.StudentRepository;
import com.school.management.domain.subject.entity.ClassStatistic;
import com.school.management.domain.subject.entity.Grade;
import com.school.management.domain.subject.entity.SemesterSummary;
import com.school.management.domain.subject.repository.ClassStatisticRepository;
import com.school.management.domain.subject.repository.GradeRepository;
import com.school.management.domain.subject.repository.SemesterSummaryRepository;
import com.school.management.domain.subject.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GradeService {

    private final GradeRepository gradeRepository;
    private final ClassStatisticRepository classStatisticRepository;
    private final SemesterSummaryRepository semesterSummaryRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;

    @Transactional
    public void registerGrade(Long studentId, Long classStatisticId, BigDecimal rawScore) {

        Student student = studentRepository.findByIdAndIsDeletedFalse(studentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 학생입니다."));

        ClassStatistic classStatistic = classStatisticRepository.findById(classStatisticId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 과목 통계입니다."));

        String achievementLevel = calculateAchievementLevel(
                rawScore,
                classStatistic.getAverageScore(),
                classStatistic.getStandardDev()
        );

        Grade grade = new Grade(student, classStatistic, rawScore, achievementLevel);
        gradeRepository.save(grade);

        updateSemesterSummary(student, classStatistic.getSemester());
    }

    // 성취도 자동 계산
    // 평균 + 표준편차 기반으로 A~E 등급 계산
    private String calculateAchievementLevel(BigDecimal rawScore, BigDecimal average, BigDecimal standardDev) {

        // 점수가 평균 + 1표준편차 이상 → A
        // 평균 ~ 평균 + 1표준편차 → B
        // 평균 - 1표준편차 ~ 평균 → C
        // 평균 - 2표준편차 ~ 평균 - 1표준편차 → D
        // 그 이하 → E
        BigDecimal oneSd = average.add(standardDev);
        BigDecimal minusOneSd = average.subtract(standardDev);
        BigDecimal minusTwoSd = average.subtract(standardDev.multiply(BigDecimal.valueOf(2)));

        if (rawScore.compareTo(oneSd) >= 0) return "A";
        if (rawScore.compareTo(average) >= 0) return "B";
        if (rawScore.compareTo(minusOneSd) >= 0) return "C";
        if (rawScore.compareTo(minusTwoSd) >= 0) return "D";
        return "E";
    }

    // 학기 요약 자동 업데이트
    // 성적 등록/수정/삭제 시 자동으로 호출
    private void updateSemesterSummary(Student student, String semester) {

        // 해당 학기 성적 전체 조회
        List<Grade> grades = gradeRepository.findAllByStudentIdAndIsDeletedFalse(student.getId())
                .stream()
                .filter(g -> g.getClassStatistic().getSemester().equals(semester))
                .toList();

        if (grades.isEmpty()) return;

        // 총점 계산
        BigDecimal totalScore = grades.stream()
                .map(Grade::getRawScore)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 총 이수 학점 계산
        int totalCredits = grades.stream()
                .mapToInt(g -> g.getClassStatistic().getSubject().getCredits())
                .sum();

        // 평균 계산
        BigDecimal averageScore = totalScore.divide(
                BigDecimal.valueOf(grades.size()), 2, RoundingMode.HALF_UP);

        // 기존 요약 있으면 업데이트, 없으면 새로 생성
        SemesterSummary summary = semesterSummaryRepository
                .findByStudentIdAndSemesterAndIsDeletedFalse(student.getId(), semester)
                .orElse(null);

        if (summary != null) {
            summary.update(totalCredits, totalScore, averageScore, null);
        } else {
            semesterSummaryRepository.save(
                    new SemesterSummary(student, semester, totalCredits, totalScore, averageScore, null)
            );
        }
    }

    // 학생 성적 조회
    @Transactional(readOnly = true)
    public List<Grade> getStudentGrades(Long studentId) {
        return gradeRepository.findAllByStudentIdAndIsDeletedFalse(studentId);
    }

    // 학기 요약 조회 (레이더 차트)
    @Transactional(readOnly = true)
    public List<SemesterSummary> getStudentSemesterSummaries(Long studentId) {
        return semesterSummaryRepository.findAllByStudentIdAndIsDeletedFalse(studentId);
    }
}
