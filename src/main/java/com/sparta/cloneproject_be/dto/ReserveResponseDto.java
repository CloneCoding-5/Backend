package com.sparta.cloneproject_be.dto;

import com.sparta.cloneproject_be.entity.Reservation;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ReserveResponseDto {

    private String image;

    private String title;

    private Long roomId;

    public ReserveResponseDto(Reservation reservation) {
        // 첫 번째 이미지를 대표 이미지로
        image = reservation.getRoom().getImages().get(0).getImageUrl();
        title = reservation.getRoom().getTitle();
        roomId = reservation.getRoom().getRoomId();
    }
}
