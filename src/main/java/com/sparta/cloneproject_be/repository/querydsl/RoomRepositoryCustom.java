package com.sparta.cloneproject_be.repository.querydsl;

import com.sparta.cloneproject_be.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RoomRepositoryCustom {
    Page<Room> findRooms(int minPrice, int maxPrice, String region, List<String> amenities, String roomType,String categories, Pageable pageable);
}