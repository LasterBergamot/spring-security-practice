package com.springsecuritypractice.jwt.service;

import com.springsecuritypractice.jwt.model.User;
import com.springsecuritypractice.jwt.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class StartupService {

    private final UserRepository repository;

    private final PasswordEncoder bcryptEncoder;

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() {
        List<User> allUsers = repository.findAll();
        log.info("Number of users: {}", allUsers.size());

        String admin = "admin";
        User newUser = User.builder()
                .username(admin)
                .password(bcryptEncoder.encode(admin))
                .build();
        log.info("Saving new user...");
        repository.save(newUser);

        allUsers = repository.findAll();
        log.info("Number of users: {}", allUsers.size());
    }
}
