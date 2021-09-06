package com.tenniscourts.schedules;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleAPI {

    @ApiOperation(value = "Add a new schedule")
    @ApiResponse(message = "New schedule was added", code = 201)
    ResponseEntity<Void> addScheduleTennisCourt(@RequestBody CreateScheduleRequestDTO createScheduleRequestDTO);

    @ApiOperation(value = "Query schedules in a range of two dates")
    @ApiResponse(message = "Done.", code = 200)
    ResponseEntity<List<ScheduleDTO>> findSchedulesByDates(@RequestParam LocalDate startDate,
                                                                  @RequestParam LocalDate endDate);

    @ApiOperation(value = "Query schedule by a given identifier")
    @ApiResponse(message = "Done.", code = 200)
    ResponseEntity<ScheduleDTO> findByScheduleId(@PathVariable(value = "scheduleId") Long scheduleId);
}