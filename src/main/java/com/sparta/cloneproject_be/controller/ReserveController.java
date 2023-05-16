package com.sparta.cloneproject_be.controller;

import com.sparta.cloneproject_be.dto.MessageDto;
import com.sparta.cloneproject_be.dto.ReservationRequestDto;
import com.sparta.cloneproject_be.dto.ReserveResponseDto;
import com.sparta.cloneproject_be.dto.RoomDetailResponseDto;
import com.sparta.cloneproject_be.entity.User;
import com.sparta.cloneproject_be.service.ReserveService;
import com.sparta.cloneproject_be.service.RoomService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ReserveController {

    private final ReserveService reserveService;
    private final RoomService roomService;

    // 나의 예약 목록 조회
    @GetMapping("/reservation")
    public ResponseEntity<Map<String, List<ReserveResponseDto>>> getReservations(@Parameter(hidden = true) @AuthenticationPrincipal User user) {
        return reserveService.getReservations(user);
    }

    // 예약 취소하기
    @DeleteMapping("/reservation/{reservationId}")
    public ResponseEntity<MessageDto> deleteReservation(@PathVariable Long reservationId, @Parameter(hidden = true) @AuthenticationPrincipal User user) {
        return reserveService.deleteReservation(reservationId, user);
    }

    // 예약하기
    @PostMapping("/reservation/{roomId}")
    public ResponseEntity<MessageDto> createReservation(@PathVariable Long roomId, ReservationRequestDto reservationRequestDto, @Parameter(hidden = true) @AuthenticationPrincipal User user) {
        return reserveService.createReservation(roomId, reservationRequestDto, user);
    }

    // 숙소 상세페이지(RoomController 쪽 api url 에 대한 논의 후 위치 변경 필요)
    @GetMapping("/rooms/{roomId}")
    public ResponseEntity<RoomDetailResponseDto> getRoomDetails(@PathVariable Long roomId) {
        return roomService.getRoomDetails(roomId);
    }
}
