package br.com.wagnerlima85.employees.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import br.com.wagnerlima85.employees.service.EmployeeService;
import br.com.wagnerlima85.employees.service.TeamService;

@ShellComponent
public class EmployeeCommands {

    @Autowired
    private EmployeeService employeeService;
    
    @Autowired
    private TeamService teamService;

    @ShellMethod("Initial loading")
    public void load() {
        employeeService.loadCSV();
    }

    @ShellMethod("Allocate employees to teams")
    public void allocate() {
        teamService.allocateOrBalance(false);
    }

    @ShellMethod("Balance employees to teams")
    public void balance() {
        teamService.allocateOrBalance(true);
    }    

    @ShellMethod("Promote employees: restrict numbers")
    public void promote(Integer value) {
        employeeService.promote(value);
    }
}
