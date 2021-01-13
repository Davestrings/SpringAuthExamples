package com.example.auth.authentications.domain.dto;

import lombok.Data;

@Data
public class SearchUsersQuery {
    private String id;
    private String username;
    private String fullname;
}
