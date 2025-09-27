package kz.musin.documentservice.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "t_suspicious_documents")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SuspiciousDocument {

    @Id
    private UUID id;

    @Column(name = "document_number", length = 20)
    private String documentNumber;

    @Column(length = 12)
    private String iin;

    @Column(name = "reason_code", nullable = false, length = 50)
    private String reasonCode;

    @Column(name = "reason_description", columnDefinition = "TEXT")
    private String reasonDescription;

    @Column(name = "reported_by", length = 100)
    private String reportedBy;

    @Column(name = "severity_level", length = 20)
    private String severityLevel;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "blocked_until")
    private LocalDateTime blockedUntil;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}