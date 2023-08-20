package com.flightsearch.flightsearchapi.service;

import com.flightsearch.flightsearchapi.dto.AirportDTO;
import com.flightsearch.flightsearchapi.entity.Airport;
import com.flightsearch.flightsearchapi.entity.Flight;
import com.flightsearch.flightsearchapi.entity.UserInfo;
import com.flightsearch.flightsearchapi.exception.FlightSearchException;
import com.flightsearch.flightsearchapi.exception.FlightSearchExceptions;
import com.flightsearch.flightsearchapi.repository.AirportRepository;
import com.flightsearch.flightsearchapi.repository.FlightRepository;
import com.flightsearch.flightsearchapi.repository.UserInfoRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AirportService {

    private AirportRepository airportRepository;
    private FlightRepository flightRepository;
    private UserInfoRepository userInfoRepository;
    private PasswordEncoder passwordEncoder;


    public Airport delete(Long id) throws FlightSearchException {
        Optional<Airport> airportOptional = airportRepository.findById(id);

        if (airportOptional.isEmpty()) {
            throw FlightSearchExceptions.AIRPORT_NOT_FOUND;
        }

        Airport airport = airportOptional.get();


        List<Flight> flightsWithDeparture = flightRepository.findByDepartureAirport(airport);
        flightsWithDeparture.forEach(flight -> flight.setDepartureAirport(null));
        flightRepository.saveAll(flightsWithDeparture);

        List<Flight> flightsWithArrival = flightRepository.findByArrivalAirport(airport);
        flightsWithArrival.forEach(flight -> flight.setArrivalAirport(null));
        flightRepository.saveAll(flightsWithArrival);

        airportRepository.delete(airport);

        return airport;
    }

    public List<Airport> getAll(){
        return airportRepository.findAll();
    }

    public UserInfo createUser(UserInfo userInfo){
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        return userInfoRepository.save(userInfo);
    }

}
