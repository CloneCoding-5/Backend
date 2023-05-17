package com.sparta.cloneproject_be.service;


import com.sparta.cloneproject_be.dto.MainpageResponseDto;
import com.sparta.cloneproject_be.dto.RoomDetailResponseDto;
import com.sparta.cloneproject_be.dto.RoomRequestDto;
import com.sparta.cloneproject_be.dto.RoomResponseDto;
import com.sparta.cloneproject_be.entity.Reservation;
import com.sparta.cloneproject_be.entity.Room;
import com.sparta.cloneproject_be.entity.RoomImage;
import com.sparta.cloneproject_be.entity.User;
import com.sparta.cloneproject_be.exception.CustomException;
import com.sparta.cloneproject_be.exception.ErrorMessage;
import com.sparta.cloneproject_be.repository.ImageRepository;
import com.sparta.cloneproject_be.repository.ReserveRepository;
import com.sparta.cloneproject_be.repository.RoomRepository;
import com.sparta.cloneproject_be.util.S3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.time.Duration;
import java.time.LocalDate;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final ImageRepository imageRepository;
    private final S3Uploader s3Uploader;
    private final ReserveRepository reserveRepository;

    //숙소 게시글 등록
    @Transactional
    public ResponseEntity<RoomResponseDto> createPost(RoomRequestDto requestDTO, List<String> imgPaths, User user) {
        if(!imgPaths.isEmpty()) {
            LocalDate expiredDate = calcExpiredDate(requestDTO.getExpiredDate());
            Room room = new Room(requestDTO, expiredDate, user);
            roomRepository.save(room);

            // 이미지 업로드
            List<RoomImage> imgList = new ArrayList<>();
            for (String imgUrl : imgPaths) {
                RoomImage roomImage = new RoomImage(imgUrl, room);
                imageRepository.save(roomImage);
                imgList.add(roomImage);
            }
            room.setImages(imgList);
            return ResponseEntity.status(HttpStatus.CREATED).body(new RoomResponseDto(room));
        } else {
            throw new CustomException(ErrorMessage.WRONG_INPUT_IMAGE);
        }
    }

    //메인페이지 조회
    public Page<MainpageResponseDto> getRoomLists(int minPrice, int maxPrice, String region, List<String> amenities, String roomType, String categories, Pageable pageable) {
        Page<Room> rooms = roomRepository.findRooms(minPrice, maxPrice, region, amenities, roomType, categories, pageable);
        return rooms.map(MainpageResponseDto::new);
    }

    //숙소 게시글 전체 조회
    @Transactional(readOnly = true)
    public ResponseEntity<Map<String, List<RoomResponseDto>>> listPosts() {
        List<Room> rooms = roomRepository.findAll();
        List<RoomResponseDto> hostingList = new ArrayList<>();
        for(Room room : rooms) {
            hostingList.add(new RoomResponseDto(room));
        }

        Map<String, List<RoomResponseDto>> result = new HashMap<>();
        result.put("hostingList", hostingList);
        return ResponseEntity.ok().body(result);
    }

    //숙소 게시글 수정
    @Transactional
    public ResponseEntity<RoomResponseDto> updatePost(Long roomId, RoomRequestDto requestDTO, List<String> imgPaths, User user) {

        // 게시글이 존재하는지 확인
        Room room = checkRoomExist(roomId);

        // 게시글 작성자와 수정하려는 사용자가 같은치 체크
        if(checkAuthorIdMatch(room, user)){
            throw new CustomException(ErrorMessage.CANNOT_UPDATE_POST);
        }

        if(!room.getImages().isEmpty()) {
            // S3에 저장된 이미지 삭제.
            List<RoomImage> imgList = imageRepository.findAllByRoom(room);
            for (RoomImage img : imgList) {
                s3Uploader.deleteFile(img.getImageUrl());
                imageRepository.deleteById(img.getImageId());
            }
        }

        // 이미지 업로드
        List<RoomImage> imgList = new ArrayList<>();
        for (String imgUrl : imgPaths) {
            RoomImage roomImage = new RoomImage(imgUrl, room);
            imageRepository.save(roomImage);
            imgList.add(roomImage);
        }
        room.setImages(imgList);

        room.update(requestDTO, imgList);
        return ResponseEntity.status(HttpStatus.OK).body(new RoomResponseDto(room));
    }

    //숙소 게시글 삭제
    @Transactional
    public ResponseEntity<String> deletePost(@PathVariable Long roomId, User user) {
        // 게시글이 존재하는지 확인
        Room room = checkRoomExist(roomId);

        // 게시글 작성자와 삭제하려는 사용자가 같은지 체크
        if(checkAuthorIdMatch(room, user)) {
            throw new CustomException(ErrorMessage.CANNOT_DELETE_POST);
        }

        // S3에 저장된 이미지 삭제.
        List<RoomImage> imgList = imageRepository.findAllByRoom(room);
        for (RoomImage img : imgList) {
            s3Uploader.deleteFile(img.getImageUrl());
        }

        roomRepository.delete(room);
        return ResponseEntity.status(HttpStatus.OK).body("게시글 식제 성공");
    }

    // 숙소 상세 페이지 데이터
    public ResponseEntity<RoomDetailResponseDto> getRoomDetails(Long roomId) {
        Room room = checkRoomExist(roomId);
        List<LocalDate> enableDates = getEnableDates(room).stream().toList();
        RoomDetailResponseDto roomDetailResponseDto = new RoomDetailResponseDto(room, enableDates);

        return ResponseEntity.ok(roomDetailResponseDto);
    }

    // id를 매개변수로 받아서 id에 대응되는 게시글이 존재하는지 체크하는 메서드
    private Room checkRoomExist(Long id){
        return roomRepository.findById(id).orElseThrow(
                () -> new CustomException(ErrorMessage.NON_EXIST_POST)
        );
    }

    // enableDates 계산기
    private Set<LocalDate> getEnableDates(Room room) {
        List<Reservation> reservations = reserveRepository.findAllByRoomAfterDate(room, LocalDate.now());
        LocalDate expiredDate = room.getExpiredDate();

        Set<LocalDate> enableDates = getBetweenDates(LocalDate.now(), expiredDate); // List 와 Set 의 contains 메소드 차이 확인하기

        // 2중포문 말고 다른 방법으로 할 수 있을지 고민해보기
        for (Reservation reservation : reservations) {
            Set<LocalDate> datesBetweenInAndOut = getBetweenDates(reservation.getCheckIn(), reservation.getCheckOut());
            for (LocalDate date : datesBetweenInAndOut) {
                enableDates.remove(date);
            }
        }

        return enableDates;
    }

    // 두 날짜 사이(경계 포함)의 날짜들을 반환하는 메서드
    private Set<LocalDate> getBetweenDates(LocalDate from, LocalDate to) {
        Set<LocalDate> betweenDates = new LinkedHashSet<>();

        int betweenFromTo = (int)Duration.between(from.atStartOfDay(), to.atStartOfDay()).toDays();
        for (int i = 0; i <= betweenFromTo; i++) {
            betweenDates.add(from.plusDays(i));
        }
        return betweenDates;
    }

    // 게시글과 게시글을 변경하려는 요청을 보낸 유저가 일치하는지 여부 체크 default return value : true
    private boolean checkAuthorIdMatch(Room room, User user){
        if(room.getUser().getUserId().equals(user.getUserId()))
            return false;
        return true;
    }
    
    // 숙소 등록 시 만기일자 계산
    private LocalDate calcExpiredDate(int expiredDate) {
        LocalDate result = LocalDate.now();
        switch (expiredDate) {
            case 1:
                result = result.plusMonths(12L);
                break;
            case 2:
                result = result.plusMonths(9L);
                break;
            case 3:
                result = result.plusMonths(6L);
                break;
            case 4:
                result = result.plusMonths(3L);
                break;
            default:
                result = null;
        }
        return result;
    }

}
