package com.example.client.services;

import com.example.client.entities.Client;
import com.example.client.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;

    public List<Client> findAll() {
        return repository.findAll();
    }

    public Client findById(Long id) throws Exception {
        return repository.findById(id)
                .orElseThrow(() -> new Exception("Client introuvable avec l'ID: " + id));
    }

    public Client addClient(Client client) {
        return repository.save(client);
    }
}
