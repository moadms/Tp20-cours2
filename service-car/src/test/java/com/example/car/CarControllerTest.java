package com.example.car;

import com.example.car.entities.Car;
import com.example.car.models.Client;
import com.example.car.repositories.CarRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CarRepository carRepository;

    @MockBean
    private RestTemplate restTemplate;

    @Test
    void testFindAll() throws Exception {
        given(restTemplate.getForObject(anyString(), eq(Client.class)))
                .willReturn(new Client(1L, "Amine Alami", 24));

        mockMvc.perform(get("/api/car")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testFindById() throws Exception {
        Car car = carRepository.save(new Car(null, "Audi", "A3", "E 12 345", 2L));
        given(restTemplate.getForObject(anyString(), eq(Client.class)))
                .willReturn(new Client(2L, "Salma Bennani", 28));

        mockMvc.perform(get("/api/car/" + car.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brand").value("Audi"))
                .andExpect(jsonPath("$.client.name").value("Salma Bennani"));
    }
}
