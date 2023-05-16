package com.sparta.cloneproject_be.repository;

import com.sparta.cloneproject_be.entity.Room;
import com.sparta.cloneproject_be.entity.RoomImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<RoomImage, Long> {
    List<RoomImage> findAllByRoom(Room roomId);
}
