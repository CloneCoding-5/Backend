package com.sparta.cloneproject_be.dto;

import com.sparta.cloneproject_be.entity.RoomImage;
import com.sparta.cloneproject_be.entity.RoomAmenities;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Getter
public class RoomRequestDto {

    private String title;
    private int price;
    private String region;
    private String city;
    private int capacity;
    private String roomType;
    private List<RoomAmenities> amenities;
    private List<MultipartFile> image;
    private Date expiredDate;
}
