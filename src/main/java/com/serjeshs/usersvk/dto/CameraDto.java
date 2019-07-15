package com.serjeshs.usersvk.dto;

import com.serjeshs.usersvk.domain.Camera;
import com.serjeshs.usersvk.domain.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CameraDto {
    private String model;
    private String description;
    private String location;
    private String date;

    public CameraDto(String model, String description, String location, String date) {
        this.model = model;
        this.description = description;
        this.location = location;
        this.date = date;
    }

    public static CameraDto toDto(Camera camera){
        return new CameraDto(
                camera.getModel(),
                camera.getDescription(),
                camera.getLocation(),
                camera.getDate()
        );
    }
}
