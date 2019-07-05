package com.serjeshs.usersvk.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_USER = "USER";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "NUMERIC", length = 20)
    private Long id;

    @Column(name="name", unique = true, nullable = false, updatable = false)
    private String name;

    @Column(name="description")
    private String description;

    @Column(name="password")
    private String password;

    @Column(name = "role")
    private String role;

    public User( String name, String description, String password, String role) {
        this.name = name;
        this.description = description;
        this.password = password;
        this.role = role;
    }

}
