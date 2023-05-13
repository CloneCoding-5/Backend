package com.sparta.cloneproject_be.repository;

import com.sparta.cloneproject_be.entity.Reservation;
import com.sparta.cloneproject_be.entity.Room;
import com.sparta.cloneproject_be.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReserveRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByUser(User user);

    List<Reservation> findAllByRoom(Room room);
}
