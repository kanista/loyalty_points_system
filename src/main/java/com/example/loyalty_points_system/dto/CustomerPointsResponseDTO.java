package com.example.loyalty_points_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerPointsResponseDTO {
    private Long customerId;
    private String name;
    private String email;
    private String phone;
    private int points;
    private String lastUpdated;
}
