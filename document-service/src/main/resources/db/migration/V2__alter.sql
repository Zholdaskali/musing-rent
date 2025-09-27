ALTER TABLE t_verification_attempts
ALTER COLUMN ip_address TYPE VARCHAR(45)
    USING ip_address::TEXT;
