package com.flightsearch.flightsearchapi.config;

import com.flightsearch.flightsearchapi.entity.Airport;
import com.flightsearch.flightsearchapi.entity.Flight;
import com.flightsearch.flightsearchapi.repository.AirportRepository;
import com.flightsearch.flightsearchapi.repository.FlightRepository;
import lombok.AllArgsConstructor;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Component
@AllArgsConstructor
public class FlightDataUpdateJob implements Job {

    @Autowired
    private final FlightRepository flightRepository;
    @Autowired
    private final AirportRepository airportRepository;

    private final Random random = new Random();
    private final List<String> cityList = List.of("İzmir", "Bursa", "Aydın");
    private final List<String> priceList = List.of("100.99", "1500.50", "678.75");
    private final List<LocalDateTime> dateList = List.of(
            LocalDateTime.parse("2023-09-16T10:00:00"),
            LocalDateTime.parse("2023-03-17T11:30:00"),
            LocalDateTime.parse("2023-05-18T14:15:00"),
            LocalDateTime.parse("2023-07-11T16:15:00"),
            LocalDateTime.parse("2023-09-18T14:15:00"),
            LocalDateTime.parse("2023-01-17T17:15:00")

    );

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        List<Flight> mockFlights = createMockFlights();

        for (Flight mockFlight : mockFlights) {
            Airport departureAirport = mockFlight.getDepartureAirport();
            Airport arrivalAirport = mockFlight.getArrivalAirport();

            airportRepository.save(departureAirport);
            airportRepository.save(arrivalAirport);

            flightRepository.save(mockFlight);
        }
    }


    private List<Flight> createMockFlights() {
        List<Flight> mockFlights = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Flight mockFlight = new Flight();

            Airport departureAirport = new Airport();
            departureAirport.setCity(getRandomCity());
            departureAirport = airportRepository.save(departureAirport);

            Airport arrivalAirport = new Airport();
            arrivalAirport.setCity(getRandomCity());
            arrivalAirport = airportRepository.save(arrivalAirport);

            mockFlight.setDepartureAirport(departureAirport);
            mockFlight.setArrivalAirport(arrivalAirport);
            mockFlight.setDepartureTime(getRandomDate());
            mockFlight.setReturnTime(getRandomDate());
            mockFlight.setPrice(getRandomPrice());

            mockFlights.add(mockFlight);
        }

        return mockFlights;
    }


    private LocalDateTime getRandomDate() {
        return dateList.get(random.nextInt(dateList.size()));
    }

    private double getRandomPrice() {
        String priceString = priceList.get(random.nextInt(priceList.size()));
        return Double.parseDouble(priceString);
    }

    private String getRandomCity() {
        return cityList.get(random.nextInt(cityList.size()));
    }
}
