package com.springsecuritypractice.jwt.model;

import java.io.Serializable;
import lombok.Value;

@Value
public class JwtResponse implements Serializable {
    String jwtToken;
}
