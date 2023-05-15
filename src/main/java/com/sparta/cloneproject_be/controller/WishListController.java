package com.sparta.cloneproject_be.controller;

import com.sparta.cloneproject_be.dto.MessageDto;
import com.sparta.cloneproject_be.dto.WishListRequestDto;
import com.sparta.cloneproject_be.dto.WishListResponseDto;
import com.sparta.cloneproject_be.entity.User;
import com.sparta.cloneproject_be.entity.WishList;
import com.sparta.cloneproject_be.service.WishListService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "wishListController", description = "위시리스트 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/wishlists")
public class WishListController {

    private final WishListService wishListService;

    // 위시리스트 추가
    @Operation(summary = "위시리스트 등록 API", description = "위시리스트 등록")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "위시리스트에 추가됐습니다.")})
    @PostMapping("/{roomId}")
    public ResponseEntity<MessageDto> createWishList(@PathVariable long roomId, @RequestBody WishListRequestDto requestDto, @AuthenticationPrincipal User user){
        return wishListService.createWishList(roomId, requestDto, user);
    }
    // 위시리스트 조회
    @Operation(summary = "위시리스트 조회 API", description = "위시리스트 조회")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "조회 성공")})
    @GetMapping
    public ResponseEntity<Map<String, List<WishListResponseDto>>> getWishList(@AuthenticationPrincipal User user){
        return wishListService.getWishList(user);
    }

    // 위시리스트 삭제
    @Operation(summary = "위시리스트 삭제 API", description = "위시리스트 삭제")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "위시리스트에서 삭제됐습니다.")})
    @DeleteMapping("/{wishListId}")
    public ResponseEntity<MessageDto> deleteWishList(@PathVariable long wishListId, @AuthenticationPrincipal User user){
        return wishListService.deleteWishList(wishListId, user);
    }
}
