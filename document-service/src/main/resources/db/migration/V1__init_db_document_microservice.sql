-- init Database udostoverenie-service (Микросервиса) --

-- Основная таблица верифицированных документов
CREATE TABLE t_verified_documents (
                                      id                  UUID            NOT NULL,
                                      auth_id             UUID            NOT NULL,                   -- auth_id приходит из Auth-service
                                      iin                 VARCHAR(12)     NOT NULL    UNIQUE,         -- ИИН пользователя
                                      full_name           VARCHAR(200)    NOT NULL,                   -- ФИО из документа
                                      birth_date          DATE            NOT NULL,                   -- Дата рождения
                                      document_number     VARCHAR(20)     NOT NULL    UNIQUE,         -- Номер удостоверения
                                      issue_date          DATE            NOT NULL,                   -- Дата выдачи
                                      expiry_date         DATE            NOT NULL,                   -- Срок действия
                                      issuing_authority   VARCHAR(200),                               -- Орган выдавший
                                      verification_status VARCHAR(20)     NOT NULL    DEFAULT 'PENDING', -- PENDING, VERIFIED, REJECTED, EXPIRED
                                      verification_date   TIMESTAMP,                                  -- Дата верификации
                                      face_match_score    DECIMAL(5,4),                               -- Схожесть лица с селфи (0.0000 - 1.0000)
                                      ocr_confidence      DECIMAL(5,4),                               -- Уверенность OCR (0.0000 - 1.0000)
                                      created_at          TIMESTAMP       NOT NULL    DEFAULT NOW(),
                                      updated_at          TIMESTAMP       NOT NULL    DEFAULT NOW(),
                                      PRIMARY KEY (id)
);

-- Таблица для логирования попыток верификации
CREATE TABLE t_verification_attempts (
                                         id                  UUID            NOT NULL,
                                         auth_id             UUID            NOT NULL,                   -- auth_id пользователя
                                         verification_type   VARCHAR(50)     NOT NULL,                   -- OCR, FACE_MATCH, DOCUMENT_VALIDATION, FULL_VERIFICATION
                                         attempt_status      VARCHAR(20)     NOT NULL,                   -- SUCCESS, FAILURE, ERROR
                                         error_code          VARCHAR(50),                                -- Код ошибки если есть
                                         error_message       TEXT,                                       -- Подробное описание ошибки
                                         processing_time_ms  INTEGER,                                    -- Время обработки в миллисекундах
                                         confidence_score    DECIMAL(5,4),                               -- Общий скор уверенности
                                         ip_address          INET,                                       -- IP адрес пользователя
                                         created_at          TIMESTAMP       NOT NULL    DEFAULT NOW(),
                                         PRIMARY KEY (id)
);

-- Таблица для блэклиста подозрительных документов
CREATE TABLE t_suspicious_documents (
                                        id                  UUID            NOT NULL,
                                        document_number     VARCHAR(20),                                -- Номер подозрительного документа
                                        iin                 VARCHAR(12),                                -- ИИН если известен
                                        reason_code         VARCHAR(50)     NOT NULL,                   -- FAKE_DOCUMENT, EXPIRED, REPORTED_STOLEN, DUPLICATE_ATTEMPT
                                        reason_description  TEXT,                                       -- Подробное описание
                                        reported_by         VARCHAR(100),                               -- Кто сообщил (система, админ, пользователь)
                                        severity_level      VARCHAR(20)     DEFAULT 'MEDIUM',           -- LOW, MEDIUM, HIGH, CRITICAL
                                        is_active           BOOLEAN         DEFAULT TRUE,               -- Активен ли блок
                                        blocked_until       TIMESTAMP,                                  -- До какого времени заблокирован
                                        created_at          TIMESTAMP       NOT NULL    DEFAULT NOW(),
                                        updated_at          TIMESTAMP       NOT NULL    DEFAULT NOW(),
                                        PRIMARY KEY (id)
);

-- Таблица для временного хранения данных во время обработки
CREATE TABLE t_document_processing (
                                       id                      UUID            NOT NULL,
                                       auth_id                 UUID            NOT NULL,               -- auth_id пользователя
                                       processing_status       VARCHAR(20)     NOT NULL DEFAULT 'PENDING', -- PENDING, PROCESSING, COMPLETED, FAILED
                                       extracted_data          JSONB,                                  -- Временные OCR данные в JSON
                                       face_comparison_result  JSONB,                                  -- Результат сравнения лиц
                                       file_metadata           JSONB,                                  -- Метаданные файла (размер, тип)
                                       expires_at              TIMESTAMP       NOT NULL,                -- Время истечения (автоудаление через 24 часа)
                                       created_at              TIMESTAMP       NOT NULL DEFAULT NOW(),
                                       PRIMARY KEY (id)
);