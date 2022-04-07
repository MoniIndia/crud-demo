package com.example.crud_demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeSearch {
    private long id;
    private String firstName;
    private String lastName;
    private String department;
}
