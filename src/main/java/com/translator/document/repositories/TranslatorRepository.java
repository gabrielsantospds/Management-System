package com.translator.document.repositories;

import com.translator.document.models.Translator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// The annotation indicates that this is a Spring Data Repository
@Repository
public interface TranslatorRepository extends JpaRepository<Translator, Long> {

    // By extending JpaRepository, TranslatorRepository inherits several methods for working with Document persistence

    Optional<Translator> findByEmail(String email);
}
