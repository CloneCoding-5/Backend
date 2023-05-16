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
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "userId")
    private User user;

    // 편의시설
    @ElementCollection
    private List<String> roomAmenities = new ArrayList<>();

    // 이미지
    @OneToMany(mappedBy = "room", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<RoomImage> images = new ArrayList<>();

    // 카테고리
    @ElementCollection
    private List<String> categories = new ArrayList<>();

    public Room(RoomRequestDto roomRequestDto, LocalDate expiredDate, User user) {
        this.title = roomRequestDto.getTitle();
        this.price = roomRequestDto.getPrice();
        this.region = roomRequestDto.getRegion();
        this.city = roomRequestDto.getCity();
        this.capacity = roomRequestDto.getCapacity();
        this.roomType = roomRequestDto.getRoomType();
        this.roomAmenities = roomRequestDto.getAmenities();
        this.categories = roomRequestDto.getCategories();
        this.expiredDate = expiredDate;
        this.user = user;
    }

    public void update(RoomRequestDto roomRequestDto, List<RoomImage> images) {
        this.title = roomRequestDto.getTitle();
        this.price = roomRequestDto.getPrice();
        this.region = roomRequestDto.getRegion();
        this.city = roomRequestDto.getCity();
        this.capacity = roomRequestDto.getCapacity();
        this.roomType = roomRequestDto.getRoomType();
        this.roomAmenities = roomRequestDto.getAmenities();
        this.categories = roomRequestDto.getCategories();
        this.expiredDate = roomRequestDto.getExpiredDate();
        this.images = images;
    }
}
