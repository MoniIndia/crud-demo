package com.example.crud_demo.repositories;

import com.example.crud_demo.models.Employee;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeePagingSortingRepository extends PagingAndSortingRepository<Employee, Long> {
}

