package com.sparta.cloneproject_be.entity;

import com.sparta.cloneproject_be.dto.RoomRequestDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roomId")
    private Long roomId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String region;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private int capacity;

    @Column(nullable = false)
    private String roomType;

    @Column(nullable = false)
    private Date expiredDate;

    // 편의시설
    @OneToMany(mappedBy = "room", cascade = CascadeType.REMOVE)
    private List<RoomAmenities> roomAmenities = new ArrayList<>();

    // 이미지
    @OneToMany(mappedBy = "room", cascade = CascadeType.REMOVE)
    private List<RoomImage> images = new ArrayList<>();

    // 카테고리
    @OneToMany(mappedBy = "room", cascade = CascadeType.REMOVE)
    private List<RoomCategory> categories = new ArrayList<>();


    public Room(RoomRequestDto roomRequestDto) {
        this.title = roomRequestDto.getTitle();
        this.price = roomRequestDto.getPrice();
        this.region = roomRequestDto.getRegion();
        this.city = roomRequestDto.getCity();
        this.capacity = roomRequestDto.getCapacity();
        this.roomType = roomRequestDto.getRoomType();
        this.expiredDate = roomRequestDto.getExpiredDate();
    }

    public void update(RoomRequestDto roomRequestDto) {
        this.title = roomRequestDto.getTitle();
        this.price = roomRequestDto.getPrice();
        this.region = roomRequestDto.getRegion();
        this.city = roomRequestDto.getCity();
        this.capacity = roomRequestDto.getCapacity();
        this.roomType = roomRequestDto.getRoomType();
        this.roomAmenities = roomRequestDto.getAmenities();
        this.expiredDate = roomRequestDto.getExpiredDate();
    }
}
