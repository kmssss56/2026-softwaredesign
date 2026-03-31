-- V7__create_notification.sql

CREATE TABLE notification (
                              id         BIGSERIAL    PRIMARY KEY,
                              user_id    BIGINT       NOT NULL REFERENCES users(id),
                              type       VARCHAR(50)  NOT NULL,
                              title      VARCHAR(200) NOT NULL,
                              content    TEXT,
                              is_read    BOOLEAN      NOT NULL DEFAULT FALSE,
                              created_at TIMESTAMP    NOT NULL DEFAULT NOW()
);

CREATE TABLE notification_setting (
                                      id               BIGSERIAL PRIMARY KEY,
                                      user_id          BIGINT    NOT NULL REFERENCES users(id),
                                      grade_alert      BOOLEAN   NOT NULL DEFAULT TRUE,
                                      feedback_alert   BOOLEAN   NOT NULL DEFAULT TRUE,
                                      counsel_alert    BOOLEAN   NOT NULL DEFAULT TRUE,
                                      email_enabled    BOOLEAN   NOT NULL DEFAULT TRUE,
                                      push_enabled     BOOLEAN   NOT NULL DEFAULT TRUE,
                                      updated_at       TIMESTAMP NOT NULL DEFAULT NOW()
);
