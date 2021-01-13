package com.example.auth.authentications.domain.model;


import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Document(collection = "books")
@Data
public class Book implements Serializable {

    @Id
    private ObjectId id;

    @CreatedBy
    private ObjectId creatorId;

    @LastModifiedBy
    private ObjectId modifierId;

    @LastModifiedDate
    private LocalDateTime modifiedAt;
    @CreatedDate
    private LocalDateTime createdAt;

    private String title;
    private String about;
    private String language;
    private Set<String> genres = new HashSet<>();
    private String isbn10;
    private String isbn13;
    private  String publisher;
    private LocalDateTime publishedDate;

    private  Set<ObjectId> authorId = new HashSet<>();
}
