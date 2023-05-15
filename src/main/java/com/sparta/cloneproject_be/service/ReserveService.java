package com.sparta.cloneproject_be.service;

import com.sparta.cloneproject_be.dto.MessageDto;
import com.sparta.cloneproject_be.dto.ReservationRequestDto;
import com.sparta.cloneproject_be.dto.ReserveResponseDto;
import com.sparta.cloneproject_be.entity.Reservation;
import com.sparta.cloneproject_be.entity.Room;
import com.sparta.cloneproject_be.entity.User;
import com.sparta.cloneproject_be.exception.CustomException;
import com.sparta.cloneproject_be.exception.ErrorMessage;
import com.sparta.cloneproject_be.repository.ReserveRepository;
import com.sparta.cloneproject_be.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReserveService {

    private final ReserveRepository reserveRepository;
    private final RoomRepository roomRepository;

    @Transactional(readOnly = true)
    public ResponseEntity<Map<String, List<ReserveResponseDto>>> getReservations(User user) {
        // 로그인 한 유저의 예약 목록 조회
        List<Reservation> reservationList = reserveRepository.findAllByUser(user);
        Map<String, List<ReserveResponseDto>> map = new HashMap<>();
        map.put("reserveList", reservationList.stream().map(ReserveResponseDto::new).collect(Collectors.toList()));
        return ResponseEntity.ok(map);
    }

    @Transactional
    public ResponseEntity<MessageDto> deleteReservation(Long reservationId, User user) {
        // 유저가 삭제 시도한 예약 기록의 존재 여부 확인(존재하지 않을 시 예외처리)
        Reservation reservation = reserveRepository.findById(reservationId).orElseThrow(
                () -> new CustomException(ErrorMessage.NON_EXIST_POST)
        );

        // 해당 예약 기록이 유저 본인의 것이 맞는지 확인(그렇지 않을 경우 예외처리)
        if(!isYourReserve(reservation, user)) {
            throw new CustomException(ErrorMessage.CANNOT_CANCLE_RESERVATION);
        }

        // 예약 기록 삭제
        reserveRepository.delete(reservation);
        return ResponseEntity.ok(new MessageDto("예약이 취소되었습니다."));
    }

    @Transactional
    public synchronized ResponseEntity<MessageDto> createReservation(Long roomId, ReservationRequestDto reservationRequestDto, User user) {
        // 유저가 예약하려는 숙소가 등록된 숙소가 맞는지 확인(아닐 경우 예외처리)
        Room room = roomRepository.findById(roomId).orElseThrow(
                () -> new CustomException(ErrorMessage.NON_EXIST_POST)
        );

        // 숙소 가용 인원을 초과하지 않는지 확인
        if (room.getCapacity() < reservationRequestDto.getGuests()) {
            throw new CustomException(ErrorMessage.OVER_ROOM_CAPACITY);
        }

        // 예약 가능한 날짜가 맞는지 확인
        if (!isAvailable(room, reservationRequestDto)) { // A 트랜잭션이 커밋되기 전에 B 트랜잭션이 확인을 진행하면 안됌. 그렇기에 확인 부분에 비관적 락 도입
            throw new CustomException(ErrorMessage.ALREADY_RESERVED);
        }

        // 예약
        Reservation reservation = new Reservation(reservationRequestDto, room, user);
        reserveRepository.save(reservation);
        return ResponseEntity.ok(new MessageDto("예약을 성공했습니다."));
    }


    private boolean isYourReserve(Reservation reservation, User user) {
        return reservation.getUser().equals(user);
    }

    private boolean isAvailable(Room room, ReservationRequestDto reservationRequestDto) {
        LocalDate userCheckIn = reservationRequestDto.getCheckIn();
        LocalDate userCheckOut = reservationRequestDto.getCheckOut();

        // 예약자가 희망하는 checkIn, checkOut 사이에 다른 사람이 예약이 있으면 예약 불가능
        List<Reservation> reservations = reserveRepository.findAllByRoomReserved(room, userCheckIn, userCheckOut);
        return reservations.size() == 0;
    }
}
