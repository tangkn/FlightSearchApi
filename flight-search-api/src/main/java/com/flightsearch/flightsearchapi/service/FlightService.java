package com.flightsearch.flightsearchapi.service;

import com.flightsearch.flightsearchapi.dto.FlightDTO;
import com.flightsearch.flightsearchapi.entity.Airport;
import com.flightsearch.flightsearchapi.entity.Flight;
import com.flightsearch.flightsearchapi.exception.FlightSearchException;
import com.flightsearch.flightsearchapi.exception.FlightSearchExceptions;
import com.flightsearch.flightsearchapi.repository.AirportRepository;
import com.flightsearch.flightsearchapi.repository.FlightRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FlightService {

    private FlightRepository flightRepository;
    private AirportRepository airportRepository;

    @Transactional
    public FlightDTO save(FlightDTO flightDTO) throws FlightSearchException {
        validateFlightDTO(flightDTO);

        var departureAirport = new Airport();
        departureAirport.setCity(flightDTO.getDepartureAirport().getCity());
        airportRepository.saveAndFlush(departureAirport);

        var arrivalAirport = new Airport();
        arrivalAirport.setCity(flightDTO.getArrivalAirport().getCity());
        airportRepository.saveAndFlush(arrivalAirport);

        var flight = convertToFlight(flightDTO);
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flightRepository.save(flight);

        return convertToDTO(flight);
    }
    @Transactional
    public FlightDTO update(FlightDTO flightDTO){
        var flight= flightRepository.findFlightById(flightDTO.getId());

        var departureAirport=airportRepository.findAirportById(flightDTO.getDepartureAirport().getId());
        departureAirport.setCity(flightDTO.getDepartureAirport().getCity());

        var arrivalAirport=airportRepository.findAirportById(flightDTO.getArrivalAirport().getId());
        arrivalAirport.setCity(flightDTO.getArrivalAirport().getCity());

        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(flightDTO.getDepartureTime());
        flight.setReturnTime(flightDTO.getReturnTime());
        flight.setPrice(flightDTO.getPrice());
        flightRepository.saveAndFlush(flight);

        return convertToDTO(flight);
    }

    @Transactional
    public Optional<Flight> delete(Long id) throws FlightSearchException {
        Optional<Flight> flight = flightRepository.findById(id);
        if (flight.isEmpty()) {
            throw FlightSearchExceptions.FLIGHT_NOT_FOUND;
        }
        flightRepository.deleteFlightById(id);
        airportRepository.deleteById(flight.get().getArrivalAirport().getId());
        airportRepository.deleteById(flight.get().getDepartureAirport().getId());
        return flight;
    }

    public List<Flight> searchFlights(String departureCity, String arrivalCity, String departureTimeString, String returnTimeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime departureTime = LocalDateTime.parse(departureTimeString, formatter);
        LocalDateTime returnTime = returnTimeString != null ? LocalDateTime.parse(returnTimeString, formatter) : null;

        List<Flight> flights;

        if (returnTime == null) {
            flights = flightRepository.findByDepartureAndArrivalCitiesAndDepartureTimeIgnoreCaseAndReturnTimeIsNull(
                    departureCity, arrivalCity, departureTime);
        } else {
            flights = flightRepository.findByDepartureAndArrivalCitiesAndDepartureTimeIgnoreCaseAndReturnTimeIsNotNull(
                    departureCity, arrivalCity, departureTime);
        }

        return flights;
    }
    private void validateFlightDTO(FlightDTO flightDTO) throws FlightSearchException {
        if (flightDTO.getArrivalAirport() == null) {
            throw FlightSearchExceptions.INVALID_ARRIVAL;
        }
        if (flightDTO.getDepartureAirport() == null) {
            throw FlightSearchExceptions.INVALID_DEPARTURE;
        }
        if (flightDTO.getPrice() <= 0.0) {
            throw FlightSearchExceptions.INVALID_PRICE;
        }
        if (flightDTO.getDepartureTime() == null) {
            throw FlightSearchExceptions.INVALID_DEPARTURE_TIME;
        }
    }

    private Flight convertToFlight(FlightDTO flightDTO) {
        Airport departureAirport = new Airport();
        departureAirport.setCity(flightDTO.getDepartureAirport().getCity());

        Airport arrivalAirport = new Airport();
        arrivalAirport.setCity(flightDTO.getArrivalAirport().getCity());

        return Flight.builder()
                .departureAirport(departureAirport)
                .arrivalAirport(arrivalAirport)
                .departureTime(flightDTO.getDepartureTime())
                .returnTime(flightDTO.getReturnTime())
                .price(flightDTO.getPrice())
                .build();
    }

    private FlightDTO convertToDTO(Flight flight) {
        FlightDTO flightDTO = new FlightDTO();

        var departureAirport = new Airport();
        departureAirport.setCity(flight.getDepartureAirport().getCity());

        var arrivalAirport = new Airport();
        arrivalAirport.setCity(flight.getArrivalAirport().getCity());

        flightDTO.setDepartureAirport(departureAirport);
        flightDTO.setArrivalAirport(arrivalAirport);
        flightDTO.setDepartureTime(flight.getDepartureTime());
        flightDTO.setReturnTime(flight.getReturnTime());
        flightDTO.setPrice(flight.getPrice());

        return flightDTO;
    }

    public List<Flight> getAll() {
        return flightRepository.findAll();
    }
}
/*
 List<Flight> flights = new ArrayList<>();

      if (returnTime == null) {
          // Tek yönlü uçuş
          List<Flight> oneWayFlights = flightRepository.findByDepartureAirportIdAndArrivalAirportIdAndDepartureTimeGreaterThanEqualAndReturnTimeIsNull(
                  departureAirportId, arrivalAirportId, departureTime);
          flights.addAll(oneWayFlights);
      } else {
          // Çift yönlü uçuş
          List<Flight> departureFlights = flightRepository.findByDepartureAirportIdAndArrivalAirportIdAndDepartureTimeGreaterThanEqualAndReturnTimeIsNotNull(
                  departureAirportId, arrivalAirportId, departureTime);
          List<Flight> returnFlights = flightRepository.findByDepartureAirportIdAndArrivalAirportIdAndDepartureTimeGreaterThanEqualAndReturnTimeIsNotNull(
                  arrivalAirportId, departureAirportId, returnTime);
          flights.addAll(departureFlights);
          flights.addAll(returnFlights);
      }

      return flights;
 */