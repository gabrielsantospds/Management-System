package com.translator.document.services;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.translator.document.dtos.DocumentDTO;
import com.translator.document.models.Document;
import com.translator.document.models.DocumentCsvRepresentation;
import com.translator.document.models.Translator;
import com.translator.document.repositories.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

// The annotation indicates that this class is a service component where business rules are implemented
@Service
public class DocumentService {

    // @Autowired is used to automatically inject the dependencies into the class
    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private TranslatorService translatorService;

    // The methods below perform query and change operations on the database through the translatorRepository

    public Optional<Document> findDocumentById(Long id) {
        return documentRepository.findById(id);
    }

//    public Document saveDocument(DocumentDTO documentDTO, Translator translator) {
//
//        Document document = new Document();
//        document.setSubject(documentDTO.subject());
//        document.setContent(documentDTO.content());
//        document.setLocale(documentDTO.locale());
//        document.setAuthor(translator);
//
//        return documentRepository.save(document);
//    }

    public Integer saveDocument(MultipartFile file) throws IOException {
        Set<Document> documents = parseCsv(file);
        if(documents == null) {
            return 0;
        }
        documentRepository.saveAll(documents);
        return documents.size();
    }

    private Set<Document> parseCsv(MultipartFile file) throws IOException {
        try(Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            HeaderColumnNameMappingStrategy<DocumentCsvRepresentation> strategy =
                    new HeaderColumnNameMappingStrategy<>();
            strategy.setType(DocumentCsvRepresentation.class);
            CsvToBean<DocumentCsvRepresentation> csvToBean =
                    new CsvToBeanBuilder<DocumentCsvRepresentation>(reader)
                            .withMappingStrategy(strategy)
                            .withIgnoreEmptyLine(true)
                            .withIgnoreLeadingWhiteSpace(true)
                            .build();
            return csvToBean.parse()
                    .stream()
                    .map(csvLine -> {
                        Optional<Translator> translatorOptional =
                                translatorService.findTranslatorByEmail(csvLine.getAuthor());
                        if(translatorOptional.isPresent()) {
                            Translator translatorFound = translatorOptional.get();
                            return new Document(
                                    csvLine.getSubject(),
                                    csvLine.getContent(),
                                    csvLine.getLocale(),
                                    translatorFound
                            );
                        }
                        return null;
                    })
                    .collect(Collectors.toSet());
        }
    }

    public Document updateDocument(DocumentDTO documentDTO, Translator translator, Long documentId) {
        Document document = new Document();
        // Keeps the same id that was defined in the object
        document.setId(documentId);
        document.setSubject(documentDTO.subject());
        document.setContent(documentDTO.content());
        document.setLocale(documentDTO.locale());
        document.setAuthor(translator);
        return documentRepository.save(document);
    }

    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }

    public void deleteDocument(Document document) {
        documentRepository.delete(document);
    }
}
