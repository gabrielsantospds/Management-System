package com.translator.document.dtos;

public record DocumentResponseDTO(

        Long id,
        String subject,
        String content,
        String locale,
        String author
) {
}
