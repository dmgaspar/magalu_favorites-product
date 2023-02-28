package com.magalu.favorites.product.controller;


import java.util.ArrayList;
import java.util.List;

import com.magalu.favorites.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.magalu.favorites.product.exception.*;
import com.magalu.favorites.product.model.Client;
import com.magalu.favorites.product.repository.ClientRepository;

//@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/clients")
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        List<Client> clients = clientRepository.findByEmail(client.getEmail());
        if(!clients.isEmpty())
        {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Client _client =  clientRepository.save(new Client(client.getName(), client.getEmail()));
        return new ResponseEntity<>(_client, HttpStatus.CREATED);
    }
    @GetMapping("/clients")
    public ResponseEntity<List<Client>> getAllClients(@RequestParam(required = false) String email) {
        List<Client> clients = new ArrayList<Client>();

        if (email == null)
            clientRepository.findAll().forEach(clients::add);
        else
            clientRepository.findByEmail(email).forEach(clients::add);


        if (clients.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @GetMapping("/clients/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable("id") long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found client with id = " + id));

        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @PutMapping("/clients/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable("id") long id, @RequestBody Client client) {
        Client _client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Client with id = " + id));

        _client.setName(client.getName());
        _client.setEmail(client.getEmail());

        return new ResponseEntity<>(clientRepository.save(_client), HttpStatus.OK);
    }
    @DeleteMapping("/clients/{id}")
    public ResponseEntity<HttpStatus> deleteClient(@PathVariable("id") long id) {
        clientRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
