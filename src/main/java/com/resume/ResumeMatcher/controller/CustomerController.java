package com.resume.ResumeMatcher.controller;


import com.resume.ResumeMatcher.dto.CustomerRequestDTO;
import com.resume.ResumeMatcher.dto.CustomerResponseDTO;
import com.resume.ResumeMatcher.model.Customer;
import com.resume.ResumeMatcher.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

     private final CustomerService customerService;

     public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
     }

     @PostMapping("/register")
     public ResponseEntity<CustomerResponseDTO> register(@RequestBody @Valid CustomerRequestDTO request){
         return ResponseEntity.ok(customerService.registerCustomer(request));
     }

     @GetMapping
     public ResponseEntity<List<Customer>> getAll(){
         return ResponseEntity.ok(customerService.getAllCustomers());
     }
}
