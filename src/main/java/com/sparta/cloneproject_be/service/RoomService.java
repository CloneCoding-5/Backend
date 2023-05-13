package com.sparta.cloneproject_be.service;


import com.sparta.cloneproject_be.dto.RoomRequestDto;
import com.sparta.cloneproject_be.dto.RoomResponseDto;
import com.sparta.cloneproject_be.entity.Room;
import com.sparta.cloneproject_be.entity.RoomImage;
import com.sparta.cloneproject_be.exception.CustomException;
import com.sparta.cloneproject_be.exception.ErrorMessage;
import com.sparta.cloneproject_be.repository.ImageRepository;
import com.sparta.cloneproject_be.repository.RoomRepository;
import com.sparta.cloneproject_be.util.S3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
// import static com.sparta.cloneproject_be.exception.ErrorMessage.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final ImageRepository imageRepository;

    //숙소 게시글 등록
    @Transactional
    public ResponseEntity<RoomResponseDto> createPost(RoomRequestDto requestDTO, List<String> imgPaths) {
        if(!imgPaths.isEmpty()) {
            Room room = new Room(requestDTO);
            roomRepository.save(room);

            List<String> imgList = new ArrayList<>();
            for (String imgUrl : imgPaths) {
                RoomImage roomImage = new RoomImage(imgUrl, room);
                imageRepository.save(roomImage);
                imgList.add(roomImage.getImageUrl());
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(new RoomResponseDto(room));
        } else {
            throw new CustomException(ErrorMessage.WRONG_INPUT_IMAGE);
        }
    }

    //숙소 게시글 전체 조회
    @Transactional(readOnly = true)
    public ResponseEntity<Map<String, List<RoomResponseDto>>> listPosts() {
        List<Room> rooms = roomRepository.findAll();
        List<RoomResponseDto> hostingList = new ArrayList<>();
        for(Room post : rooms) {
            hostingList.add(new RoomResponseDto(post));
        }

        Map<String, List<RoomResponseDto>> result = new HashMap<>();
        result.put("hostingList", hostingList);
        return ResponseEntity.ok().body(result);
    }

    //숙소 게시글 수정
    @Transactional
    public ResponseEntity<RoomResponseDto> updatePost(Long roomId, RoomRequestDto requestDTO) {
        // 게시글 존재 유무체크
        Room room = isRoomExist(roomId);
        // 게시글 작성자와, 유저 매치 체크
//        if(checkAuthorIdMatch(room)){
//            throw new CustomException(WRITER_ONLY_MODIFY);
//        }

        room.update(requestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new RoomResponseDto(room));
    }

    //숙소 게시글 삭제
    @Transactional
    public ResponseEntity<String> deletePost(@PathVariable Long roomId) {
        // 게시글 존재 유무체크
        Room room = isRoomExist(roomId);
        // 게시글 작성자와, 유저 매치 체크
//        if(checkAuthorIdMatch(post, user)) {
//            throw new CustomException(WRITER_ONLY_DELETE);
//        }
        roomRepository.delete(room);
        return ResponseEntity.status(HttpStatus.OK).body("게시글 식제 성공");
    }


    // id를 매개변수로 받아서 id에 대응되는 게시글이 존재하는지 체크하는 메서드
    private Room isRoomExist(Long id){
        return roomRepository.findById(id).orElseThrow(
                () -> new NullPointerException("error")
        );
    }

    // 게시글과 게시글을 변경하려는 요청을 보낸 유저가 일치하는지 여부 체크 default return value : true
//    private boolean checkAuthorIdMatch(Room room, User user){
//        if(room.getUser().getUsername().equals(user.getUsername()))
//            return false;
//        return true;
//    }

}
