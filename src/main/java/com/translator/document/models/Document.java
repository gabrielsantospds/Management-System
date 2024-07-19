package com.translator.document.models;

import jakarta.persistence.*;

// The annotation indicates that this Class will be an Entity in the database
@Entity
public class Document {

    // Uses column annotation to specify details when mapping the attributes in the database
    @Id
    // The id attribute will be generated automatically
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false)
    private Long id;
    @Column(nullable = false)
    private String subject;
    @Column(nullable = false)
    private String content;
    @Column(nullable = false)
    private String locale;
    @Column(nullable = false)
    private String author;

    // Generate getters and setters to access and manipulate the attribute data
    public Long getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
