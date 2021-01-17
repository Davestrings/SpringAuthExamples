package com.example.auth.authentications;

import com.example.auth.authentications.domain.model.Role;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DatabaseInitalizer implements ApplicationListener<ApplicationReadyEvent> {

    private final List<String> usernames = List.of(
            "ada.lovelace@nix.io",
            "alan.turing@nix.io",
            "dennis.ritchie@nix.io",
            "lionel.messi@nix.io"
    );

    private final List<String> fullNames = List.of(
            "Ada Lovelace",
            "Alan Turning",
            "Dennis Ritchie",
            "Lionel Messi"
    );

    private final List<String> roles = List.of(
            Role.USER_ADMIN,
            Role.BOOK_ADMIN,
            Role.AUTHOR_ADMIN
    );

    private final String password = "Testing@123password";

    public DatabaseInitalizer(){

    }
    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {

    }
}
