package com.flightsearch.flightsearchapi.controller;

import com.flightsearch.flightsearchapi.common.RestResponse;
import com.flightsearch.flightsearchapi.common.RestResponseUtils;
import com.flightsearch.flightsearchapi.entity.Airport;
import com.flightsearch.flightsearchapi.entity.UserInfo;
import com.flightsearch.flightsearchapi.exception.FlightSearchException;
import com.flightsearch.flightsearchapi.service.AirportService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/portal/airport")
@AllArgsConstructor
public class AirportRestController {

    @Autowired
    private AirportService airportService;

    @Operation(summary = "Delete Airport by id", description = "Returns deleted airport info")
    @PreAuthorize("hasAuthority('ADMIN')")
    public RestResponse<Airport> delete(@PathVariable Long id) throws FlightSearchException {
        return RestResponseUtils.success(airportService.delete(id));
    }
    @Operation(summary = "Get All available airport", description = "Returns all airport info")
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getAll")
    public RestResponse<List<Airport>> getAll() {
        return RestResponseUtils.success(airportService.getAll());
    }

}
