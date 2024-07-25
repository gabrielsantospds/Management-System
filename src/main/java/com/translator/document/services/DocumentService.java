package com.translator.document.services;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.translator.document.dtos.DocumentDTO;
import com.translator.document.dtos.DocumentResponseDTO;
import com.translator.document.models.Document;
import com.translator.document.models.DocumentCsvRepresentation;
import com.translator.document.models.Translator;
import com.translator.document.repositories.DocumentRepository;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
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

    @Autowired
    private AiService aiService;

    // The methods below perform query and change operations on the database through the translatorRepository

    public Optional<Document> findDocumentById(Long id) {
        return documentRepository.findById(id);
    }

    // Uses this annotation to make database operations in an atomic and consistent manner
    @Transactional
    public Integer saveDocument(MultipartFile file) throws IOException {
        // Gets a set of documents read by the csv that have the author saved in the database
        Set<Document> documents = parseCsv(file);
        if(documents == null || documents.isEmpty()) {
            return 0;
        }
        documentRepository.saveAll(documents);
        return documents.size();
    }

    private Set<Document> parseCsv(MultipartFile file) throws IOException {
        // Try read the csv stream data
        try(Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            // Set the names of header columns to follow
            HeaderColumnNameMappingStrategy<DocumentCsvRepresentation> strategy =
                    new HeaderColumnNameMappingStrategy<>();
            strategy.setType(DocumentCsvRepresentation.class);

            // Creates the csv bean with extra configuration
            CsvToBean<DocumentCsvRepresentation> csvToBean =
                    new CsvToBeanBuilder<DocumentCsvRepresentation>(reader)
                            .withMappingStrategy(strategy)
                            .withIgnoreEmptyLine(true)
                            .withIgnoreLeadingWhiteSpace(true)
                            .build();

            // Goes through each line of the file
            return csvToBean.parse()
                    .stream()
                    .map(csvLine -> {
                        // Validates if the translator email exists in the database,
                        // if yes, adds the document to the returned list to be saved
                        Optional<Translator> translatorOptional =
                                translatorService.findTranslatorByEmail(csvLine.getAuthor());
                        if(translatorOptional.isPresent()) {
                            Translator translatorFound = translatorOptional.get();

                            // Generates locale from content
                            String locale = aiService.chatResponse(csvLine.getContent());
                            return new Document(
                                    csvLine.getSubject(),
                                    csvLine.getContent(),
                                    locale,
                                    translatorFound
                            );
                        } else {
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
        }
    }

    @Transactional
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

    public PagedModel<DocumentResponseDTO> getAllDocuments(Pageable pageable) {
        // Fetches data in a paged form and returns the email of the document owner
        return new PagedModel<>(documentRepository.findAll(pageable).map(document -> new DocumentResponseDTO(
                document.getId(),
                document.getSubject(),
                document.getContent(),
                document.getLocale(),
                document.getAuthor().getEmail()
        )));
    }

    public DocumentResponseDTO getCustomDocument(Document document) {
        return new DocumentResponseDTO(
                document.getId(),
                document.getSubject(),
                document.getContent(),
                document.getLocale(),
                document.getAuthor().getEmail()
        );
    }

    @Transactional
    public void deleteDocument(Document document) {
        documentRepository.delete(document);
    }
}
