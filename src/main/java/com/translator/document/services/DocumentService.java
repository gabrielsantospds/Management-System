package com.translator.document.services;

import com.translator.document.models.Document;
import com.translator.document.models.Translator;
import com.translator.document.repositories.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// The annotation indicates that this class is a service component where business rules are implemented
@Service
public class DocumentService {

    // @Autowired is used to automatically inject the dependencies into the class
    @Autowired
    private DocumentRepository documentRepository;

    // The methods below perform query and change operations on the database through the translatorRepository

    public Optional<Document> findDocumentById(Long id) {
        return documentRepository.findById(id);
    }

    public Document saveDocument(Document document) {
        return documentRepository.save(document);
    }

    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }

    public void deleteDocument(Document document) {
        documentRepository.delete(document);
    }
}
