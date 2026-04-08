package com.school.management.domain.subject.controller;

import com.school.management.domain.subject.dto.SubjectCreateRequest;
import com.school.management.domain.subject.dto.SubjectResponse;
import com.school.management.domain.subject.dto.SubjectUpdateRequest;
import com.school.management.domain.subject.service.SubjectService;
// ApiResponse = 이 프로젝트의 공통 응답 포맷 { data, message, status }
import com.school.management.global.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
// @RestController = @Controller + @ResponseBody
// @Controller : 이 클래스가 요청을 처리하는 컨트롤러임을 Spring에 알림
// @ResponseBody : 반환값을 JSON으로 변환해서 응답 body에 담음
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// @RequestMapping = 이 컨트롤러의 모든 API 공통 경로 prefix
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/subjects")
public class SubjectController {

    private final SubjectService subjectService;

    // GET /api/subjects — 전체 과목 조회
    // @GetMapping = HTTP GET 요청을 이 메서드에 연결
    @GetMapping
    public ResponseEntity<ApiResponse<List<SubjectResponse>>> getSubjects() {
        List<SubjectResponse> subjects = subjectService.getSubjects();
        // ResponseEntity = HTTP 응답 전체(상태코드 + body)를 담는 객체
        // .ok() = 200 OK
        return ResponseEntity.ok(ApiResponse.success("과목 조회 성공", subjects));
    }

    // POST /api/subjects — 과목 등록
    // @PostMapping = HTTP POST 요청을 이 메서드에 연결
    // @RequestBody = HTTP 요청 body의 JSON을 Java 객체로 변환
    @PostMapping
    public ResponseEntity<ApiResponse<SubjectResponse>> createSubject(
            @RequestBody SubjectCreateRequest request) {
        SubjectResponse response = subjectService.createSubject(request);
        return ResponseEntity.ok(ApiResponse.success("과목 등록 성공", response));
    }

    // PUT /api/subjects/{id} — 과목 수정
    // @PathVariable = URL 경로의 {id} 값을 파라미터로 받음
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SubjectResponse>> updateSubject(
            @PathVariable Long id,
            @RequestBody SubjectUpdateRequest request) {
        SubjectResponse response = subjectService.updateSubject(id, request);
        return ResponseEntity.ok(ApiResponse.success("과목 수정 성공", response));
    }

    // DELETE /api/subjects/{id} — 과목 삭제 (Soft Delete)
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteSubject(
            @PathVariable Long id) {
        subjectService.deleteSubject(id);
        // Void = 반환 데이터 없음, 성공 메시지만 전달
        return ResponseEntity.ok(ApiResponse.success("과목 삭제 성공"));
    }
}