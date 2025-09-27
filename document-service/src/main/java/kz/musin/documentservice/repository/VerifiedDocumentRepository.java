package kz.musin.documentservice.repository;

import kz.musin.documentservice.model.entity.VerifiedDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VerifiedDocumentRepository extends JpaRepository<VerifiedDocument, UUID> { }
