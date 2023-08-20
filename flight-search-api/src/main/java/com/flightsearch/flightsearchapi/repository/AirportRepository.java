package com.flightsearch.flightsearchapi.repository;

import com.flightsearch.flightsearchapi.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AirportRepository extends JpaRepository<Airport,Long> {

    void deleteById(Long id);
    Airport findAirportById(Long id);
    @Query("SELECT a FROM Airport a WHERE a.city = ?1")
    Airport findFirstByCity(String city);

}
