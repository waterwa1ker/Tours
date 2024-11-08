package com.example.tours.controller;

import com.example.tours.dto.AuthDTO;
import com.example.tours.model.Person;
import com.example.tours.security.JwtTokenProvider;
import com.example.tours.service.PersonService;
import com.example.tours.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {

    private final RegistrationService registrationService;
    private final PersonService personService;
    private final JwtTokenProvider jwtTokenProvider;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/registration")
    public ResponseEntity<Map<Object, Object>> register(@RequestBody @Valid AuthDTO authDTO,
                                                        BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        String email = authDTO.getEmail().toLowerCase();
        Optional<Person> byEmail = personService.findByEmail(email);
        if (byEmail.isPresent()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        Person person = fromAuthDTO(authDTO);
        registrationService.register(person);
        return ResponseEntity.ok(getToken(email, person));
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthDTO authDTO){
        try{
            String email = authDTO.getEmail().toLowerCase();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, authDTO.getPassword()));
            Person person = personService.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User doesn't exist"));
            return ResponseEntity.ok(getToken(email, person));
        } catch (AuthenticationException e){
            return new ResponseEntity<>("Invalid email or password", HttpStatus.FORBIDDEN);
        }
    }

    private Map<Object, Object> getToken(String email, Person person) {
        String token = jwtTokenProvider.createToken(email, person.getRole().name());
        Map<Object, Object> response = new HashMap<>();
        response.put("email", email);
        response.put("token", token);
        return response;
    }

    private Person fromAuthDTO(AuthDTO authDTO) {
        return modelMapper.map(authDTO, Person.class);
    }

}
