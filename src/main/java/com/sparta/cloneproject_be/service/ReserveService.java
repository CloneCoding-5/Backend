package com.sparta.cloneproject_be.service;

import com.sparta.cloneproject_be.dto.MessageDto;
import com.sparta.cloneproject_be.dto.ReserveResponseDto;
import com.sparta.cloneproject_be.entity.Reservation;
import com.sparta.cloneproject_be.entity.User;
import com.sparta.cloneproject_be.exception.CustomException;
import com.sparta.cloneproject_be.exception.ErrorMessage;
import com.sparta.cloneproject_be.repository.ReserveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReserveService {

    private final ReserveRepository reserveRepository;

    @Transactional(readOnly = true)
    public ResponseEntity<Map<String, List<ReserveResponseDto>>> getReservations(User user) {
        // 나의 예약 기록 반환 시 정렬 기준 논의 필요
        List<Reservation> reservationList = reserveRepository.findAllByUser(user);
        Map<String, List<ReserveResponseDto>> map = new HashMap<>();
        map.put("reserveList", reservationList.stream().map(ReserveResponseDto::new).collect(Collectors.toList()));
        return ResponseEntity.ok(map);
    }

    @Transactional
    public ResponseEntity<MessageDto> deleteReservation(Long reservationId, User user) {

        Reservation reservation = reserveRepository.findById(reservationId).orElseThrow(
                () -> new CustomException(ErrorMessage.NON_EXIST_POST)
        );

        if(!isYourReserve(reservation, user)) {
            throw new CustomException(ErrorMessage.CANNOT_CANCLE_RESERVATION);
        }

        reserveRepository.delete(reservation);
        return ResponseEntity.ok(new MessageDto("예약이 취소되었습니다."));
    }

    private boolean isYourReserve(Reservation reservation, User user) {
        return reservation.getUser().equals(user);
    }
}
