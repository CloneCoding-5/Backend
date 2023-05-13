package com.sparta.cloneproject_be.repository;

import com.sparta.cloneproject_be.entity.RoomImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<RoomImage, Long> {
}
