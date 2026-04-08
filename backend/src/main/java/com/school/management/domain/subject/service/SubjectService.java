package com.school.management.domain.subject.service;

import com.school.management.domain.subject.dto.SubjectCreateRequest;
import com.school.management.domain.subject.dto.SubjectResponse;
import com.school.management.domain.subject.dto.SubjectUpdateRequest;
import com.school.management.domain.subject.entity.Subject;
import com.school.management.domain.subject.repository.SubjectRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubjectService {
    private final SubjectRepository subjectRepository;

    // 읽기 전용 트랜잭션 — 조회만 하므로 성능 최적화
    @Transactional(readOnly = true)
    public List<SubjectResponse> getSubjects(){
        // 삭제 안 된 전체 과목을 SubjectResponse 리스트로 변환해서 반환
        return subjectRepository.findAllByIsDeletedFalse()
                .stream()
                .map(SubjectResponse::new)
                .collect(Collectors.toList());
    }

    // 과목 등록 — DB 변경이 있으므로 일반 트랜잭션
    @Transactional
    public SubjectResponse createSubject(SubjectCreateRequest request) {
        // teacher_id = null — seed 과목과 동일하게 시스템 과목으로 저장
        Subject subject = new Subject(
                null,
                request.getCategory(),
                request.getSubjectName(),
                request.getCredits(),
                request.isCareerElective(),
                request.getSchoolType(),
                request.getGradeType()
        );

        return new SubjectResponse(subjectRepository.save(subject));
    }

    // 과목 수정
    @Transactional
    public SubjectResponse updateSubject(Long subjectId, SubjectUpdateRequest request) {
        // 삭제된 과목은 조회 안 됨
        Subject subject = subjectRepository.findByIdAndIsDeletedFalse(subjectId)
                .orElseThrow(() -> new RuntimeException("과목을 찾을 수 없습니다."));

        // 엔티티의 update() 호출 — 트랜잭션 끝날 때 JPA 더티체킹이 자동으로 UPDATE 쿼리 실행
        subject.update(
                request.getCategory(),
                request.getSubjectName(),
                request.getCredits(),
                request.isCareerElective(),
                request.getSchoolType(),
                request.getGradeType()
        );

        return new SubjectResponse(subject);
    }

    // 과목 삭제 (Soft Delete — is_deleted = true, 실제 행은 남아있음)
    @Transactional
    public void deleteSubject(Long subjectId) {
        Subject subject = subjectRepository.findByIdAndIsDeletedFalse(subjectId)
                .orElseThrow(() -> new RuntimeException("과목을 찾을 수 없습니다."));

        // delete() 호출 후 save() 없어도 됨 — 더티체킹이 자동 반영
        subject.delete();
    }
}
