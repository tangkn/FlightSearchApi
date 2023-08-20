package com.flightsearch.flightsearchapi.repository;

import com.flightsearch.flightsearchapi.entity.Airport;
import com.flightsearch.flightsearchapi.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface FlightRepository extends JpaRepository<Flight,Long> {

    boolean existsFlightById(Long id);

    void deleteFlightById(Long id);

    Optional<Flight> findById(Long id);
    Flight findFlightById(Long id);

    @Query("SELECT f FROM Flight f WHERE LOWER(f.departureAirport.city) = LOWER(:departureCity) AND LOWER(f.arrivalAirport.city) = LOWER(:arrivalCity) AND f.departureTime >= :departureTime AND f.returnTime IS NULL")
    List<Flight> findByDepartureAndArrivalCitiesAndDepartureTimeIgnoreCaseAndReturnTimeIsNull(
            String departureCity, String arrivalCity, LocalDateTime departureTime);

    @Query("SELECT f FROM Flight f WHERE LOWER(f.departureAirport.city) = LOWER(:departureCity) AND LOWER(f.arrivalAirport.city) = LOWER(:arrivalCity) AND f.departureTime >= :departureTime AND f.returnTime IS NOT NULL")
    List<Flight> findByDepartureAndArrivalCitiesAndDepartureTimeIgnoreCaseAndReturnTimeIsNotNull(
            String departureCity, String arrivalCity, LocalDateTime departureTime);


    @Query("SELECT f FROM Flight f WHERE f.arrivalAirport.id = :id OR f.departureAirport.id = :id")
    List<Flight> findAllByAirportId(Long id);

    List<Flight> findByDepartureAirport(Airport airport);

    List<Flight> findByArrivalAirport(Airport airport);
}