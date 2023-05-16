package com.sparta.cloneproject_be.repository;

import com.sparta.cloneproject_be.entity.Room;
import com.sparta.cloneproject_be.entity.User;
import com.sparta.cloneproject_be.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WishListRepository extends JpaRepository<WishList, Long> {

    List<WishList> findAllByUser(User user);

    Optional<WishList> findWishListByUserAndRoom(User user, Room room);
}
