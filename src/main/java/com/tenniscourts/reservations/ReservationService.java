package com.tenniscourts.reservations;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.guests.GuestMapper;
import com.tenniscourts.guests.GuestService;
import com.tenniscourts.schedules.ScheduleMapper;
import com.tenniscourts.schedules.ScheduleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@AllArgsConstructor
public class ReservationService {

    private final ReservationRepository repository;

    private final ReservationMapper mapper;

    private final ScheduleService scheduleService;

    private final ScheduleMapper scheduleMapper;

    private final GuestService guestService;

    private final GuestMapper guestMapper;

    public ReservationDTO bookReservation(CreateReservationRequestDTO createReservationRequestDTO) {
        var guest = guestService.findById(createReservationRequestDTO.getGuestId());
        var schedule = scheduleService.findSchedule(createReservationRequestDTO.getScheduleId());
        var booked = Reservation.builder()
                .guest(guestMapper.map(guest))
                .schedule(scheduleMapper.map(schedule))
                .reservationStatus(ReservationStatus.READY_TO_PLAY)
                .refundValue(BigDecimal.TEN)
                .value(BigDecimal.TEN)
                .build();
        return mapper.map(repository.save(booked));
    }

    public ReservationDTO findReservation(Long reservationId) {
        return repository.findById(reservationId)
                .map(mapper::map)
                .orElseThrow( () -> { throw new EntityNotFoundException("Reservation not found."); });
    }

    public ReservationDTO cancelReservation(Long reservationId) {
        return mapper.map(this.cancel(reservationId));
    }

    private Reservation cancel(Long reservationId) {
        return repository.findById(reservationId).map(reservation -> {

            this.validateCancellation(reservation);

            BigDecimal refundValue = getRefundValue(reservation);
            return this.updateReservation(reservation, refundValue, ReservationStatus.CANCELLED);

        }).orElseThrow(() -> {
            throw new EntityNotFoundException("Reservation not found.");
        });
    }

    private Reservation updateReservation(Reservation reservation, BigDecimal refundValue, ReservationStatus status) {
        reservation.setReservationStatus(status);
        reservation.setValue(reservation.getValue().subtract(refundValue));
        reservation.setRefundValue(refundValue);

        return repository.save(reservation);
    }

    private void validateCancellation(Reservation reservation) {
        if (!ReservationStatus.READY_TO_PLAY.equals(reservation.getReservationStatus())) {
            throw new IllegalArgumentException("Cannot cancel/reschedule because it's not in ready to play status.");
        }

        if (reservation.getSchedule().getStartDateTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Can cancel/reschedule only future dates.");
        }
    }

    public BigDecimal getRefundValue(Reservation reservation) {
        return calculateRefund(
                reservation.getSchedule().getStartDateTime(),
                reservation.getValue());
    }

    private BigDecimal calculateRefund(LocalDateTime reservationSchedule, BigDecimal reservationValue){
        var hours = ChronoUnit.HOURS.between(LocalDateTime.now(), reservationSchedule);
        var minutes = ChronoUnit.MINUTES.between(LocalDateTime.now(), reservationSchedule);
        return defineCalculatedRefundValue(hours,minutes,reservationValue);
    }

    public ReservationDTO rescheduleReservation(Long previousReservationId, Long scheduleId) {
        Reservation previousReservation = cancel(previousReservationId);

        if (scheduleId.equals(previousReservation.getSchedule().getId())) {
            throw new IllegalArgumentException("Can't reschedule the same reservation.");
        }

        previousReservation.setReservationStatus(ReservationStatus.RESCHEDULED);
        repository.save(previousReservation);

        var newReservation = bookReservation(CreateReservationRequestDTO.builder()
                .guestId(previousReservation.getGuest().getId())
                .scheduleId(scheduleId)
                .build());
        newReservation.setPreviousReservation(mapper.map(previousReservation));
        return newReservation;
    }

    private BigDecimal defineCalculatedRefundValue(long hours, long minutes, BigDecimal reservationValue) {
        if (hours >= 24L) return reservationValue;
        if (hours >= 12L) return reservationValue.multiply(new BigDecimal(0.75));
        if (hours >= 2L) return reservationValue.multiply(new BigDecimal(0.50));
        if (minutes >= 1L) return reservationValue.multiply(new BigDecimal(0.25));
        return BigDecimal.ZERO;
    }
}