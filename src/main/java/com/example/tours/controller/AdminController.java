package com.example.tours.controller;

import com.example.tours.dto.TourDTO;
import com.example.tours.exception.InvalidTourException;
import com.example.tours.model.Tour;
import com.example.tours.service.TourService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasAuthority('ADMIN')")
@AllArgsConstructor
public class AdminController {

    private final ModelMapper modelMapper;
    private final TourService tourService;

    @PostMapping("/add-tour")
    public ResponseEntity<?> addTour(@RequestBody @Valid TourDTO tourDTO,
                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidTourException("Check fields");
        }
        System.out.println(tourDTO);
        Tour tour = toTour(tourDTO);
        tourService.save(tour);
        return new ResponseEntity<>("Ok", HttpStatus.OK);
    }

    private Tour toTour(TourDTO tourDTO) {
        return modelMapper.map(tourDTO, Tour.class);
    }

}
