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

/*
무선 인터넷, 주방, 침실에 딸린 개인 욕실, 세탁기, 건조기, 에어컨, 난방, 업무 전용 공간,
TV, 헤어드라이어, 다리미
 */