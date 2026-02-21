package com.resume.ResumeMatcher.security;

import com.resume.ResumeMatcher.model.Customer;
import com.resume.ResumeMatcher.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        var customer = customerRepository.findByEmail(username).orElseThrow(() ->
                new UsernameNotFoundException("User not found"));

        return User.builder()
                .username(customer.getEmail())
                .password(customer.getPassword())
                .roles("USER")
                .build();

    }
}

