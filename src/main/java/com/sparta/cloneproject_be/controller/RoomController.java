//package com.sparta.cloneproject_be.controller;
//
//import com.sparta.cloneproject_be.dto.RoomRequestDto;
//import com.sparta.cloneproject_be.dto.RoomResponseDto;
//import com.sparta.cloneproject_be.service.RoomService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.Map;
//
//@Slf4j
//@RestController
//@RequestMapping("/users")
//@RequiredArgsConstructor
//public class RoomController {
//
//    private final RoomService roomService;
//
//    //숙소 게시글 등록 API
//    @PostMapping(value = "/host", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<RoomResponseDto> createPost(@RequestBody RoomRequestDto requestDTO,
//                                                      @RequestParam(value="image") MultipartFile image) throws IOException {
//        return roomService.createPost(requestDTO, image);
//    }
//
//    //숙소 게시글 전체 조회 API
//    @GetMapping("/host")
//    public ResponseEntity<Map<String, List<RoomResponseDto>>> listPosts() {
//        return roomService.listPosts();
//    }
//
//    //숙소 게시글 수정 API
//    @PutMapping("/host/{roomId}")
//    public ResponseEntity<RoomResponseDto> updatePost(@PathVariable Long roomId, @RequestBody RoomRequestDto requestDTO) {
//        return roomService.updatePost(roomId, requestDTO);
//    }
//
//    //숙소 게시글 삭제 API
//    @DeleteMapping("/posts/{roomId}")
//    public ResponseEntity<String> deletePost(@PathVariable Long roomId) {
//        return roomService.deletePost(roomId);
//    }
//}
