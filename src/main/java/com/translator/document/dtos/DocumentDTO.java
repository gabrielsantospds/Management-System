package com.translator.document.dtos;

import jakarta.validation.constraints.NotNull;

// Create Record DTO to separate the presentation layer from the domain layer ensuring data integrity
public record DocumentDTO(

        // Uses constraint annotations to validate the data format provided by the request body
        @NotNull String subject,
        @NotNull String content,
        @NotNull String author
) {
}
