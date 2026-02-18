package com.resume.ResumeMatcher.service;

import com.resume.ResumeMatcher.config.PasswordConfig;
import com.resume.ResumeMatcher.dto.CustomerRequestDTO;
import com.resume.ResumeMatcher.dto.CustomerResponseDTO;
import com.resume.ResumeMatcher.model.Customer;
import com.resume.ResumeMatcher.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

   private final CustomerRepository customerRepository;
   private final PasswordEncoder passwordEncoder;


    public CustomerService(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public CustomerResponseDTO registerCustomer(CustomerRequestDTO request){

        // encoding password before saving

        Customer customer = new Customer();
        customer.setName(request.getName());
        customer.setEmail(request.getEmail());
        customer.setPassword(passwordEncoder.encode(request.getPassword()));
        customer.setRole("USER");

        Customer saved = customerRepository.save(customer);


      return new CustomerResponseDTO(
              saved.getId(),
              saved.getName(),
              saved.getEmail()
      );

    }

    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }


}
