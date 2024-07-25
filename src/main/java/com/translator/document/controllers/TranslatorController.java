package com.translator.document.controllers;

import com.translator.document.dtos.TranslatorDTO;
import com.translator.document.models.Translator;
import com.translator.document.services.TranslatorService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;


// The annotation is used to create a RESTFul controller
@RestController
// Set CORS to allows request from the frontend application
@CrossOrigin(origins = "https://management-system-frontend-h23o.onrender.com")
public class TranslatorController {

    // @Autowired is used to automatically inject the dependencies into the class
    @Autowired
    private TranslatorService translatorService;

    @PostMapping("/translator")
    public ResponseEntity<Object> saveTranslator(@RequestBody TranslatorDTO translatorDTO) {

        // Checks if the email already exists in the database
        Optional<Translator> optionalTranslator = translatorService.findTranslatorByEmail(translatorDTO.email());
        if (optionalTranslator.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    translatorDTO.email() + " has already been registered"
            );
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(translatorService.saveTranslator(translatorDTO));
    }

    @GetMapping("/translators")
    public ResponseEntity<PagedModel<Translator>> getAllTranslators(
            @RequestParam int page,
            @PageableDefault(size = 5) Pageable pageable
    ) {
        pageable.withPage(page);
        // Gets a Pageable object with the page number and page size
        return ResponseEntity.status(HttpStatus.OK).body(translatorService.getAllTranslators(pageable));
    }

    @GetMapping("/translator/{id}")
    public ResponseEntity<Object> getOneTranslator(@PathVariable Long id) {
        // Checks if the translator exists by the provided id
        Optional<Translator> optionalTranslator = translatorService.getOneTranslator(id);
        if(optionalTranslator.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Translator not found");
        }
        Translator translatorFound = optionalTranslator.get();

        return ResponseEntity.status(HttpStatus.OK).body(translatorFound);
    }

    @PutMapping("/translator/{id}")
    public ResponseEntity<Object> updateTranslator(
            @PathVariable Long id,
            @RequestBody TranslatorDTO translatorDTO
    ) {
        // Checks if the translator exists by the provided id
        Optional<Translator> optionalTranslator = translatorService.findTranslatorById(id);

        if(optionalTranslator.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Translator not found");
        }

        return ResponseEntity.status(HttpStatus.OK).body(translatorService.updateTranslator(translatorDTO, id));
    }

    @DeleteMapping("/translator/{id}")
    public ResponseEntity<String> deleteTranslator(@PathVariable Long id) {

        try {
            // Checks if the translator exists by the provided id
            Optional<Translator> optionalTranslator = translatorService.findTranslatorById(id);

            if(optionalTranslator.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Translator not found");
            }

            Translator translatorFound = optionalTranslator.get();
            translatorService.deleteTranslator(translatorFound);

        } catch (Exception e) {
            // if the Exception is captured returns a conflict response
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    "It was no possible delete such translator because it is linked to documents");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Translator deleted successfully");
    }
}
