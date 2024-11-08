package com.example.loyalty_points_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommonApiResponse <T>{
    private int status;
    private String message;
    private T data;

}
