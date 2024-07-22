package com.translator.document.services;

import com.translator.document.dtos.TranslatorDTO;
import com.translator.document.models.Translator;
import com.translator.document.repositories.TranslatorRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// The annotation indicates that this class is a service component where business rules are implemented if there is
@Service
public class TranslatorService {

    // @Autowired is used to automatically inject the dependencies into the class
    @Autowired
    private TranslatorRepository translatorRepository;

    // The methods below perform query and change operations on the database through the translatorRepository

    public Optional<Translator> findTranslatorByEmail(String email) {
        return translatorRepository.findByEmail(email);
    }

    public Optional<Translator> findTranslatorById(Long id) {
        return translatorRepository.findById(id);
    }

    public Translator saveTranslator(TranslatorDTO translatorDTO) {
        // Creates a new translator and copies the properties of the translatorDTO to save it in the database
        Translator translator = new Translator();
        BeanUtils.copyProperties(translatorDTO, translator);
        return translatorRepository.save(translator);
    }

    public Translator updateTranslator(TranslatorDTO translatorDTO, Long translatorId) {
        // Creates a new translator and copies the properties of the translatorDTO to save and update it in the database
        Translator translator = new Translator();
        BeanUtils.copyProperties(translatorDTO, translator);
        // Keeps the same id that was defined in the object
        translator.setId(translatorId);
        return translatorRepository.save(translator);
    }

    public List<Translator> getAllTranslators() {
        return translatorRepository.findAll();
    }

    public void deleteTranslator(Translator translator) {
        translatorRepository.delete(translator);
    }
}
