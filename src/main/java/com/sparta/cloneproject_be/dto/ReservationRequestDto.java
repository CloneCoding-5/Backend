package com.sparta.cloneproject_be.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ReservationRequestDto {

    private LocalDate checkIn;

    private LocalDate checkOut;

    private int guests;

    public ReservationRequestDto(String checkIn, String checkOut, int guests) {
        this.checkIn = LocalDate.parse(checkIn);
        this.checkOut = LocalDate.parse(checkOut);
        this.guests = guests;
    }
}
