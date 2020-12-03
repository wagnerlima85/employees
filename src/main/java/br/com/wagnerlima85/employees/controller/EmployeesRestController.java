package br.com.wagnerlima85.employees.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.wagnerlima85.employees.service.EmployeeService;
import br.com.wagnerlima85.employees.service.TeamService;

@RestController
@RequestMapping("/employee")
public class EmployeesRestController {
    
    @Autowired
    private EmployeeService employeeService;
    
    @Autowired
    private TeamService teamService;

    @PostMapping(value = "/load")
    public ResponseEntity<?> load(){
        employeeService.loadCSV();
        return ResponseEntity.ok().body("Comando executado com sucesso. Verifique o log para mais informações");
    }

    @PutMapping(value = "/promote/{value}")
    public ResponseEntity<?> promote(@PathVariable String value){
        employeeService.promote(Integer.parseInt(value));
        return ResponseEntity.ok().body("Comando executado com sucesso. Verifique o log para mais informações");
    }

    @PutMapping(value = "/allocate")
    public ResponseEntity<?> allocate(){
        teamService.allocateOrBalance(false);
        return ResponseEntity.ok().body("Comando executado com sucesso. Verifique o log para mais informações");
    }

    @PutMapping(value = "/balance")
    public ResponseEntity<?> balance(){
        teamService.allocateOrBalance(true);
        return ResponseEntity.ok().body("Comando executado com sucesso. Verifique o log para mais informações");
    }
}
