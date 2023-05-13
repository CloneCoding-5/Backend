package com.sparta.cloneproject_be.controller;

import com.sparta.cloneproject_be.dto.MessageDto;
import com.sparta.cloneproject_be.dto.ReserveResponseDto;
import com.sparta.cloneproject_be.entity.User;
import com.sparta.cloneproject_be.service.ReserveService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReserveController {

    private final ReserveService reserveService;

    @GetMapping
    public ResponseEntity<Map<String, List<ReserveResponseDto>>> getReservations(@Parameter(hidden = true) @AuthenticationPrincipal User user) {
        return reserveService.getReservations(user);
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<MessageDto> deleteReservation(@PathVariable Long reservationId, @AuthenticationPrincipal User user) {
        return reserveService.deleteReservation(reservationId, user);
    }
}
