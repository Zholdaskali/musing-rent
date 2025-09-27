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
@Table(name = "t_document_processing")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentProcessing {

    @Id
    private UUID id;

    @Column(name = "auth_id", nullable = false)
    private UUID authId;

    @Column(name = "processing_status", nullable = false, length = 20)
    private String processingStatus;

    @Column(name = "extracted_data", columnDefinition = "JSONB")
    private String extractedData;

    @Column(name = "face_comparison_result", columnDefinition = "JSONB")
    private String faceComparisonResult;

    @Column(name = "file_metadata", columnDefinition = "JSONB")
    private String fileMetadata;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
