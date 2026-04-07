-- V11__alter_semester_summary_add_average_rank_grade.sql
-- 고등학교 학기 평균 석차등급 컬럼 추가
--
-- [average_rank_grade 산출 방식 — 진학사/나이스 기준]
--   고등학교 RELATIVE 과목만 포함 (공통과목, 일반선택 — 체육·예술 제외)
--   공식: Σ(석차등급 × 이수단위) / Σ(이수단위)
--   예) 수학Ⅰ(3단위, 2등급) + 국어(3단위, 3등급) = (6+9)/6 = 2.50
--
--   중학교는 석차등급 없음 → NULL 저장, 평균 원점수(average_score)만 사용

ALTER TABLE semester_summary ADD COLUMN average_rank_grade DECIMAL(4, 2);