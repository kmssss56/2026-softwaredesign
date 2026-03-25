cd ~/software-project/2026-project
cat > CLAUDE.md << 'EOF'
# 학생 성적 및 상담 관리 시스템

## 프로젝트 개요
중/고등학교 교사용 학생 성적, 학생부, 피드백, 상담 관리 시스템

## 기술 스택
- Backend: Java 21, Spring Boot 3.3, Spring Security, JPA, PostgreSQL, Redis
- Frontend: React + TypeScript, Tailwind CSS, Chart.js, PWA
- Infra: Docker, EC2 2대, Vercel, S3, Firebase, AWS SES

## 프로젝트 구조
- backend/ : Spring Boot (com.school.management)
- frontend/ : React + TypeScript
- .github/ : GitHub Actions CI/CD

## 패키지 구조
com.school.management
├── global/ (config, security, exception, dto, entity, util)
└── domain/ (auth, user, student, grade, feedback, counseling, notification, report, search)

## 주요 규칙
- Soft Delete: is_deleted 플래그 사용
- JWT: Access Token 30분, Refresh Token 7일
- RBAC: ADMIN / TEACHER / STUDENT / PARENT
- 중학교(A/B/C, 석차등급 없음) / 고등학교(A~E, 1~9등급) 분리
  EOF