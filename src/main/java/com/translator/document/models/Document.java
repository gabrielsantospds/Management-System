package com.translator.document.models;

import jakarta.persistence.*;

// The annotation indicates that this Class will be an Entity in the database
@Entity
public class Document {

    // Uses column annotation to specify details when mapping the attributes in the database
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false)
    private Long id;
    @Column(nullable = false)
    private String subject;
    @Column(nullable = false)
    private String content;
    @Column(nullable = false)
    private String locale;

    // Specifies the association between the translator and document classes
    @ManyToOne
    // This will add a translator email column to the document table as a foreign key
    @JoinColumn(
            nullable = false,
            name = "author",
            referencedColumnName = "email"
    )
    private Translator author;

    // Generate getters and setters to access and manipulate the attribute data
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Translator getAuthor() {
        return author;
    }

    public void setAuthor(Translator author) {
        this.author = author;
    }
}
