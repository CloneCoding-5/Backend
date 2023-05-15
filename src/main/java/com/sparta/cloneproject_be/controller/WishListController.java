package com.sparta.cloneproject_be.controller;

import com.sparta.cloneproject_be.dto.MessageDto;
import com.sparta.cloneproject_be.dto.WishListRequestDto;
import com.sparta.cloneproject_be.entity.User;
import com.sparta.cloneproject_be.service.WishListService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "wishListController", description = "위시리스트 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/wishlists")
public class WishListController {

    private final WishListService wishListService;

    // 위시리스트 추가
    @Operation(summary = "위시리스트 API", description = "위시리스트 등록")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "위시리스트에 추가됐습니다.")})
    @PostMapping("/{roomId}")
    public ResponseEntity<MessageDto> createWishList(@PathVariable long roomId, WishListRequestDto requestDto, @AuthenticationPrincipal User user){
        return wishListService.createWishList(roomId, requestDto, user);
    }
}
