package com.sparta.cloneproject_be.service;

import com.sparta.cloneproject_be.dto.MainpageResponseDto;
import com.sparta.cloneproject_be.entity.Room;
import com.sparta.cloneproject_be.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MainPageService {

    private final RoomRepository roomRepository;

//    public ResponseEntity<Map<String, List<MainpageResponseDto>>> getRoomLists(Pageable pageable) {
        public Page<Room> getRoomLists(Pageable pageable) {
        Sort sort = Sort.by(Sort.Direction.DESC, "roomId");
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        return roomRepository.findAll(pageable);
//        List<Room> rooms = roomRepository.findAll();
//        List<MainpageResponseDto> allLists = new ArrayList<>();
//        for(Room post : rooms){
//            allLists.add(new MainpageResponseDto(post));
//        }
//        Map<String, List<MainpageResponseDto>> result = new HashMap<>();
//        result.put("roomList", allLists);
//        return ResponseEntity.ok().body(result);
    }
}
