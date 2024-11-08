package com.example.tours.service;

import com.example.tours.model.Tour;
import com.example.tours.repository.TourRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class TourService {

    private final TourRepository tourRepository;

    public List<Tour> findAll() {
        return tourRepository.findAll();
    }

    @Transactional
    public void save(Tour tour) {
        tourRepository.save(tour);
    }
}
