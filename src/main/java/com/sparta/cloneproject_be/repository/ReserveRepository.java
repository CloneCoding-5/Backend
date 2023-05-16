package com.sparta.cloneproject_be.repository;

import com.sparta.cloneproject_be.entity.Reservation;
import com.sparta.cloneproject_be.entity.Room;
import com.sparta.cloneproject_be.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.time.LocalDate;
import java.util.List;

public interface ReserveRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByUser(User user);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select r from Reservation r where r.room = :room and r.checkOut >= :checkIn and r.checkIn <= :checkOut")
    List<Reservation> findAllByRoomReserved(@Param("room") Room room, @Param("checkIn") LocalDate checkIn, @Param("checkOut") LocalDate checkOut);

    @Query("select r from Reservation r where r.room = :room and r.checkOut >= :date")
    List<Reservation> findAllByRoomAfterDate(@Param("room") Room room, @Param("date") LocalDate date);

    @Query("select r from Reservation r where r.room.roomId = :id")
    List<Reservation> findAllByRoomId(@Param("id") Long id);
}
