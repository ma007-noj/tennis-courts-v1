package com.tenniscourts.guests;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface GuestAPI {

    @ApiOperation(value = "Add a new guest")
    @ApiResponse(message = "New guest has been added.", code = 201)
    ResponseEntity<Void> add(@RequestBody GuestDTO guestDTO);

    @ApiOperation(value = "Find a guest by given identifier")
    @ApiResponse(message = "Done.", code = 200)
     ResponseEntity<GuestDTO> findById(@PathVariable(value = "id") Long guestId);

    @ApiOperation(value = "List all guests")
    @ApiResponse(message = "Done.", code = 200)
    ResponseEntity<List<GuestDTO>> listAllGuests();

    @ApiOperation(value = "Query a guest by the given name")
    @ApiResponse(message = "Done.", code = 200)
    ResponseEntity<Guest> findByName(String guestName);

    @ApiOperation(value = "Exclude a guest by the given identifier")
    @ApiResponse(message = "Guest excluded successfully.", code = 200)
    ResponseEntity<String> delete(@PathVariable(value = "id") Long guestId);

    @ApiOperation(value = "Update a guest by the given context")
    @ApiResponse(message = "Successfully changed.", code = 200
    )
    ResponseEntity<GuestDTO> update(@RequestBody GuestDTO guestDTO);
}