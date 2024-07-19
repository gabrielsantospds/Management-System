package com.translator.document.models;

import jakarta.persistence.*;

@Entity
public class Translator {

    // Uses column annotation to specify details when mapping the attributes in the database
    @Id
    // The id attribute will be generated automatically
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String source_language;
    @Column(nullable = false)
    private String target_language;

    // Generate getters and setters to access and manipulate the attribute data
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSource_language() {
        return source_language;
    }

    public void setSource_language(String source_language) {
        this.source_language = source_language;
    }

    public String getTarget_language() {
        return target_language;
    }

    public void setTarget_language(String target_language) {
        this.target_language = target_language;
    }
}
