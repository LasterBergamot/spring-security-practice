package com.springsecuritypractice.jwt.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserCreateDto {
    String username;
    String password;
}
