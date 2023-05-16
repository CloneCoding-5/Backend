package com.sparta.cloneproject_be.dto;

import com.sparta.cloneproject_be.entity.Room;
import com.sparta.cloneproject_be.entity.RoomImage;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MainpageResponseDto {

    private Long roomId;
    private String region;
    private List<RoomImage> imageUrl;
//    private String host;
    private int price;

    public MainpageResponseDto(Room room) {
        this.roomId = room.getRoomId();
        this.region = room.getRegion();
        this.imageUrl = room.getImages();
//        this.host = room.getUser().getNickname();
        this.price = room.getPrice();
    }
}
