package com.sparta.cloneproject_be.repository;

import com.sparta.cloneproject_be.entity.Mypage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MypageRepository extends JpaRepository<Mypage, Long> {
}
