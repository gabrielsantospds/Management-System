package com.translator.document.controllers;

import com.translator.document.dtos.TranslatorDTO;
import com.translator.document.models.Translator;
import com.translator.document.services.TranslatorService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


// The annotation is used to create a RESTFul controller
@RestController
public class TranslatorController {

    // @Autowired is used to automatically inject the dependencies into the class
    @Autowired
    private TranslatorService translatorService;

    @PostMapping("/translator")
    public ResponseEntity<Translator> saveTranslator(@RequestBody TranslatorDTO translatorDTO) {
        // Creates a new translator and copies the properties of the translatorDTO to save it in the database
        Translator translator = new Translator();
        BeanUtils.copyProperties(translatorDTO, translator);
        return ResponseEntity.status(HttpStatus.CREATED).body(translatorService.saveTranslator(translator));
    }

    @GetMapping("/translators")
    public ResponseEntity<List<Translator>> getAllTranslators() {
        return ResponseEntity.status(HttpStatus.OK).body(translatorService.getAllTranslators());
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

        // Creates a new translator and copies the properties of the translatorDTO to save and update it in the database
        Translator translator = new Translator();
        BeanUtils.copyProperties(translatorDTO, translator);
        Translator translatorFound = optionalTranslator.get();
        // Keeps the same id that was defined in the object
        translator.setId(translatorFound.getId());
        return ResponseEntity.status(HttpStatus.OK).body(translatorService.saveTranslator(translator));
    }

    @DeleteMapping("/translator/{id}")
    public ResponseEntity<String> deleteTranslator(@PathVariable Long id) {

        // Checks if the translator exists by the provided id
        Optional<Translator> optionalTranslator = translatorService.findTranslatorById(id);

        if(optionalTranslator.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Translator not found");
        }

        Translator translatorFound = optionalTranslator.get();
        translatorService.deleteTranslator(translatorFound);

        return ResponseEntity.status(HttpStatus.OK).body("Translator deleted successfully");
    }
}
