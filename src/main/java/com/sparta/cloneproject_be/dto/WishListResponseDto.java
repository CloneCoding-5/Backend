package com.sparta.cloneproject_be.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WishListResponseDto {

    private String image;
    private String wishListName;
    private Long roomId;

    public WishListResponseDto(String image, String wishListName, Long roomId){
        this.image = image;
        this.wishListName = wishListName;
        this.roomId = roomId;
    }
}
