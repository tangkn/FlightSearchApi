package com.flightsearch.flightsearchapi.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.flightsearch.flightsearchapi.entity.Airport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlightDTO {

    //@JsonView(Views.Public.class)
    private Long id;
    //@JsonView(Views.Details.class)
    private LocalDateTime returnTime;
    //@JsonView(Views.Details.class)
    private LocalDateTime departureTime;
    //@JsonView(Views.Details.class)
    private double price;
    //@JsonView(Views.Details.class)
    private Airport arrivalAirport;
    //@JsonView(Views.Details.class)
    private Airport departureAirport;

}
