package com.example.loyalty_points_system.service;

import com.example.loyalty_points_system.dto.CustomerPointsResponseDTO;
import com.example.loyalty_points_system.dto.CustomerRequestDTO;
import com.example.loyalty_points_system.entity.Customer;
import com.example.loyalty_points_system.entity.LoyaltyPoint;
import com.example.loyalty_points_system.exception.GlobalExceptionHandler;
import com.example.loyalty_points_system.repository.CustomerRepository;
import com.example.loyalty_points_system.repository.LoyaltyPointRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final LoyaltyPointRepository loyaltyPointRepository;

    public CustomerService(CustomerRepository customerRepository, LoyaltyPointRepository loyaltyPointRepository ) {
        this.customerRepository = customerRepository;
        this.loyaltyPointRepository = loyaltyPointRepository;
    }

    public Customer createCustomer(CustomerRequestDTO customerRequestDTO) {
        if (customerRepository.findByEmail(customerRequestDTO.getEmail()).isPresent()) {
            throw new GlobalExceptionHandler.EmailAlreadyExistsException("A user with this email already exists.");
        }

        Customer customer = new Customer();
        customer.setName(customerRequestDTO.getName());
        customer.setEmail(customerRequestDTO.getEmail());
        customer.setAddress(customerRequestDTO.getAddress());
        customer.setPhone(customerRequestDTO.getPhone());
        return customerRepository.save(customer);
    }


    public void addLoyaltyPoints(Long customerId, int points) {
        if (points <= 0) {
            throw new GlobalExceptionHandler.InvalidPointsException("Points must be a positive number");
        }

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new GlobalExceptionHandler.CustomerNotFoundException("Customer not found"));

        customer.setTotalLoyaltyPoints(customer.getTotalLoyaltyPoints() + points);
        customerRepository.save(customer);

        LoyaltyPoint loyaltyPoint = LoyaltyPoint.builder()
                .customer(customer)
                .points(points)
                .date(LocalDate.now())
                .redeemed(false)
                .build();
        loyaltyPointRepository.save(loyaltyPoint);
    }


    public void redeemPoints(Long customerId, int points) {
        if (points <= 0) {
            throw new GlobalExceptionHandler.InvalidPointsException("Points must be a positive number");
        }
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new GlobalExceptionHandler.CustomerNotFoundException("Customer not found with ID: " + customerId));
        if (customer.getTotalLoyaltyPoints() >= points) {
            customer.setTotalLoyaltyPoints(customer.getTotalLoyaltyPoints() - points);
            customerRepository.save(customer);
        } else {
            throw new GlobalExceptionHandler.InsufficientPointsException("Insufficient loyalty points. Available points: " + customer.getTotalLoyaltyPoints());
        }
    }

    public CustomerPointsResponseDTO getCustomerPoints(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new GlobalExceptionHandler.CustomerNotFoundException("Customer not found"));
        String lastUpdated = LocalDateTime.now().toString();
        return new CustomerPointsResponseDTO(customer.getId(),customer.getName(),customer.getEmail(),customer.getPhone(), customer.getTotalLoyaltyPoints(), lastUpdated);
    }

    public List<CustomerPointsResponseDTO> getAllCustomersWithPoints() {
        List<Customer> customers = customerRepository.findAll();

        if (customers.isEmpty()) {
            return new ArrayList<>();
        }

        List<CustomerPointsResponseDTO> response = new ArrayList<>();

        for (Customer customer : customers) {
            String lastUpdated = LocalDateTime.now().toString();
            CustomerPointsResponseDTO dto = new CustomerPointsResponseDTO(
                    customer.getId(),
                    customer.getName(),
                    customer.getEmail(),
                    customer.getPhone(),
                    customer.getTotalLoyaltyPoints(),
                    lastUpdated
            );
            response.add(dto);
        }

        return response;
    }
}

