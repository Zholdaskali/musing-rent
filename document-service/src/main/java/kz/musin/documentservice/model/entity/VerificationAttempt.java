package kz.musin.documentservice.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "t_verification_attempts")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VerificationAttempt {

    @Id
    private UUID id;

    @Column(name = "auth_id", nullable = false)
    private UUID authId;

    @Column(name = "verification_type", nullable = false, length = 50)
    private String verificationType;

    @Column(name = "attempt_status", nullable = false, length = 20)
    private String attemptStatus;

    @Column(name = "error_code", length = 50)
    private String errorCode;

    @Column(name = "error_message", columnDefinition = "TEXT")
    private String errorMessage;

    @Column(name = "processing_time_ms")
    private Integer processingTimeMs;

    @Column(name = "confidence_score", precision = 5, scale = 4)
    private BigDecimal confidenceScore;

    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}