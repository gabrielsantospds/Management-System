package com.translator.document.dtos;

// Creates Record DTO to separate the presentation layer from the domain layer ensuring data integrity
public record DocumentDTO(

        String subject,
        String content,
        String locale,
        String author
) {
}
