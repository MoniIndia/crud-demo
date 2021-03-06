package com.example.crud_demo.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class EmployeeRequest {
    @NotEmpty(message="first name cannot be empty")
    @NotNull(message="first name cannot be null")
    private String firstName;
    @NotEmpty(message="last name cannot be empty")
    @NotNull(message="last name cannot be null")
    private String lastName;
    private String department;
    private String employeeType;
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDate dateOfJoining;
    private String city;
    private String state;
    private String country;
}
