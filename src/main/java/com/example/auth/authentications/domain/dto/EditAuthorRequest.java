package com.example.auth.authentications.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class EditAuthorRequest {
    @NotBlank
    private String fullName;
    private String about;
    private String nationality;
    private List<String> genres;
}
