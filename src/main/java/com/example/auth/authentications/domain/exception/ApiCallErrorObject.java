package com.example.auth.authentications.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiCallErrorObject<T> {

    private String message;
    private List<T> details;
    private Instant createdAt;

    public <E> ApiCallErrorObject(String message, List<E> details) {
        this.message = message;
        this.details = (List<T>) details;
        createdAt = Instant.now();
    }
}
