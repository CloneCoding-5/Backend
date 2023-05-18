package com.sparta.cloneproject_be.dto;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Getter
public class RoomRequestDto {
    private String title;
    private int price;
    private String region;
    private String city;
    private int capacity;
    private String roomType;
    private List<String> amenities;
    private List<String> categories;
    private int expiredDate;
}
