package br.com.wagnerlima85.employees.repository;

import org.springframework.data.repository.CrudRepository;
import br.com.wagnerlima85.employees.model.Employee;


public interface EmployeeRepository extends CrudRepository<Employee, Long>, EmployeeRepositoryCustom{
    
}
