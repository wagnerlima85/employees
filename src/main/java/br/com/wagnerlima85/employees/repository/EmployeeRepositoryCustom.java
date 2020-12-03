package br.com.wagnerlima85.employees.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.wagnerlima85.employees.model.Employee;

@Repository
public interface EmployeeRepositoryCustom {
    List<Employee> listOrderByWeight();
	
}
