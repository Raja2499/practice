package com.employee.serviceImpl;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employee.entity.Employee;
import com.employee.entity.TaxDeductionResponse;
import com.employee.repository.EmployeeRepository;
import com.employee.service.EmployeeService;

import jakarta.validation.Valid;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
    private EmployeeRepository employeeRepository;
    
	
	@Override
	public Employee createEmployee(@Valid Employee employee) {
		return employeeRepository.save(employee);
	}


	@Override
	public List<TaxDeductionResponse> getTaxDeductions() {
		List<Employee> employees = employeeRepository.findAll();
        List<TaxDeductionResponse> deductions = new ArrayList<>();
        double lop = 0;
        for (Employee employee : employees) {
            LocalDate joiningDate = employee.getDoj();
            LocalDate startOfYear = joiningDate.withMonth(4).withDayOfMonth(1);
            LocalDate endOfYear = joiningDate.plusYears(1).withMonth(3).withDayOfMonth(31);

            if (joiningDate.isAfter(startOfYear)) {
//                startOfYear = joiningDate.plusYears(1).withMonth(4).withDayOfMonth(1);
//                endOfYear = joiningDate.plusYears(2).withMonth(3).withDayOfMonth(31);
            	  startOfYear = joiningDate;
                //lop = ChronoUnit.DAYS.between(startOfYear, joiningDate) * employee.getSalary()/30;
            }
            //System.out.println("joiningDate " + joiningDate + " startOfYear " + startOfYear + " endOfYear " + endOfYear);

           // System.out.println("lop" +ChronoUnit.DAYS.between(startOfYear, joiningDate));
     
            
            double totalSalary = employee.getSalary() * (ChronoUnit.MONTHS.between(startOfYear, endOfYear) + 1) ;
           // System.out.println(totalSalary + " total salary for " + employee.getFirstName());
            double yearlySalary = employee.getSalary() * 12;

            double taxAmount = calculateTax(totalSalary);
            double cessAmount = calculateCess(totalSalary);

            TaxDeductionResponse response = new TaxDeductionResponse();
            response.setEmployeeCode(employee.getEmployeeId());
            response.setFirstName(employee.getFirstName());
            response.setLastName(employee.getLastName());
            response.setYearlySalary(yearlySalary);
            response.setTaxAmount(taxAmount);
            response.setCessAmount(cessAmount);

            deductions.add(response);
        }

        return deductions;
	}


	private double calculateTax(double yearlySalary) {
        if (yearlySalary <= 250000) {
            return 0;
        } else if (yearlySalary <= 500000) {
            return 0.05 * (yearlySalary - 250000);
        } else if (yearlySalary <= 1000000) {
            return 0.1 * (yearlySalary - 500000) + 12500;
        } else {
            return 0.2 * (yearlySalary - 1000000) + 112500;
        }
    }

    private double calculateCess(double yearlySalary) {
        if (yearlySalary > 2500000) {
            return 0.02 * (yearlySalary - 2500000);
        }
        return 0;
    }

}
