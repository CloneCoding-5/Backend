package com.sparta.cloneproject_be.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sparta.cloneproject_be.dto.RoomRequestDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
    private LocalDate expiredDate;

    // 호스트
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    // 편의시설
    @OneToMany(mappedBy = "room", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<RoomAmenities> roomAmenities = new ArrayList<>();

    // 이미지
    @OneToMany(mappedBy = "room", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<RoomImage> images = new ArrayList<>();

    // 카테고리
    @OneToMany(mappedBy = "room", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<RoomCategory> categories = new ArrayList<>();

    public Room(RoomRequestDto roomRequestDto, User user) {
        this.title = roomRequestDto.getTitle();
        this.price = roomRequestDto.getPrice();
        this.region = roomRequestDto.getRegion();
        this.city = roomRequestDto.getCity();
        this.capacity = roomRequestDto.getCapacity();
        this.roomType = roomRequestDto.getRoomType();
        this.expiredDate = roomRequestDto.getExpiredDate();
        this.user = user;
    }

    public void update(RoomRequestDto roomRequestDto) {
        this.title = roomRequestDto.getTitle();
        this.price = roomRequestDto.getPrice();
        this.region = roomRequestDto.getRegion();
        this.city = roomRequestDto.getCity();
        this.capacity = roomRequestDto.getCapacity();
        this.roomType = roomRequestDto.getRoomType();
        //this.roomAmenities = roomRequestDto.getAmenities().;
        this.expiredDate = roomRequestDto.getExpiredDate();
    }
}
