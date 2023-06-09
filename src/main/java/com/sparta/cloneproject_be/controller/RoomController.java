package com.sparta.cloneproject_be.controller;

import com.sparta.cloneproject_be.dto.MainpageResponseDto;
import com.sparta.cloneproject_be.dto.RoomRequestDto;
import com.sparta.cloneproject_be.dto.RoomResponseDto;
import com.sparta.cloneproject_be.entity.User;
import com.sparta.cloneproject_be.service.RoomService;
import com.sparta.cloneproject_be.util.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;
    private final S3Service s3Service;


    //숙소 게시글 등록 API
    @PostMapping(value = "/host", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<RoomResponseDto> createPost(@RequestPart("content") RoomRequestDto requestDTO,
                                                      @RequestPart("image") List<MultipartFile> multipartFile,
                                                      @AuthenticationPrincipal User user) {
        List<String> imgPaths = s3Service.upload(multipartFile);
        return roomService.createPost(requestDTO, imgPaths, user);
    }


    //메인페이지 조회 API
    @GetMapping("/")
    public ResponseEntity<Page<MainpageResponseDto>> getRooms(
            @RequestParam(defaultValue = "0") int minPrice,
            @RequestParam(defaultValue = "1000000") int maxPrice,
            @RequestParam(required = false) String region,
            @RequestParam(required = false) List<String> amenities,
            @RequestParam(required = false) String roomType,
            @RequestParam(required = false) String categories,
            Pageable pageable){

        Page<MainpageResponseDto> response = roomService.getRoomLists(minPrice, maxPrice, region, amenities, roomType, categories, pageable);
        return ResponseEntity.ok(response);
    }

    //숙소 게시글 전체 조회 API
    @GetMapping("/host")
    public ResponseEntity<Map<String, List<RoomResponseDto>>> listPosts() {
        return roomService.listPosts();
    }

    //숙소 게시글 수정 API
    @PutMapping("/host/{roomId}")
    public ResponseEntity<RoomResponseDto> updatePost(@PathVariable Long roomId,
                                                      @RequestPart("content") RoomRequestDto requestDTO,
                                                      @RequestPart("image") List<MultipartFile> multipartFile,
                                                      @AuthenticationPrincipal User user) {
        List<String> imgPaths = s3Service.upload(multipartFile);
        return roomService.updatePost(roomId, requestDTO, imgPaths, user);
    }

    //숙소 게시글 삭제 API
    @DeleteMapping("/host/{roomId}")
    public ResponseEntity<String> deletePost(@PathVariable Long roomId, @AuthenticationPrincipal User user) {
        return roomService.deletePost(roomId, user);
    }
}
