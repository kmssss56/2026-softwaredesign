-- V3__create_subject_and_grade.sql

CREATE TABLE subject (
                         id                 BIGSERIAL    PRIMARY KEY,
                         teacher_id         BIGINT       NOT NULL REFERENCES users(id),
                         category           VARCHAR(20)  NOT NULL,
                         subject_name       VARCHAR(255) NOT NULL,
                         credits            INT          NOT NULL,
                         is_career_elective BOOLEAN      NOT NULL DEFAULT FALSE,
                         created_at         TIMESTAMP    NOT NULL DEFAULT NOW(),
                         updated_at         TIMESTAMP    NOT NULL DEFAULT NOW(),
                         is_deleted         BOOLEAN      NOT NULL DEFAULT FALSE
);

CREATE TABLE class_statistic (
                                 id             BIGSERIAL      PRIMARY KEY,
                                 teacher_id     BIGINT         NOT NULL REFERENCES users(id),
                                 subject_id     BIGINT         NOT NULL REFERENCES subject(id),
                                 semester       VARCHAR        NOT NULL,
                                 enrolled_count INT            NOT NULL,
                                 average_score  DECIMAL(5, 2)  NOT NULL,
                                 standard_dev   DECIMAL(5, 2)  NOT NULL,
                                 ratio_a        DECIMAL(5, 2)  NOT NULL,
                                 ratio_b        DECIMAL(5, 2)  NOT NULL,
                                 ratio_c        DECIMAL(5, 2)  NOT NULL,
                                 created_at     TIMESTAMP      NOT NULL DEFAULT NOW(),
                                 updated_at     TIMESTAMP      NOT NULL DEFAULT NOW(),
                                 is_deleted     BOOLEAN        NOT NULL DEFAULT FALSE
);

CREATE TABLE grade (
                       id                 BIGSERIAL      PRIMARY KEY,
                       student_id         BIGINT         NOT NULL REFERENCES student(id),
                       class_statistic_id BIGINT         NOT NULL REFERENCES class_statistic(id),
                       raw_score          DECIMAL(5, 2)  NOT NULL,
                       achievement_level  VARCHAR(2)     NOT NULL,
                       created_at         TIMESTAMP      NOT NULL DEFAULT NOW(),
                       updated_at         TIMESTAMP      NOT NULL DEFAULT NOW(),
                       is_deleted         BOOLEAN        NOT NULL DEFAULT FALSE
);

CREATE TABLE semester_summary (
                                  id            BIGSERIAL      PRIMARY KEY,
                                  student_id    BIGINT         NOT NULL REFERENCES student(id),
                                  semester      VARCHAR(10)    NOT NULL,
                                  total_credits INT            NOT NULL,
                                  total_score   DECIMAL(5, 2)  NOT NULL,
                                  average_score DECIMAL(5, 2)  NOT NULL,
                                  ranking       INT,
                                  created_at    TIMESTAMP      NOT NULL DEFAULT NOW(),
                                  updated_at    TIMESTAMP      NOT NULL DEFAULT NOW(),
                                  is_deleted    BOOLEAN        NOT NULL DEFAULT FALSE
);
