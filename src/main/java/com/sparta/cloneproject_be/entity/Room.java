package com.sparta.cloneproject_be.entity;

import com.sparta.cloneproject_be.dto.RoomRequestDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "postId")
    private Long roomId;
    private String title;
    private int price;
    private String region;
    private String city;
    private int capacity;
    private String roomType;
    private Date expiredDate;

    // 편의시설
    @OneToMany(mappedBy = "room", cascade = CascadeType.REMOVE)
    private List<Room_Amenities> roomAmenity = new ArrayList<>();

    // 이미지
    @OneToMany(mappedBy = "room", cascade = CascadeType.REMOVE)
    private List<RoomImage> images = new ArrayList<>();


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
        this.roomAmenity = roomRequestDto.getAmenities();
        this.images = roomRequestDto.getImages();
        this.expiredDate = roomRequestDto.getExpiredDate();
    }
}
