package com.sparta.cloneproject_be.dto;

import com.sparta.cloneproject_be.entity.WishList;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WishListResponseDto {

    private String image;
    private String wishListName;
    private Long roomId;

    public WishListResponseDto(WishList wishList){
        this.image = wishList.getRoom().getImages().stream().limit(1).toString();
        this.wishListName = wishList.getWishListName();
        this.roomId = wishList.getRoom().getRoomId();
    }
}
