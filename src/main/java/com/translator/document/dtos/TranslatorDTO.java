package com.translator.document.dtos;
import jakarta.validation.constraints.*;

// Create Record DTO to separate the presentation layer from the domain layer ensuring data integrity
public record TranslatorDTO(

        // Uses constraint annotations to validate the data format provided by the request body
        @NotNull String name,
        @NotNull @Email String email,
        @NotNull String source_language,
        @NotNull String target_language
) {
}
