package com.sparta.cloneproject_be.service;

import com.sparta.cloneproject_be.dto.MainpageResponseDto;
import com.sparta.cloneproject_be.entity.Room;
import com.sparta.cloneproject_be.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MainPageService {

    private final RoomRepository roomRepository;



    public Page<MainpageResponseDto> getRoomLists(int minPrice, int maxPrice, String region, List<String> amenities, String roomType, Pageable pageable) {
        Page<Room> rooms = roomRepository.findRooms(minPrice, maxPrice, region, amenities, roomType, pageable);
        return rooms.map(MainpageResponseDto::new);
    }
//    public Page<MainpageResponseDto> getRoomLists(Pageable pageable) {
//        Sort sort = Sort.by(Sort.Direction.DESC, "roomId");
//        pageable = PageRequest.of(0, pageable.getPageSize());
//        Page<Room> rooms = roomRepository.findAll(pageable);
//        return rooms.map(MainpageResponseDto::new);
//    }

}
