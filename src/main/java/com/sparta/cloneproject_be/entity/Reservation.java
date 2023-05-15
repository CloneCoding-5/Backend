package com.sparta.cloneproject_be.entity;

import com.sparta.cloneproject_be.dto.ReservationRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reserveId")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Room room;

    private LocalDate checkIn;

    private LocalDate checkOut;

    private Integer guests;

    public Reservation(ReservationRequestDto reservationRequestDto, Room room, User user) {
        this.checkIn = reservationRequestDto.getCheckIn();
        this.checkOut = reservationRequestDto.getCheckOut();
        this.guests = reservationRequestDto.getGuests();
        this.room = room;
        this.user = user;
    }
}
