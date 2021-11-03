package com.springsecuritypractice.jwt.converter;

import com.springsecuritypractice.jwt.dto.UserCreateDto;
import com.springsecuritypractice.jwt.dto.UserDto;
import com.springsecuritypractice.jwt.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserConverter {

    private final PasswordEncoder bcryptEncoder;

    public User toEntity(UserCreateDto dto) {
        return User.builder()
                .username(dto.getUsername())
                .password(bcryptEncoder.encode(dto.getPassword()))
                .build();
    }

    public UserDto toDto(User user) {
        return UserDto.builder()
                .username(user.getUsername())
                .build();
    }
}
