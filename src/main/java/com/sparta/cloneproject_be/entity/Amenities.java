package com.sparta.cloneproject_be.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Amenities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "amennityId")
    private Long amennityId;

    @OneToMany(mappedBy = "amenities")
    private List<Room_Amenities> roomAmenity = new ArrayList<>();
}
