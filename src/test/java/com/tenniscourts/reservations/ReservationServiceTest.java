package com.tenniscourts.reservations;

import com.tenniscourts.guests.GuestMapper;
import com.tenniscourts.guests.GuestService;
import com.tenniscourts.schedules.Schedule;
import com.tenniscourts.schedules.ScheduleMapper;
import com.tenniscourts.schedules.ScheduleService;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = ReservationService.class)
public class ReservationServiceTest {

    @InjectMocks
    ReservationService reservationService;

    @Mock
    private ReservationRepository repository;

    @Mock
    private ReservationMapper mapper;

    @Mock
    private ScheduleService scheduleService;

    @Mock
    private ScheduleMapper scheduleMapper;

    @Mock
    private GuestService guestService;

    @Mock
    private GuestMapper guestMapper;

    @Test
    public void getRefundValueFullRefund() {
        var schedule = new Schedule();
        var startDateTime = LocalDateTime.now().plusDays(2);
        schedule.setStartDateTime(startDateTime);

        Assert.assertEquals(reservationService.getRefundValue(Reservation.builder().schedule(schedule).value(BigDecimal.TEN).build()), BigDecimal.TEN);
    }

    @Test
    public void refundTest() {
        var schedule = Schedule.builder().build();
        var startDateTime = LocalDateTime.now().plusDays(2);
        schedule.setStartDateTime(startDateTime);

        Assert.assertEquals(BigDecimal.TEN,
                reservationService.getRefundValue(Reservation.builder().schedule(schedule).value(BigDecimal.TEN).build()));

        startDateTime = LocalDateTime.now().plusHours(1).plusMinutes(30);
        schedule.setStartDateTime(startDateTime);

        Assert.assertEquals(BigDecimal.valueOf(2.5).setScale(2),
                reservationService.getRefundValue(Reservation.builder().schedule(schedule).value(BigDecimal.TEN).build()));

        startDateTime = LocalDateTime.now().plusHours(9).plusMinutes(30);
        schedule.setStartDateTime(startDateTime);

        Assert.assertEquals(BigDecimal.valueOf(5).setScale(1),
                reservationService.getRefundValue(Reservation.builder().schedule(schedule).value(BigDecimal.TEN).build()));

        startDateTime = LocalDateTime.now().plusHours(11).plusMinutes(30);
        schedule.setStartDateTime(startDateTime);

        Assert.assertEquals(BigDecimal.valueOf(5.00).setScale(2),
                reservationService.getRefundValue(Reservation.builder().schedule(schedule).value(BigDecimal.TEN).build()).setScale(2));
    }

}