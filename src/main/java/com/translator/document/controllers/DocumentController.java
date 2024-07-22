package com.translator.document.controllers;

import com.translator.document.dtos.DocumentDTO;
import com.translator.document.models.Document;
import com.translator.document.models.Translator;
import com.translator.document.services.DocumentService;
import com.translator.document.services.TranslatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @PostMapping(value = "/document", consumes = {"multipart/form-data"})
    public ResponseEntity<Object> saveDocument(@RequestPart("file")MultipartFile file) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(documentService.saveDocument(file));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There was a problem reading the file");
        }

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

        String translatorEmail = documentDTO.author();
        // Checks if the document exists by the provided email
        Optional<Translator> translatorOptional = translatorService.findTranslatorByEmail(translatorEmail);

        if(translatorOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    "There is no translator in the database with " + translatorEmail
            );
        }

        Translator translatorFound = translatorOptional.get();

        return ResponseEntity.status(HttpStatus.OK).body(documentService.updateDocument(documentDTO, translatorFound, id));
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
