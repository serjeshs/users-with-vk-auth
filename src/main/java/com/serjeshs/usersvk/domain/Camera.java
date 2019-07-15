package com.serjeshs.usersvk.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "cameras")
public class Camera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "NUMERIC", length = 20)
    private Long id;

    @Column(name="model", nullable = false)
    private String model;

    @Column(name="description")
    private String description;

    @Column(name="location")
    private String location;

    @Column(name = "date")
    private String date;

    public Camera(String model, String description, String location, String date) {
        this.model = model;
        this.description = description;
        this.location = location;
        this.date = date;
    }
}
