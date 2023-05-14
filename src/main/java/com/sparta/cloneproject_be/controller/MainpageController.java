package com.sparta.cloneproject_be.controller;


import com.sparta.cloneproject_be.dto.MainpageResponseDto;
import com.sparta.cloneproject_be.entity.Room;
import com.sparta.cloneproject_be.service.MainPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MainpageController {

    private final MainPageService mainPageService;

    ///rooms?page=&category=&region=&checkin=&checkout=&guests=&min_price=&max_price=&room_type=&amenities=
    @GetMapping("/rooms")
    public ResponseEntity<Page<MainpageResponseDto>> getRooms(Pageable pageable){
        Page<MainpageResponseDto> response = mainPageService.getRoomLists(pageable);
        return ResponseEntity.ok(response);
    }

}
