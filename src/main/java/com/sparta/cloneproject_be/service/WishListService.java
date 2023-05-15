package com.sparta.cloneproject_be.service;

import com.sparta.cloneproject_be.dto.MessageDto;
import com.sparta.cloneproject_be.dto.WishListRequestDto;
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
}
