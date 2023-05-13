package com.sparta.cloneproject_be.dto;

import com.sparta.cloneproject_be.entity.Room;
import com.sparta.cloneproject_be.entity.RoomImage;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class RoomResponseDto {

    private Long roomId;
    private List<RoomImage> imageUrl;
    private String title;

    public RoomResponseDto(Room room) {
        this.roomId = room.getRoomId();
        this.imageUrl = room.getImages();
        this.title = room.getTitle();
    }
}
