package kz.musin.documentservice.repository;

import kz.musin.documentservice.model.entity.SuspiciousDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SuspiciousDocumentRepository extends JpaRepository<SuspiciousDocument, UUID> {
}
