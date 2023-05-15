package com.sparta.cloneproject_be;

import com.sparta.cloneproject_be.dto.ReservationRequestDto;
import com.sparta.cloneproject_be.entity.Reservation;
import com.sparta.cloneproject_be.entity.User;
import com.sparta.cloneproject_be.exception.CustomException;
import com.sparta.cloneproject_be.repository.ReserveRepository;
import com.sparta.cloneproject_be.repository.UserRepository;
import com.sparta.cloneproject_be.service.ReserveService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
class CloneProjectBeApplicationTests {

    @Autowired
    private ReserveService reserveService;
    @Autowired
    private ReserveRepository reserveRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void conCurrencyTests() throws InterruptedException { // 예약하기 동시성 문제 테스트(synchronized + 비관적 락을 통해 해결 왜 되는지?)
        // Given
        User user1 = userRepository.findById(1L).orElseThrow();
        User user2 = userRepository.findById(1L).orElseThrow();

        ReservationRequestDto reservationRequestDto1 = new ReservationRequestDto("2023-06-06", "2023-06-10", 1);
        ReservationRequestDto reservationRequestDto2 = new ReservationRequestDto("2023-06-08", "2023-06-15", 1);

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        CountDownLatch latch = new CountDownLatch(2);


        // When
        executorService.execute(() -> {
            try {
                reserveService.createReservation(1L, reservationRequestDto1, user1);
            } catch (CustomException e) {
                System.out.println(e.getErrorMessage().getMessage());
            }
            latch.countDown();
        });

        executorService.execute(() -> {
            try {
                reserveService.createReservation(1L, reservationRequestDto2, user2);
            } catch (CustomException e) {
                System.out.println(e.getErrorMessage().getMessage());
            }
            latch.countDown();
        });
        latch.await();

        // Then
        List<Reservation> reservations = reserveRepository.findAllByRoomId(1L);
        Assertions.assertEquals(1, reservations.size());
    }

    @Test
    @Transactional
    void checkDoubleReserve() { // 이미 예약이 있는 날짜에 중복 예약이 되는지 확인(원하는 대로 exception 처리 되고 있음)
        User user1 = userRepository.findById(1L).orElseThrow();
        User user2 = userRepository.findById(1L).orElseThrow();

        ReservationRequestDto reservationRequestDto1 = new ReservationRequestDto("2023-06-06", "2023-06-10", 1);
        ReservationRequestDto reservationRequestDto2 = new ReservationRequestDto("2023-06-08", "2023-06-15", 1);

        try {
            reserveService.createReservation(1L, reservationRequestDto1, user1);
            reserveService.createReservation(1L, reservationRequestDto2, user2);
        } catch (CustomException e) {
            System.out.println(e.getErrorMessage().getMessage());
        }
    }
}
