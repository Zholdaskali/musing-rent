package kz.musin.documentservice.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "t_verified_documents")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VerifiedDocument {

    @Id
    private UUID id;

    @Column(name = "auth_id", nullable = false)
    private UUID authId;

    @Column(nullable = false, unique = true, length = 12)
    private String iin;

    @Column(name = "full_name", nullable = false, length = 200)
    private String fullName;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "document_number", nullable = false, unique = true, length = 20)
    private String documentNumber;

    @Column(name = "issue_date", nullable = false)
    private LocalDate issueDate;

    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;

    @Column(name = "issuing_authority", length = 200)
    private String issuingAuthority;

    @Column(name = "verification_status", nullable = false, length = 20)
    private String verificationStatus;

    @Column(name = "verification_date")
    private LocalDateTime verificationDate;

    @Column(name = "face_match_score", precision = 5, scale = 4)
    private BigDecimal faceMatchScore;

    @Column(name = "ocr_confidence", precision = 5, scale = 4)
    private BigDecimal ocrConfidence;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}

