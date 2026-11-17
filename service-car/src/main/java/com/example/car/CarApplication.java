package com.example.car;

import com.example.car.entities.Car;
import com.example.car.repositories.CarRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
public class CarApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(5000);
        requestFactory.setReadTimeout(5000);
        restTemplate.setRequestFactory(requestFactory);
        return restTemplate;
    }

    @Bean
    CommandLineRunner initH2(CarRepository carRepository) {
        return args -> {
            carRepository.save(new Car(null, "Toyota", "Corolla", "A 25 333", 1L));
            carRepository.save(new Car(null, "Renault", "Megane", "B 6 3456", 1L));
            carRepository.save(new Car(null, "Peugeot", "301", "A 55 4444", 2L));

            System.out.println("=== VOITURES ENREGISTREES DANS H2 ===");
            carRepository.findAll().forEach(c -> System.out.println(c.getId() + ": " + c.getBrand() + " " + c.getModel() + " (Client ID: " + c.getClient_id() + ")"));
        };
    }
}
