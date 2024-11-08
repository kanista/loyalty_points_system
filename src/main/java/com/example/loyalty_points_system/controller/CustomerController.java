package com.example.loyalty_points_system.controller;

import com.example.loyalty_points_system.dto.CommonApiResponse;
import com.example.loyalty_points_system.dto.CustomerPointsResponseDTO;
import com.example.loyalty_points_system.dto.CustomerRequestDTO;
import com.example.loyalty_points_system.entity.Customer;
import com.example.loyalty_points_system.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<CommonApiResponse<Customer>> createCustomer(@RequestBody CustomerRequestDTO customerRequestDTO) {
        Customer createdCustomer = customerService.createCustomer(customerRequestDTO);
        CommonApiResponse<Customer> response = new CommonApiResponse<>(HttpStatus.OK.value(), "Customer created successfully", createdCustomer);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @PostMapping("/{customer_id}/add-points")
    public ResponseEntity<CommonApiResponse<Void>> addPoints(@PathVariable Long customer_id, @RequestParam int points) {
        customerService.addLoyaltyPoints(customer_id, points);
        CommonApiResponse<Void> response = new CommonApiResponse<>(HttpStatus.OK.value(), "Points added successfully", null);
        return ResponseEntity.ok(response);

    }

    @PostMapping("/{customer_id}/redeem-points")
    public ResponseEntity<CommonApiResponse<Void>> redeemPoints(@PathVariable Long customer_id, @RequestParam int points) {
        customerService.redeemPoints(customer_id, points);
        CommonApiResponse<Void> response = new CommonApiResponse<>(HttpStatus.OK.value(), "Points redeemed successfully", null);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{customer_id}/points")
    public ResponseEntity<CommonApiResponse<CustomerPointsResponseDTO>> getCustomerPoints(@PathVariable Long customer_id) {
        CustomerPointsResponseDTO pointsResponse = customerService.getCustomerPoints(customer_id);
        CommonApiResponse<CustomerPointsResponseDTO> response = new CommonApiResponse<>(HttpStatus.OK.value(), "Points retrieved successfully for customer ID " + customer_id, pointsResponse);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all-with-points")
    public ResponseEntity<CommonApiResponse<List<CustomerPointsResponseDTO>>> getAllCustomersWithPoints() {
        List<CustomerPointsResponseDTO> customersWithPoints = customerService.getAllCustomersWithPoints();
        CommonApiResponse<List<CustomerPointsResponseDTO>> response = new CommonApiResponse<>(
                HttpStatus.OK.value(),
                "All customers with their points retrieved successfully",
                customersWithPoints
        );
        return ResponseEntity.ok(response);
    }
}
