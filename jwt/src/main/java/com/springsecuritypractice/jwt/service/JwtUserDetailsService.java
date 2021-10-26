package com.springsecuritypractice.jwt.service;

import java.util.ArrayList;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String hardCodedUsername = "username";

        if (hardCodedUsername.equals(username)) {
            return new User(hardCodedUsername, "$2a$10$nA1HW0ZKGXQ1wZIEDa2lNOl1tyYzocS2Jenalq48UDBha1qR6wY7W", new ArrayList<>());
        } else {
            throw new UsernameNotFoundException(String.format("User not found with the following username: %s", username));
        }
    }
}
