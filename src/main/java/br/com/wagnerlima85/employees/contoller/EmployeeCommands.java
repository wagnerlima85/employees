package br.com.wagnerlima85.employees.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import br.com.wagnerlima85.employees.service.EmployeeService;

@ShellComponent
public class EmployeeCommands {

    @Autowired
    private EmployeeService service;

    @ShellMethod("Initial loading")
    public void load() {
        service.loadCSV();
    }

    @ShellMethod("Allocate employees to teams")
    public void allocate() {
        service.allocateOrBalance(false);
    }

    @ShellMethod("Balance employees to teams")
    public void balance() {
        service.allocateOrBalance(true);
    }    

    @ShellMethod("Promote employees: restrict numbers")
    public void promote(Integer value) {
        service.promote(value);
    }
}
