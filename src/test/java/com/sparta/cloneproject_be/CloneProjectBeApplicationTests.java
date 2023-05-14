package com.sparta.cloneproject_be;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

@SpringBootTest
class CloneProjectBeApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void getDates() {
        LocalDate today = LocalDate.now(); // 2023-05-13
        LocalDate expiredDate = LocalDate.of(2023, 6, 25); // 2023-06-25

        LocalDate checkIn = LocalDate.of(2023, 5, 20);
        LocalDate checkOut = LocalDate.of(2023, 5, 25);

        Set<LocalDate> enableDates = new LinkedHashSet<>();

        int betweenDays = (int) Duration.between(today.atStartOfDay(), expiredDate.atStartOfDay()).toDays();
        for (int i = 0; i <= betweenDays; i++) {
            enableDates.add(today.plusDays(i));
        }

        int betweeNDays2 = (int) Duration.between(checkIn.atStartOfDay(), checkOut.atStartOfDay()).toDays();
        for (int i = 0; i <= betweeNDays2; i++) {
            LocalDate date = checkIn.plusDays(i);
            if (enableDates.contains(date)) {
                enableDates.remove(date);
            }
        }

        for(LocalDate enableDate : enableDates) {
            System.out.println(enableDate);
        }
    }

}
