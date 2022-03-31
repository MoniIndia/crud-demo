package com.example.crud_demo.services;

import com.example.crud_demo.dtos.EmployeeRequest;
import com.example.crud_demo.dtos.EmployeeSearch;
import com.example.crud_demo.models.Employee;
import com.example.crud_demo.repositories.EmployeePagingSortingRepository;
import com.example.crud_demo.repositories.EmployeeRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;
    private EmployeePagingSortingRepository employeePagingSortingRepository;

    @Autowired
    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Autowired
    public void setEmployeePagingSortingRepository(EmployeePagingSortingRepository employeePagingSortingRepository) {
        this.employeePagingSortingRepository = employeePagingSortingRepository;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public EmployeeSearch getEmployeeById(Long id) {
        EmployeeSearch employeeSearch = null;

        if (id != null) {
            Employee employee = employeeRepository.findById(id).orElse(null);
            if(employee != null){
                employeeSearch = EmployeeSearch.builder()
                        .firstName(employee.getFirstName())
                        .lastName(employee.getLastName())
                        .department(employee.getDepartment())
                        .build();
            }
        }
        return employeeSearch;
    }

    public Employee createEmployee(EmployeeRequest employee){

        Employee newEmployee = null;
        try {
            newEmployee = Employee.builder()
                    .employeeType(employee.getEmployeeType())
                    .firstName(employee.getFirstName())
                    .lastName(employee.getLastName())
                    .department(employee.getDepartment())
                    .dateOfJoining(employee.getDateOfJoining())
                    .city(employee.getCity())
                    .state(employee.getState())
                    .country(employee.getCountry())
                    .build();
            return employeeRepository.save(newEmployee);
        }
        catch (Exception exp){
            log.error("Exception while creating employee record" , exp.getMessage());
        }
        return newEmployee;
    }

    public Employee updateEmployee(EmployeeRequest employeeRequest, Long id) {
        Employee employee = null;
        try {
            Optional<Employee> employeeSearch = employeeRepository.findById(id);
            if (employeeSearch.isPresent()) {

                Employee employeeRecord = employeeSearch.get();
                employeeRecord.setFirstName(employeeRequest.getFirstName());
                employeeRecord.setLastName(employeeRequest.getLastName());
                employeeRecord.setEmployeeType(employeeRequest.getEmployeeType());
                employeeRecord.setDepartment(employeeRequest.getDepartment());
                employeeRecord.setDateOfJoining(employeeRequest.getDateOfJoining());
                employeeRecord.setCity(employeeRequest.getCity());
                employeeRecord.setState(employeeRequest.getState());
                employeeRecord.setCountry(employeeRequest.getCountry());

                employee = employeeRepository.save(employeeRecord);

            }
        }
        catch (Exception exp){
            log.error("Error encountered while updating employee");
        }
        return employee;
    }

    public void deleteEmployee(long id) {

        Optional<Employee> employeeSearch = employeeRepository.findById(id);
        if (employeeSearch.isPresent()) {
            employeeRepository.deleteById(id);
        }
    }

    public List<Employee> getEmployeeByType(String employeeType) {
       return employeeRepository.findByEmployeeType(employeeType);
    }

    public List<Employee> getAllEmployeesPaginated(Integer pageNo, Integer pageSize, String sortBy)
    {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<Employee> pagedResult = employeePagingSortingRepository.findAll(paging);

        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<>();
        }
    }
}

