# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## 프로젝트 개요

중/고등학교 교사용 학생 성적, 학생부, 피드백, 상담 관리 시스템. 4개 역할(ADMIN/TEACHER/STUDENT/PARENT) 기반 RBAC.

## 개발 환경

### 인프라 (Docker)
```bash
cd backend && docker-compose up -d   # PostgreSQL 16 (5433), Redis 7 (6379)
cd backend && docker-compose down     # 중지
```

### 시크릿
```bash
cp backend/src/main/resources/application-secret.yml.example backend/src/main/resources/application-secret.yml
# DB_PASSWORD, REDIS_PASSWORD (기본값: redis1234), JWT_SECRET 입력
```

### 백엔드 (Java 21 + Spring Boot 3.3)
```bash
cd backend
./gradlew bootRun                    # 개발 서버 (localhost:8080)
./gradlew build                      # 빌드
./gradlew test                       # 전체 테스트
./gradlew test --tests "com.school.management.domain.auth.AuthServiceTest"  # 단일 테스트
./gradlew clean build                # 클린 빌드
```

### 프론트엔드 (React 19 + TypeScript + Vite)
```bash
cd frontend
npm install                          # 의존성 설치
npm run dev                          # 개발 서버 (localhost:5173)
npm run build                        # 프로덕션 빌드 (tsc + vite build)
npm run lint                         # ESLint
```

### 주요 URL
- Swagger UI: http://localhost:8080/swagger-ui.html
- Prometheus: http://localhost:8080/actuator/prometheus

## 기술 스택

- **Backend**: Java 21, Spring Boot 3.3, Spring Security, JPA/Hibernate, Flyway, PostgreSQL 16, Redis 7
- **Frontend**: React 19, TypeScript, Vite, Tailwind CSS v4, Chart.js, Axios
- **Infra**: Docker, EC2, Vercel, S3, Firebase FCM, AWS SES

## 아키텍처

### 백엔드 패키지 구조
```
com.school.management
├── global/
│   ├── config/         # SecurityConfig, RedisConfig
│   ├── security/       # JwtProvider, JwtAuthenticationFilter, CustomUserDetailsService
│   ├── exception/      # GlobalExceptionHandler (@RestControllerAdvice)
│   ├── dto/            # ApiResponse<T> (공통 응답 래퍼)
│   └── entity/         # BaseEntity (createdAt, updatedAt, isDeleted)
└── domain/
    ├── auth/           # 로그인/로그아웃/토큰 재발급
    ├── student/        # 학생 CRUD
    ├── subject/        # 교과목 + 성적 + 레이더차트
    ├── attendance/     # 출결
    ├── record/         # 학생부
    ├── feedback/       # 피드백 (미구현)
    ├── counseling/     # 상담 (미구현)
    ├── notification/   # 알림 (미구현)
    └── report/         # 보고서 (미구현)
```

각 도메인은 `controller/`, `service/`, `dto/`, `entity/`, `repository/` 하위 패키지로 구성.

### 프론트엔드 구조
```
frontend/src/
├── api/client.ts       # Axios 인스턴스 (JWT 인터셉터, 401 자동 로그아웃)
├── hooks/useAuth.ts    # 인증 상태 (localStorage 기반, React hooks)
├── components/
│   ├── common/         # Button, Card, Badge, Modal, Input, Select 등
│   └── layout/         # MainLayout, Header, Sidebar
├── pages/              # 역할별 페이지 (teacher, student, parent, admin)
├── services/           # API 호출 모듈 (*Service.ts)
└── types/              # TypeScript 인터페이스
```

라우팅은 `App.tsx`에서 역할별로 구성. 상태관리는 React hooks + localStorage (Redux 없음).

## 핵심 규칙

### Soft Delete
모든 엔티티는 `BaseEntity`를 상속하며 `is_deleted` 플래그로 소프트 삭제. 물리 삭제 금지. Repository 조회 시 `findByIdAndIsDeletedFalse` 패턴 사용.

### DB 마이그레이션
Flyway 사용. DDL auto는 `validate` — 스키마 변경은 반드시 마이그레이션 파일로 작성.
- 위치: `backend/src/main/resources/db/migration/`
- 파일명: `V{버전}__{설명}.sql` (현재 V11까지 존재)

### JWT 인증
- Access Token 30분 / Refresh Token 7일
- Refresh Token은 Redis에 `refresh:{userId}` 키로 저장
- JwtProvider에서 userId + role을 claims에 포함

### 성적 체계
- 중학교: A/B/C 등급, 석차등급 없음
- 고등학교: A~E 등급, 석차등급 1~9등급

### 캐싱
레이더 차트 성적 조회에 Redis 캐시 적용 (`@Cacheable`, TTL 10분). 성적 CUD 시 `@CacheEvict`로 무효화.

### API 응답
모든 API는 `ApiResponse<T>`로 래핑 (`success/error` 팩토리 메서드).

### DTO 패턴
Request/Response DTO에 `static from(Entity)` 팩토리 메서드로 엔티티 변환.

### 프론트엔드 API 호출
`api/client.ts`의 Axios 인스턴스 사용. 환경변수 `VITE_API_URL` (기본값: `http://localhost:8080/api`).
