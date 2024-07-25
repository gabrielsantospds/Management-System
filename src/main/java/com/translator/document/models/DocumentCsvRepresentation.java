package com.translator.document.models;

import com.opencsv.bean.CsvBindByName;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DocumentCsvRepresentation {

    @CsvBindByName(column = "subject")
    private String subject;
    @CsvBindByName(column = "content")
    private String content;
    @CsvBindByName(column = "author")
    private String author;

}
