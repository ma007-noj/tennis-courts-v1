package com.tenniscourts.reservations;


import com.tenniscourts.guests.Guest;
import com.tenniscourts.reservations.CreateReservationRequestDTO;
import com.tenniscourts.reservations.Reservation;
import com.tenniscourts.reservations.ReservationStatus;
import com.tenniscourts.schedules.Schedule;
import com.tenniscourts.tenniscourts.TennisCourt;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ReservationFixture {

    public static Reservation reservation() {
        Guest guest = guest();

        TennisCourt tennisCourt = new TennisCourt("Some Tournament");
        Schedule schedule = schedule(tennisCourt);
        schedule.setId(Long.valueOf(1));

        Reservation reservation = reservation(guest, schedule);
        reservation.setId(Long.valueOf(1));
        return reservation;
    }

    public static Reservation reservation(Guest guest, Schedule schedule) {
        return Reservation.builder()
                .guest(guest)
                .value(BigDecimal.valueOf(10))
                .reservationStatus(ReservationStatus.READY_TO_PLAY)
                .schedule(schedule)
                .refundValue(BigDecimal.valueOf(5))
                .build();
    }

    public static Guest guest() {
        Guest guest = Guest.builder()
                .name("Gustavo Kuerten")
                .build();
        return guest;
    }

    public static CreateReservationRequestDTO createReservationRequestDTO() {
        return CreateReservationRequestDTO.builder()
                .guestId(Long.valueOf(1))
                .scheduleId(Long.valueOf(1))
                .build();
    }

    public static Schedule schedule(TennisCourt tennisCourt) {
        Schedule schedule = Schedule.builder()
                .tennisCourt(tennisCourt)
                .startDateTime(LocalDateTime.now().plusHours(2))
                .endDateTime(LocalDateTime.now().plusHours(1))
                .build();
        return schedule;
    }
}