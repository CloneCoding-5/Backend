package com.sparta.cloneproject_be.service;

import com.sparta.cloneproject_be.dto.MessageDto;
import com.sparta.cloneproject_be.dto.WishListRequestDto;
import com.sparta.cloneproject_be.dto.WishListResponseDto;
import com.sparta.cloneproject_be.entity.Room;
import com.sparta.cloneproject_be.entity.User;
import com.sparta.cloneproject_be.entity.WishList;
import com.sparta.cloneproject_be.exception.CustomException;
import com.sparta.cloneproject_be.exception.ErrorMessage;
import com.sparta.cloneproject_be.repository.RoomRepository;
import com.sparta.cloneproject_be.repository.WishListRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor

public class WishListService {

    private final WishListRepository wishListRepository;
    private final RoomRepository roomRepository;

    // 위시리스트 등록
    @Transactional
    public ResponseEntity<MessageDto> createWishList(long roomId, WishListRequestDto requestDto, User user){
        Room room = roomRepository.findById(roomId).orElseThrow(
                () -> new CustomException(ErrorMessage.NON_EXIST_POST));

        WishList wishList = new WishList(requestDto, user, room);
        wishListRepository.save(wishList);
        return ResponseEntity.ok().body(new MessageDto("위시리스트에 추가됐습니다."));
    }

    // 위시리스트 조회
    @Transactional(readOnly = true)
    public ResponseEntity<Map<String, List<WishListResponseDto>>> getWishList(User user){
        List<WishList> wishLists = wishListRepository.findAllByUser(user);
        List<WishListResponseDto> responseDto = wishLists.stream()
                .map(WishListResponseDto::new)
                .toList();

        Map<String, List<WishListResponseDto>> result = new HashMap<>();
        result.put("wishList", responseDto);

        return ResponseEntity.ok().body(result);
    }

    // 위시리스트 삭제
    @Transactional
    public ResponseEntity<MessageDto> deleteWishList(long wishListId, User user) {
        wishListRepository.deleteByIdAndUser(wishListId, user);
        return ResponseEntity.ok().body(new MessageDto("위시리스트에서 삭제됐습니다."));
    }
}
