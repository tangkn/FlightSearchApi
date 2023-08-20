package com.flightsearch.flightsearchapi.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.flightsearch.flightsearchapi.common.RestResponse;
import com.flightsearch.flightsearchapi.common.RestResponseUtils;
import com.flightsearch.flightsearchapi.dto.FlightDTO;
import com.flightsearch.flightsearchapi.dto.Views;
import com.flightsearch.flightsearchapi.entity.Airport;
import com.flightsearch.flightsearchapi.entity.Flight;
import com.flightsearch.flightsearchapi.exception.FlightSearchException;
import com.flightsearch.flightsearchapi.repository.AirportRepository;
import com.flightsearch.flightsearchapi.service.FlightService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/portal/flight")
public class FlightRestController {

    @Autowired
    private FlightService flightService;
    private AirportRepository airportRepository;
    @Operation(summary = "Save flight without id", description = "Returns created flight info")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/save")
    public RestResponse<FlightDTO> save(@RequestBody FlightDTO flightDTO) throws FlightSearchException {
        return RestResponseUtils.success(flightService.save(flightDTO));
    }

    @Operation(summary = "Get all flights", description = "Returns all flights info")
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getAll")
    public RestResponse<List<Flight>> getAll()  {
        return RestResponseUtils.success(flightService.getAll());
    }

    @Operation(summary = "Delete flight by id", description = "Returns deleted flight info")
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public RestResponse<Optional<Flight>> delete(@PathVariable Long id) throws FlightSearchException {
        return RestResponseUtils.success(flightService.delete(id));
    }

    @Operation(summary = "Update an exist flight", description = "Returns updated flight info")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/update")
    public RestResponse<FlightDTO> update(@RequestBody FlightDTO flightDTO) throws FlightSearchException {
        return RestResponseUtils.success(flightService.update(flightDTO));
    }

    @Operation(summary = "Search all available flight by given info (enter time as string)", description = "Returns all available flight info")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/search")
    public RestResponse<List<Flight>> getFlights(
            @RequestParam String departureCity,
            @RequestParam String arrivalCity,
            @RequestParam String departureTime,
            @RequestParam(required = false) String returnTime
    )  {
        return RestResponseUtils.success(flightService.searchFlights(departureCity, arrivalCity, departureTime, returnTime));
    }

}
