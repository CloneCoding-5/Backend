package com.sparta.cloneproject_be.controller;


import com.sparta.cloneproject_be.dto.MainpageResponseDto;
import com.sparta.cloneproject_be.dto.RoomResponseDto;
import com.sparta.cloneproject_be.entity.Room;
import com.sparta.cloneproject_be.service.MainPageService;
import com.sparta.cloneproject_be.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MainpageController {

    private final MainPageService mainPageService;
//{
//“roomList” : [
//       {
//        ”roomId”: 1
//        ”region” : “region”,
//        ”image” : “image”
//        ”host” : “host”,
//        ”price” : 12345
//        }, …
//]
//}
    ///rooms?page=&category=&region=&checkin=&checkout=&guests=&min_price=&max_price=&room_type=&amenities=
//    @GetMapping("/rooms")
//    public  ResponseEntity<Map<String, List<MainpageResponseDto>>> getRoomLists(Pageable pageable){
//    public ResponseEntity<Page<Room>> getRooms(Pageable pageable){
//        return ResponseEntity.ok(mainPageService.getRoomLists(pageable));
//    }
    @GetMapping("/rooms")
    public ResponseEntity<Page<MainpageResponseDto>> getRooms(Pageable pageable){
        Page<Room> rooms = mainPageService.getRoomLists(pageable);
        Page<MainpageResponseDto> response = rooms.map(MainpageResponseDto::new);
        return ResponseEntity.ok(response);
    }


}
