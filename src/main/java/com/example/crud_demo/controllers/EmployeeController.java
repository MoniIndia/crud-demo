package com.example.crud_demo.controllers;

import com.example.crud_demo.constants.ApiConstants;
import com.example.crud_demo.dtos.EmployeeRequest;
import com.example.crud_demo.dtos.EmployeeSearch;
import com.example.crud_demo.models.Employee;
import com.example.crud_demo.services.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static com.example.crud_demo.constants.ApiConstants.*;

@Slf4j
@RestController
@RequestMapping(ApiConstants.API_CONTEXT)
public class EmployeeController {

    private EmployeeService employeeService;

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Operation(summary = "Get all Employees")

    @GetMapping(EMPLOYEES)
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employeeList = employeeService.getAllEmployees();
        return ResponseEntity.ok().body(employeeList);
    }

    @Operation(summary = "Get Employee by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the employee",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Employee.class))}),
            @ApiResponse(responseCode = "404", description = "Employee not found with the given id",
                    content = @Content)})
    @GetMapping(EMPLOYEE_BY_ID)
    public ResponseEntity<EmployeeSearch> getEmployeeById(@PathVariable("id") long id) {
        EmployeeSearch employee = employeeService.getEmployeeById(id);
        if (employee != null) {
            return ResponseEntity.ok().body(employee);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Get all Employees by Employee Type")
    @GetMapping(EMPLOYEE_BY_TYPE)
    public ResponseEntity<List<Employee>> getEmployeeByType(@RequestParam("type") String type) {
        return ResponseEntity.ok().body(employeeService.getEmployeeByType(type));
    }

    @Operation(summary = "Get Employee records based on page number, page size and sorting field")
    @GetMapping(EMPLOYEE_BY_PAGE)
    public ResponseEntity<List<Employee>> getAllEmployees(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        List<Employee> employeeList = employeeService.getAllEmployeesPaginated(pageNo, pageSize, sortBy);

        return ResponseEntity.ok().body(employeeList);
    }

    @Operation(summary = "Create Employee")
    @PostMapping(EMPLOYEES)
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody EmployeeRequest employeeRequest) {
        Employee newEmployee = employeeService.createEmployee(employeeRequest);
        log.info("Employee record created successfully. Employee Id: {}", newEmployee.getId());
        return ResponseEntity.created(URI.create("/employees" + "/" + newEmployee.getId())).body(newEmployee);
    }

    @Operation(summary = "Update Employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee record updated successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Employee.class))}),
            @ApiResponse(responseCode = "404", description = "Employee not found with the given id",
                    content = @Content)})
    @PutMapping(EMPLOYEES)
    public ResponseEntity<Employee> updateEmployee(@RequestParam(name = "id") long id, @RequestBody EmployeeRequest employeeRequest) {
        Employee newEmployee = employeeService.updateEmployee(employeeRequest, id);
        if (newEmployee != null) {
            log.info("Employee record updated successfully. Employee Id: {}", id);
            return ResponseEntity.ok().body(newEmployee);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete an Employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Employee record deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Employee not found with the given id")})
    @DeleteMapping(EMPLOYEE_BY_ID)
    public ResponseEntity deleteEmployee(@PathVariable("id") long id) {
        if (employeeService.getEmployeeById(id) != null) {
            employeeService.deleteEmployee(id);
            log.info("Employee record deleted successfully. Employee Id: {}", id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
