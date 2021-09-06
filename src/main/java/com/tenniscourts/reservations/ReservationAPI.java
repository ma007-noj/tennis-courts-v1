package com.tenniscourts.reservations;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface ReservationAPI {

    @ApiResponse(message = "A reservation has been added.", code = 201 )
    @ApiOperation(value = "Add a new reservation")
    ResponseEntity<Void> bookReservation(@RequestBody CreateReservationRequestDTO createReservationRequestDTO);

    @ApiResponse(message = "Done.", code = 200)
    @ApiOperation(value = "Find a reservation by the given identifier")
    ResponseEntity<ReservationDTO> findReservation(@PathVariable(value = "reservationId") Long reservationId);

    @ApiResponse(message = "Done.", code = 200)
    @ApiOperation(value = "Cancel a reservation by the given identifier")
    ResponseEntity<ReservationDTO> cancelReservation(@PathVariable(value = "id") Long reservationId);

    @ApiOperation(value = "Reschedule a reservation by the given identifier")
    @ApiResponse(message = "Done.", code = 200)
    ResponseEntity<ReservationDTO> rescheduleReservation(
            @PathVariable(value = "reservationId") Long reservationId,
            @PathVariable(value = "scheduleId") Long scheduleId);
}