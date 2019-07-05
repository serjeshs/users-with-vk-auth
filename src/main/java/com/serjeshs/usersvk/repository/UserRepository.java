package com.serjeshs.usersvk.repository;

import com.serjeshs.usersvk.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByName(String name);

}
