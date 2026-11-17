package com.example.client;

import com.example.client.entities.Client;
import com.example.client.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class ClientServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner initH2(ClientRepository repository) {
        return args -> {
            repository.save(new Client(null, "Amine Alami", 24));
            repository.save(new Client(null, "Salma Bennani", 28));
            repository.save(new Client(null, "Karim Tazi", 35));

            System.out.println("=== CLIENTS ENREGISTRES DANS H2 ===");
            repository.findAll().forEach(c -> System.out.println(c.getId() + ": " + c.getName() + " (" + c.getAge() + " ans)"));
        };
    }
}
