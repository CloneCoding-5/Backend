package com.sparta.cloneproject_be.entity;

import com.sparta.cloneproject_be.dto.WishListRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class WishList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wishListId")
    private Long id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "roomId")
    private Room room;

    private String wishListName;

    public WishList(WishListRequestDto wishListRequestDto, User user, Room room){
        this.wishListName = wishListRequestDto.getWishListName();
        this.user = user;
        this.room = room;
    }
}
