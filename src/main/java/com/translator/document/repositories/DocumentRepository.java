package com.translator.document.repositories;

import com.translator.document.models.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// The annotation indicates that this is a Spring Data Repository
@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    // By extending JpaRepository, DocumentRepository inherits several methods for working with Document persistence
}
