package com.example.car.services;

import com.example.car.entities.Car;
import com.example.car.models.CarResponse;
import com.example.car.models.Client;
import com.example.car.repositories.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${client.service.url:http://localhost:8888/SERVICE-CLIENT/api/client/}")
    private String clientServiceUrl;

    public void setClientServiceUrl(String url) {
        this.clientServiceUrl = url;
    }

    public List<CarResponse> findAll() {
        List<Car> cars = carRepository.findAll();
        return cars.stream()
                .map(this::mapToCarResponse)
                .collect(Collectors.toList());
    }

    public CarResponse findById(Long id) throws Exception {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new Exception("Voiture non trouvée avec l'ID: " + id));
        return mapToCarResponse(car);
    }

    public Car save(Car car) {
        return carRepository.save(car);
    }

    private CarResponse mapToCarResponse(Car car) {
        Client client = null;
        try {
            if (car.getClient_id() != null) {
                client = restTemplate.getForObject(clientServiceUrl + car.getClient_id(), Client.class);
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération du client via RestTemplate: " + e.getMessage());
        }

        return CarResponse.builder()
                .id(car.getId())
                .brand(car.getBrand())
                .model(car.getModel())
                .matricule(car.getMatricule())
                .client(client)
                .build();
    }
}
