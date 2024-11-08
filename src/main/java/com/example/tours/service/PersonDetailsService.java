package com.example.tours.service;

import com.example.tours.exception.EmailNotFoundException;
import com.example.tours.model.Person;
import com.example.tours.repository.PersonRepository;
import com.example.tours.security.PersonDetails;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PersonDetailsService implements UserDetailsService {

    private final PersonRepository personRepository;

    @Override
    public UserDetails loadUserByUsername(String email){
        Optional<Person> byEmail = personRepository.findByEmail(email);
        if (byEmail.isEmpty()) {
            throw new EmailNotFoundException(String.format("Username %s not found!", email));
        }
        return new PersonDetails(byEmail.get());
    }
}
