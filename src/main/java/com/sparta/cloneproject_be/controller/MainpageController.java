package com.sparta.cloneproject_be.controller;


import com.sparta.cloneproject_be.dto.MainpageResponseDto;
import com.sparta.cloneproject_be.service.MainPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MainpageController {

    private final MainPageService mainPageService;

    ///rooms?page=&category=&region=&checkin=&checkout=&guests=&min_price=&max_price=&room_type=&amenities=
//    @GetMapping("/rooms")
//    public ResponseEntity<Page<MainpageResponseDto>> getRooms(Pageable pageable){
//        Page<MainpageResponseDto> response = mainPageService.getRoomLists(pageable);
//        return ResponseEntity.ok(response);

        @GetMapping("/rooms")
        public ResponseEntity<Page<MainpageResponseDto>> getRooms(
                @RequestParam(defaultValue = "0") int minPrice,
                @RequestParam(defaultValue = "1000000") int maxPrice,
                @RequestParam(required = false) String region,
                @RequestParam(required = false) List<String> amenities,
                @RequestParam(required = false) String roomType,
                @RequestParam(required = false) List<String> categories,
                Pageable pageable){

            Page<MainpageResponseDto> response = mainPageService.getRoomLists(minPrice, maxPrice, region, amenities, roomType, categories, pageable);
            return ResponseEntity.ok(response);
        }
    }


