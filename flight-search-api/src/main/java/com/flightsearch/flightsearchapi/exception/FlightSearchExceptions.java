package com.flightsearch.flightsearchapi.exception;

public interface FlightSearchExceptions {

    FlightSearchException FLIGHT_NOT_FOUND = new FlightSearchException(1000, "Flight not found!");
    FlightSearchException INVALID_DEPARTURE = new FlightSearchException(1001, "Invalid departure airport");
    FlightSearchException INVALID_ARRIVAL = new FlightSearchException(1002, "Invalid arrival airport!");
    FlightSearchException INVALID_PRICE = new FlightSearchException(1003, "Invalid price!");
    FlightSearchException EXIST_FLIGHT = new FlightSearchException(1004, "Flight already exist!");
    FlightSearchException INVALID_ARRIVAL_TIME = new FlightSearchException(1004, "Invalid arrival time!");
    FlightSearchException INVALID_DEPARTURE_TIME = new FlightSearchException(1004, "Invalid departure time!");
    FlightSearchException AIRPORT_NOT_FOUND = new FlightSearchException(1000, "Airport not found!");
}
