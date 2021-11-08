package com.springsecuritypractice.oauth2.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "oauth2_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Oauth2PracticeUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "email", nullable = false)
    String email;

    @Column(name = "unique_identifier", nullable = false)
    String uniqueIdentifier;

    @Enumerated(EnumType.STRING)
    @Column(name = "resource_server", nullable = false)
    ResourceServer resourceServer;
}
