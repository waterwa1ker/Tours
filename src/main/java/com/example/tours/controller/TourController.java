package com.example.tours.controller;

import com.example.tours.model.Tour;
import com.example.tours.service.TourService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tours")
@AllArgsConstructor
public class TourController {

    private final TourService tourService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public List<Tour> findAll() {
        return tourService.findAll();
    }

}
