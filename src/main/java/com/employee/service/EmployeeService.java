package com.employee.service;

import java.util.List;

import com.employee.entity.Employee;
import com.employee.entity.TaxDeductionResponse;

import jakarta.validation.Valid;

public interface EmployeeService {

	Employee createEmployee(@Valid Employee employee);

	List<TaxDeductionResponse> getTaxDeductions();

}
