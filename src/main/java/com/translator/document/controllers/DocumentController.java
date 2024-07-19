package com.translator.document.controllers;

import com.translator.document.models.Document;
import com.translator.document.models.Translator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// The annotation is used to create a RESTFul controller
// The methods return data directly in the HTTP response body
@RestController
public class DocumentController {

    // Handles HTTP GET requests to /documents
    @GetMapping("/document")
    public ResponseEntity<Document> getAllDocuments() {
        // Returns 200 code and a list of content of all documents
        return ResponseEntity.status(HttpStatus.OK).body(new Document());
    }

    // Handles HTTP POST requests to /documents
    @PostMapping("/document")
    public ResponseEntity<String> saveDocument() {
        // Returns 201 code and the confirmation message that the document has been saved
        return ResponseEntity.status(HttpStatus.CREATED).body("Document saved");
    }

    // Handles HTTP PUT requests to /document/documentId
    @PutMapping("/document/{id}")
    public ResponseEntity<Document> updateDocument(@PathVariable Long id) {
        // Returns 200 code and updated document content
        return ResponseEntity.status(HttpStatus.OK).body(new Document());
    }

    // Handles HTTP DELETE requests to /document/documentId
    @DeleteMapping("/document/{id}")
    public ResponseEntity<String> deleteDocument(@PathVariable Long id) {
        //Returns 200 code and the confirmation message that the document has been deleted
        return ResponseEntity.status(HttpStatus.OK).body("Document deleted successfully");
    }
}
