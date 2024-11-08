package com.example.tours.service;

import com.example.tours.model.Person;
import com.example.tours.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public Optional<Person> findByEmail(String email) {
        return personRepository.findByEmail(email);
    }
}
