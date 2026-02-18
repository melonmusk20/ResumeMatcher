package com.resume.ResumeMatcher.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponseDTO {

     private Long id;
     private String name;
     private String email;

}
