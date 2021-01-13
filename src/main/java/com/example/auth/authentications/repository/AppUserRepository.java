package com.example.auth.authentications.repository;

import com.example.auth.authentications.domain.model.AppUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends MongoRepository<AppUser, String> {
    AppUser findByUsername(String username);
}
