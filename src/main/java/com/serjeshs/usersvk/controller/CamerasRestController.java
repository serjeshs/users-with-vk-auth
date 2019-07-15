package com.serjeshs.usersvk.controller;

import com.serjeshs.usersvk.domain.Camera;
import com.serjeshs.usersvk.dto.CameraDto;
import com.serjeshs.usersvk.repository.CameraRepository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CamerasRestController {

    private final CameraRepository cameraRepository;

    public CamerasRestController(CameraRepository cameraRepository) {
        this.cameraRepository = cameraRepository;
    }

    @GetMapping("/api/cameras")
    public List<CameraDto> listCameras(Model model){
        List<Camera> cameras = cameraRepository.findAll();
        return cameras.stream().map(CameraDto::toDto).collect(Collectors.toList());
    }

}
