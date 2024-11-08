package com.example.tours.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class TourDTO {

    private long duration;

    private long price;

    private String description;

    private LocalDateTime endDate;

    private LocalDateTime startDate;

}
