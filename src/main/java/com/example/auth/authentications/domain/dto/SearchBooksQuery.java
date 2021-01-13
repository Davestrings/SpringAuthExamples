package com.example.auth.authentications.domain.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
public class SearchBooksQuery {
    private String id;

    private String creatorId;
    private LocalDateTime createdAtStart;
    private LocalDateTime createdAtEnd;

    private String title;
    private Set<String> genres = new HashSet<>();
    private String isbn13;
    private String publisher;
    private String isbn10;
    private LocalDateTime publishDateStart;
    private LocalDateTime publishDateEnd;

    private String authorId;
    private String authorFullName;
}
