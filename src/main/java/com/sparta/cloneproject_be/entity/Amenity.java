package com.sparta.cloneproject_be.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Amenity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "amenityId")
    private Long id;

    private String amenityName;

    @OneToMany
    List<RoomAmenities> roomAmenities = new ArrayList<>();
}
