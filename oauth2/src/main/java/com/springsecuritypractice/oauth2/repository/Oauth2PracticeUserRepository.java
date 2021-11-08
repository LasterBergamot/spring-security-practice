package com.springsecuritypractice.oauth2.repository;

import com.springsecuritypractice.oauth2.model.Oauth2PracticeUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Oauth2PracticeUserRepository extends JpaRepository<Oauth2PracticeUser, Long> {
}
