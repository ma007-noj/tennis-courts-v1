package com.tenniscourts.tenniscourts;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface TennisCourtAPI {

    @ApiOperation(value = "Add a tennis court")
    @ApiResponse(message = "A tennis court has been added.", code = 201)
    ResponseEntity<Void> addTennisCourt(@RequestBody TennisCourtDTO tennisCourtDTO);

    @ApiOperation(value = "Query a tennis court by the given identifier")
    @ApiResponse(message = "Done.", code = 200)
    ResponseEntity<TennisCourtDTO> findTennisCourtById(@PathVariable(value = "tennisCourtId") Long tennisCourtId);

    @ApiOperation(value = "Query a tennis court by its identifier and show tennis court and its schedule")
    @ApiResponse(message = "Done.", code = 200)
    ResponseEntity<TennisCourtDTO> findTennisCourtWithSchedulesById(@PathVariable(value = "tennisCourtId") Long tennisCourtId);

    @ApiOperation(value = "List all tennis courts")
    @ApiResponse(code = 200, message = "Success")
    ResponseEntity<List<TennisCourtDTO>> list();
}