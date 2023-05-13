package com.sparta.cloneproject_be.dto;

import com.sparta.cloneproject_be.entity.Reservation;
import lombok.Getter;

@Getter
public class ReserveResponseDto {

    private String image;

    private String title;

    private Long roomId;

    public ReserveResponseDto(Reservation reservation) {
        image = reservation.getRoom().getRoomImages().get(0).getImageUrl();
        title = reservation.getRoom().getTitle();
        roomId = reservation.getRoom().getId();
    }
}
