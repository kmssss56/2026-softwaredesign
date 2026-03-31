-- V5__create_student_record.sql

CREATE TABLE student_record (
                                id          BIGSERIAL    PRIMARY KEY,
                                student_id  BIGINT       NOT NULL REFERENCES student(id),
                                created_by  BIGINT       NOT NULL REFERENCES users(id),  -- 작성 교사
                                category    VARCHAR(50)  NOT NULL,
                                note        TEXT,
                                record_date DATE         NOT NULL,
                                semester    VARCHAR(10)  NOT NULL,
                                created_at  TIMESTAMP    NOT NULL DEFAULT NOW(),
                                updated_at  TIMESTAMP    NOT NULL DEFAULT NOW(),
                                is_deleted  BOOLEAN      NOT NULL DEFAULT FALSE
);
