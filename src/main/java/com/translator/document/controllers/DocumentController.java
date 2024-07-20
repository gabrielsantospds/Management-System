package com.translator.document.controllers;

import com.translator.document.dtos.DocumentDTO;
import com.translator.document.models.Document;
import com.translator.document.models.Translator;
import com.translator.document.services.DocumentService;
import com.translator.document.services.TranslatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// The annotation is used to create a RESTFul controller
@RestController
public class DocumentController {

    // @Autowired is used to automatically inject the dependencies into the class
    @Autowired
    private TranslatorService translatorService;

    @Autowired
    private DocumentService documentService;

    @PostMapping("/document")
    public ResponseEntity<Object> saveDocument(@RequestBody DocumentDTO documentDTO) {
        // Extracts the DocumentDTO translator's email provided in the request body
        String translatorEmail = documentDTO.author();
        // Checks if the document exists by the provided email
        Optional<Translator> translatorOptional = translatorService.findTranslatorByEmail(translatorEmail);

        if(translatorOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        "There is no translator in the database with " + translatorEmail
                    );
        }

        Translator translatorFound = translatorOptional.get();

        Document document = new Document();
        document.setSubject(documentDTO.subject());
        document.setContent(documentDTO.content());
        document.setLocale(documentDTO.locale());
        document.setAuthor(translatorFound);

        return ResponseEntity.status(HttpStatus.CREATED).body(documentService.saveDocument(document));
    }

    @GetMapping("/documents")
    public ResponseEntity<List<Document>> getAllDocuments() {
        return ResponseEntity.status(HttpStatus.OK).body(documentService.getAllDocuments());
    }

    @PutMapping("/document/{id}")
    public ResponseEntity<Object> updateDocument(
            @PathVariable Long id,
            @RequestBody DocumentDTO documentDTO
    ) {
        // Checks if the document exists by the provided id
        Optional<Document> optionalDocument = documentService.findDocumentById(id);
        if(optionalDocument.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Document not found");
        }

        // Extracts the DocumentDTO translator's email provided in the request body
        String translatorEmail = documentDTO.author();
        // Checks if the document exists by the provided email
        Optional<Translator> translatorOptional = translatorService.findTranslatorByEmail(translatorEmail);

        if(translatorOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    "There is no translator in the database with " + translatorEmail
            );
        }

        Translator translatorFound = translatorOptional.get();
        Document document = new Document();
        // Keeps the same id that was defined in the object
        document.setId(translatorFound.getId());
        document.setSubject(documentDTO.subject());
        document.setContent(documentDTO.content());
        document.setLocale(documentDTO.locale());
        document.setAuthor(translatorFound);

        return ResponseEntity.status(HttpStatus.OK).body(documentService.saveDocument(document));
    }

    @DeleteMapping("/document/{id}")
    public ResponseEntity<String> deleteDocument(@PathVariable Long id) {

        // Checks if the documents exists by the provided id
        Optional<Document> optionalDocument = documentService.findDocumentById(id);

        if(optionalDocument.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Document not found");
        }

        Document documentFound = optionalDocument.get();
        documentService.deleteDocument(documentFound);

        return ResponseEntity.status(HttpStatus.OK).body("Document deleted successfully");
    }
}
