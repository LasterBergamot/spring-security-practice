package com.springsecuritypractice.jwt.service;

import com.springsecuritypractice.jwt.converter.UserConverter;
import com.springsecuritypractice.jwt.dto.UserCreateDto;
import com.springsecuritypractice.jwt.dto.UserDto;
import com.springsecuritypractice.jwt.model.User;
import com.springsecuritypractice.jwt.repository.UserRepository;
import java.util.ArrayList;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    private final UserConverter converter;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return Optional.ofNullable(repository.findByUsername(username))
                .map(user -> new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>()))
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User not found with the following username: %s", username)));
    }

    public UserDto save(UserCreateDto dto) {
        User transientUser = converter.toEntity(dto);
        User persistedUser = repository.save(transientUser);

        return converter.toDto(persistedUser);
    }
}
