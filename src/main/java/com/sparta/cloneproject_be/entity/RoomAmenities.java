package com.sparta.cloneproject_be.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomAmenities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "raId")
    private Long id;

    @ManyToOne
    @JsonBackReference
    private Room room;

    @ManyToOne
    private Amenity amenity;

}
