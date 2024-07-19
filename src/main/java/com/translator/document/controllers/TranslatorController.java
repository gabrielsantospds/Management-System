package com.translator.document.controllers;

import com.translator.document.models.Translator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


// The annotation is used to create a RESTFul controller
// The methods return data directly in the HTTP response body
@RestController
public class TranslatorController {

    // Handles HTTP GET requests to /translators
    @GetMapping("/translators")
    public ResponseEntity<Translator> getAllTranslators() {
        // Returns 200 code and a list of translators
        return ResponseEntity.status(HttpStatus.OK).body(new Translator());
    }

    // Handles HTTP POST requests to /translators
    @PostMapping("/translator")
    public ResponseEntity<String> saveTranslator() {
        // Returns 201 code and the confirmation message that the translator has been saved
        return ResponseEntity.status(HttpStatus.CREATED).body("Translator saved");
    }

    // Handles HTTP PUT requests to /translator/translatorId
    @PutMapping("/translator/{id}")
    public ResponseEntity<Translator> updateTranslator(@PathVariable Long id) {
        // Returns 200 code and updated translator data
        return ResponseEntity.status(HttpStatus.OK).body(new Translator());
    }

    // Handles HTTP DELETE requests to /translator/translatorId
    @DeleteMapping("/translator/{id}")
    public ResponseEntity<String> deleteTranslator(@PathVariable Long id) {
        //Returns 200 code and the confirmation message that the translator has been deleted
        return ResponseEntity.status(HttpStatus.OK).body("Translator deleted successfully");
    }
}
