package com.translator.document.dtos;

// Creates Record DTO to separate the presentation layer from the domain layer ensuring data integrity
public record TranslatorDTO(

        String name,
        String email,
        String source_language,
        String target_language
) {
}
