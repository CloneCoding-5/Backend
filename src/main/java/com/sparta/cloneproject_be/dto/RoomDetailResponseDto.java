package com.sparta.cloneproject_be.dto;

import com.sparta.cloneproject_be.entity.Room;
import com.sparta.cloneproject_be.entity.RoomImage;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@ToString
public class RoomDetailResponseDto {

    private String title;

    private int price;

    private String region;

    private int capacity;

    private List<String> amenities;

    private List<LocalDate> enableDates;

    private List<String> images;

    public RoomDetailResponseDto(Room room, List<LocalDate> enableDates) {
        this.title = room.getTitle();
        this.price = room.getPrice();
        this.region = room.getRegion();
        this.capacity = room.getCapacity();
        this.amenities = room.getRoomAmenities();
        this.enableDates = enableDates;
        this.images = room.getImages().stream().map(RoomImage::getImageUrl).collect(Collectors.toList());
    }
}
