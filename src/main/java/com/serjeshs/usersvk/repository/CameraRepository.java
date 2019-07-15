package com.serjeshs.usersvk.repository;

import com.serjeshs.usersvk.domain.Camera;
import com.serjeshs.usersvk.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CameraRepository extends JpaRepository<Camera, Integer> {

//    Optional<Camera> findBy(String name);

}
