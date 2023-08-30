package com.employee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employee.entity.Employee;
import com.employee.entity.TaxDeductionResponse;
import com.employee.service.EmployeeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {
   
    
    @Autowired
    private EmployeeService empService;

   /**
    * API to create new Employee providing relevant fields
    * @param employee
    * @return
    */
    @PostMapping("/create")
    public Employee createEmployee(@Valid @RequestBody Employee employee) {
        return empService.createEmployee(employee);
    }

    /**
     * API to fetch the tax Deductions
     * @return
     */
    @GetMapping("/fetch/tax-deduction")
    public List<TaxDeductionResponse> getTaxDeductions() {
    	 return empService.getTaxDeductions();
    }
    	
}

